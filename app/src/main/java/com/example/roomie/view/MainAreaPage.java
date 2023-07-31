package com.example.roomie.view;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.roomie.R;
import com.example.roomie.model.RoomDB.UserRoomController;
import com.example.roomie.model.User;
import com.example.roomie.service.Request.Logout;

import java.util.ArrayList;


public class MainAreaPage extends AppCompatActivity {
    Button searchWordButton, rightMenuButton;
    User user = User.getInstance();
    //SwipeRefreshLayout pullToRefresh;
    UserRoomController userRoomController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_area_page);

        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#f0f1f2"));

        //pullToRefresh = findViewById(R.id.swipeRefreshLayout);

        rightMenuButton = findViewById(R.id.profile_button);
        rightMenuButton.setOnClickListener(v -> showProfileDialog());
    }


    private void showProfileDialog() {
        try {
            MainAreaPage.this.runOnUiThread(() -> {
                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.right_side_bar);

                ImageView closeButton = dialog.findViewById(R.id.close_button);
                closeButton.setOnClickListener(v -> dialog.dismiss());

                Button exitButton = dialog.findViewById(R.id.exit_button);
                exitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        logout();

                        Intent intent = new Intent(MainAreaPage.this, LoginPage.class);
                        startActivity(intent);

                        finish();

                        dialog.dismiss();
                    }
                });

                TextView username = dialog.findViewById(R.id.username);

                if (user.getUsername() != null) {
                    username.setText(user.getUsername().replaceAll("\"", ""));
                } else {
                    username.setText("UNKNOWN");
                }

                Spinner dropdownCities, dropdownDistrict, dropdownNeighbourhood,
                        dropdownSmoke, dropdownAlcohol, dropdownGuest, dropdownAnimal,
                        dropdownGender;

                ArrayAdapter<String> adapterOfCities, adapterOfDistrict, adapterOfNeighbourhood,
                        adapterOfSmoke, adapterOfAlcohol, adapterOfGuest, adapterOfAnimal, adapterOfGender;

                String[] itemsCity, itemsDistrict, itemsNeighbourhood, itemsAnimal, itemsGuest, itemsGender,
                        itemsSmoke, itemsAlcohol;

                itemsCity = new String[6];
                itemsCity[0] = "İl?";
                itemsCity[1] = "İstanbul";
                itemsCity[2] = "Ankara";
                itemsCity[3] = "İzmir";
                itemsCity[4] = "Adana";
                itemsCity[5] = "Bursa";

                // --------------------------------

                itemsDistrict = new String[6];
                itemsDistrict[0] = "İlçe?";
                itemsDistrict[1] = "Çankaya";
                itemsDistrict[2] = "Altındağ";
                itemsDistrict[3] = "Polatlı";
                itemsDistrict[4] = "Kızılcahamam";
                itemsDistrict[5] = "Keçiören";

                // --------------------------------

                itemsNeighbourhood = new String[6];
                itemsNeighbourhood[0] = "Mahalle?";
                itemsNeighbourhood[1] = "Doğantepe";
                itemsNeighbourhood[2] = "Karapürçek";
                itemsNeighbourhood[3] = "HüseyinGazi";
                itemsNeighbourhood[4] = "Beşevler";
                itemsNeighbourhood[5] = "Eryaman";

                // --------------------------------

                itemsGender = new String[4];
                itemsGender[0] = "Cinsiyet tercihiniz?";
                itemsGender[1] = "Kadın";
                itemsGender[2] = "Erkek";
                itemsGender[3] = "Farketmez";

                // --------------------------------

                itemsAlcohol = new String[4];
                itemsAlcohol[0] = "Alkol kullanılabilir mi?";
                itemsAlcohol[1] = "Evet";
                itemsAlcohol[2] = "Hayır";
                itemsAlcohol[3] = "Farketmez";

                // --------------------------------

                itemsAnimal = new String[4];
                itemsAnimal[0] = "Evcil hayvan beslenebilir mi?";
                itemsAnimal[1] = "Evet";
                itemsAnimal[2] = "Hayır";
                itemsAnimal[3] = "Farketmez";

                // --------------------------------

                itemsGuest = new String[4];
                itemsGuest[0] = "Misafir kabul ediyor musunuz?";
                itemsGuest[1] = "Evet";
                itemsGuest[2] = "Hayır";
                itemsGuest[3] = "Farketmez";

                // --------------------------------

                itemsSmoke = new String[4];
                itemsSmoke[0] = "Ortak alanda sigara içilsin mi?";
                itemsSmoke[1] = "Evet";
                itemsSmoke[2] = "Evet";
                itemsSmoke[3] = "Tabiki evet";

                adapterOfCities = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsCity);
                adapterOfDistrict = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsDistrict);
                adapterOfNeighbourhood = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsNeighbourhood);
                adapterOfGender = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsGender);
                adapterOfAlcohol = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsAlcohol);
                adapterOfAnimal = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsAnimal);
                adapterOfGuest = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsGuest);
                adapterOfSmoke = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsSmoke);

                dropdownCities = dialog.findViewById(R.id.city);
                dropdownDistrict = dialog.findViewById(R.id.district);
                dropdownNeighbourhood = dialog.findViewById(R.id.neighbourhood);
                dropdownSmoke = dialog.findViewById(R.id.smoke);
                dropdownAlcohol = dialog.findViewById(R.id.alcohol);
                dropdownGuest = dialog.findViewById(R.id.guest);
                dropdownAnimal = dialog.findViewById(R.id.animal);
                dropdownGender = dialog.findViewById(R.id.gender);

                dropdownCities.setAdapter(adapterOfCities);
                dropdownDistrict.setAdapter(adapterOfDistrict);
                dropdownNeighbourhood.setAdapter(adapterOfNeighbourhood);
                dropdownSmoke.setAdapter(adapterOfSmoke);
                dropdownAlcohol.setAdapter(adapterOfAlcohol);
                dropdownGuest.setAdapter(adapterOfGuest);
                dropdownAnimal.setAdapter(adapterOfAnimal);
                dropdownGender.setAdapter(adapterOfGender);

                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.ProfileAnimation;
                dialog.getWindow().getExitTransition().setDuration(400);
                //dialog.getWindow().setGravity(Gravity.RIGHT);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout() {
        Logout logoutModel = new ViewModelProvider(this).get(Logout.class);

        user.setAccessToken(null);
        //user.setRefreshToken(null);
        user.setEmail(null);
        user.setUsername(null);
        user.setName(null);
        user.setSurname(null);
        user.setId(null);
        user.setIsAnonymous(false);
        user.setAccessTokenExpiration(null);
        user.setRefreshTokenExpiration(null);

        userRoomController = new UserRoomController(this);
        userRoomController.deleteUserInRoom();

        logoutModel.getLogoutStatus()
                .observe(this, state -> {
                    if (state) {
                        user.setRefreshToken(null);
                    }});
    }

    @Override
    public void onBackPressed() {}
}