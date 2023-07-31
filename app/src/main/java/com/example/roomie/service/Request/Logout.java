package com.example.roomie.service.Request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.roomie.model.User;
import com.example.roomie.service.ApiInterface.UserAPI;
import com.example.roomie.service.ApiModel.ErrorHandlerModel;
import com.example.roomie.service.SSLTrustModel.TrustAllCertificates;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Logout extends ViewModel {
    MutableLiveData<Boolean> status = new MutableLiveData<>();
    public static String defaultErrorMessage = "Kullanıcı çıkışı sırasında hata..";
    ErrorHandlerModel errorHandlerModel = ErrorHandlerModel.getInstance();

    public LiveData<Boolean> getLogoutStatus() {
        logout();

        return status;
    }

    private void logout() {
        try {
            errorHandlerModel.setLogoutErrorMessage(null);

            UserAPI userAPI = new TrustAllCertificates(false).createTrustAllRetrofit()
                    .create(UserAPI.class);

            User user = User.getInstance();
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), user.toJsonForLogout());

            Call<ResponseBody> call = userAPI.Logout(body);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        Log.i("LOGOUT", "RESPONSE : " + response.toString());

                        errorHandlerModel.setLogoutErrorMessage(null);

                        Gson gson = new Gson();

                        if (response.isSuccessful()) {
                            Log.i("LOGOUT", "SUCCESS");

                            status.setValue(true);
                        } else {
                            ResponseBody myResponseErrorBody = response.errorBody();

                            Log.e("LOGOUT", "ERROR11 : " + myResponseErrorBody.string());

                            JsonObject errorResult = gson.fromJson(myResponseErrorBody.string(), JsonObject.class);

                            JsonArray errorData = (JsonArray) errorResult.get("errors");

                            Log.e("LOGOUT", "ERROR12 : " + errorData.toString());

                            errorHandlerModel.setLogoutErrorMessage(errorData.get(0).toString().replaceAll("\"", ""));

                            Log.e("LOGOUT", "ERROR1 : " + defaultErrorMessage);

                            status.setValue(false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                        errorHandlerModel.setLogoutErrorMessage(defaultErrorMessage);
                        Log.e("LOGOUT", "ERROR2 : " + e.getLocalizedMessage());

                        status.setValue(false);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("LOGOUT", "ERROR3 : " + t.getLocalizedMessage());

                    errorHandlerModel.setLogoutErrorMessage(defaultErrorMessage);

                    status.setValue(false);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();

            Log.e("LOGOUT", "ERROR4 : " + e.getLocalizedMessage());

            errorHandlerModel.setLogoutErrorMessage(defaultErrorMessage);

            status.setValue(false);
        }
    }
}
