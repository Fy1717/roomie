package com.example.roomie.view.pages;


import static com.example.roomie.service.ApiModel.ErrorHandlerModel.errorHandlerModel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.roomie.R;
import com.example.roomie.controller.FormValidator;
import com.example.roomie.model.PageNavigator;
import com.example.roomie.model.RoomDB.UserEntity;
import com.example.roomie.model.RoomDB.UserRoomController;
import com.example.roomie.model.User;
import com.example.roomie.service.Request.Login;
import com.example.roomie.service.Request.LoginAnonymous;


public class LoginPage extends AppCompatActivity {
    Button loginButton;
    View loadingLayout, passwordLayout, rememberMeArea;
    EditText emailEditText, passwordEditText;
    CheckBox rememberMe;
    TextView registerText;
    ImageView passwordEye;
    UserRoomController userRoomController;
    User user = User.getInstance();
    PageNavigator pageNavigator = PageNavigator.getInstance();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#f0f1f2"));

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        rememberMe = findViewById(R.id.checkBoxRemember);
        registerText = findViewById(R.id.registerText);
        rememberMeArea = findViewById(R.id.rememberLinear);
        passwordLayout = findViewById(R.id.passwordLayout);
        passwordEye = findViewById(R.id.password_eye);

        passwordEye.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        passwordEditText.setTransformationMethod(null);
                        return true;
                    case MotionEvent.ACTION_UP:
                        passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
                        return true;
                }
                return false;
            }
        });

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRoomController.deleteUserInRoom();

                Intent intent = new Intent(LoginPage.this, RegisterPage.class);
                startActivity(intent);

                finish();
            }
        });

        loadingLayout = findViewById(R.id.loading_layout);

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rememberMe.isChecked()) {
                    user.setRememberMe(true);
                } else {
                    user.setRememberMe(false);
                }

                String emailF = emailEditText.getText().toString().trim();
                String passwordF = passwordEditText.getText().toString().trim();

                if (formController(emailF, passwordF)) {
                    LoginPage.this.runOnUiThread(new Runnable() {
                        public void run() {
                            loadingLayout.setVisibility(View.VISIBLE);
                        }
                    });

                    controlLogin(emailF, passwordF);
                }
            }
        });

        recordedUserController();
    }

    public void controlLogin(String email, String password) {
        Login model = new ViewModelProvider(this).get(Login.class);

        model.getLoginStatus(email, password)
                .observe(this, state -> {
                    Log.i("LOGIN", "STATE : " + state);

                    if (state) {
                        Intent intent = new Intent(LoginPage.this, MainAreaPage.class);

                        user.setIsAnonymous(false);

                        try {
                            Log.i("LOGIN",
                                    "NAME : " + user.getName() + " SURNAME : " + user.getSurname() +
                                            " USERNAME : " + user.getUsername() + " EMAIL : " + email +
                                            " PASSWORD : " + password + " ACCESSTOKEN : " + user.getAccessToken() +
                                            " ACCESSTOKENEXP : " + user.getAccessTokenExpiration() +
                                            " REFRESHTOKEN : " + user.getRefreshToken() +
                                            " REFRESHTOKENEXP : " + user.getRefreshTokenExpiration() +
                                            " REMEMBERME : " + user.getRememberMe());

                            if (user.getRememberMe()) {
                                userRoomController.updateUserInRoom(
                                        user.getName(), user.getSurname(),
                                        user.getUsername(), email, password,
                                        user.getAccessToken(), user.getAccessTokenExpiration(),
                                        user.getRefreshToken(), user.getRefreshTokenExpiration(),
                                        user.getRememberMe());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        startActivity(intent);
                    } else {
                        if (errorHandlerModel.getLoginErrorMessage() != null && !errorHandlerModel.getLoginErrorMessage().equals("")) {
                            Toast.makeText(LoginPage.this, errorHandlerModel.getLoginErrorMessage(), Toast.LENGTH_SHORT).show();
                        }

                        LoginPage.this.runOnUiThread(new Runnable() {
                            public void run() {
                                loadingLayout.setVisibility(View.GONE);
                            }
                        });
                    }
                });
    }

    public void controlLoginAnonymous() {
        LoginPage.this.runOnUiThread(new Runnable() {
            public void run() {
                loadingLayout.setVisibility(View.VISIBLE);
            }
        });

        emailEditText.setVisibility(View.GONE);
        passwordEditText.setVisibility(View.GONE);
        rememberMe.setVisibility(View.GONE);
        registerText.setVisibility(View.GONE);
        loginButton.setVisibility(View.GONE);
        passwordLayout.setVisibility(View.GONE);
        rememberMeArea.setVisibility(View.GONE);

        LoginAnonymous model = new ViewModelProvider(this).get(LoginAnonymous.class);

        model.getLoginAnonymousStatus()
                .observe(this, state -> {
                    Log.i("LOGIN_ANONYMOUS", "STATE : " + state);

                    if (state) {
                        LoginPage.this.runOnUiThread(new Runnable() {
                            public void run() {
                                loadingLayout.setVisibility(View.GONE);
                                emailEditText.setVisibility(View.VISIBLE);
                                passwordEditText.setVisibility(View.VISIBLE);
                                rememberMe.setVisibility(View.VISIBLE);
                                registerText.setVisibility(View.VISIBLE);
                                loginButton.setVisibility(View.VISIBLE);
                                passwordLayout.setVisibility(View.VISIBLE);
                                rememberMeArea.setVisibility(View.VISIBLE);
                            }
                        });

                        Intent intent = new Intent(LoginPage.this, MainAreaPage.class);
                        startActivity(intent);
                    } else {
                        if (errorHandlerModel.getLoginErrorMessage() != null && !errorHandlerModel.getLoginErrorMessage().equals("")) {
                            Toast.makeText(LoginPage.this, errorHandlerModel.getLoginErrorMessage(), Toast.LENGTH_SHORT).show();

                            loadingLayout.setVisibility(View.GONE);
                            emailEditText.setVisibility(View.VISIBLE);
                            passwordEditText.setVisibility(View.VISIBLE);
                            rememberMe.setVisibility(View.VISIBLE);
                            registerText.setVisibility(View.VISIBLE);
                            loginButton.setVisibility(View.VISIBLE);
                            passwordLayout.setVisibility(View.VISIBLE);
                            rememberMeArea.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    public void recordedUserController() {
        try {
            userRoomController = new UserRoomController(this);
            UserEntity userFromRoom = userRoomController.getUserFromRoom();

            if (userFromRoom != null) {
                Log.i("LOGIN", "EMAIL : " + userFromRoom.getEmail());
                Log.i("LOGIN", "PASS : " + userFromRoom.getPassword());
                Log.i("LOGIN", "ACCESSTOKEN : " + userFromRoom.getAccessToken());

                if (!pageNavigator.getFromPageName().equals("register")) {
                    emailEditText.setText(userFromRoom.getEmail());
                    passwordEditText.setText(userFromRoom.getPassword());
                }
            } else {
                if (pageNavigator.getFromPageName().equals("login_anonymous")) {
                    controlLoginAnonymous();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        pageNavigator.setFromPageName("login");
    }

    public Boolean formController(String email, String password) {
        FormValidator formValidator = new FormValidator();

        if (!formValidator.validateEmail(email)) {
            emailEditText.setError("Geçerli bir e-posta adresi giriniz");
            return false;
        } else if (!formValidator.validatePassword(password)) {
            passwordEditText.setError("En az (8 haneli, 1 büyük harf, 1 küçük harf, 1 sayı)");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onBackPressed() {}
}