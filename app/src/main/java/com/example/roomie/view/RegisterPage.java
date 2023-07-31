package com.example.roomie.view;


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
import com.example.roomie.service.Request.Register;


public class RegisterPage extends AppCompatActivity {

    Button registerButton;
    View loadingLayout;
    TextView loginText, unknownUserText;
    EditText nameEditText, surnameEditText, usernameEditText,
            emailEditText, passwordEditText, passwordRepeatEditText;
    ImageView passwordEye, passwordRepeatEye;
    UserRoomController userRoomController;
    PageNavigator pageNavigator = PageNavigator.getInstance();
    User user = User.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#f0f1f2"));

        if (!pageNavigator.getFromPageName().equals("login")) {
            userRoomController = new UserRoomController(this);
            UserEntity userFromRoom = userRoomController.getUserFromRoom();

            if (userFromRoom != null) {
                Intent intent = new Intent(RegisterPage.this, LoginPage.class);
                startActivity(intent);
                finish();
            }
        }

        pageNavigator.setFromPageName("register");

        nameEditText = findViewById(R.id.name);
        surnameEditText = findViewById(R.id.surname);
        usernameEditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        passwordRepeatEditText = findViewById(R.id.passwordRepeat);
        passwordEye = findViewById(R.id.password_eye);
        passwordRepeatEye = findViewById(R.id.password_repeat_eye);

        passwordEye.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
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


        passwordRepeatEye.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Show the password
                        passwordRepeatEditText.setTransformationMethod(null);

                        return true;
                    case MotionEvent.ACTION_UP:
                        // Hide the password
                        passwordRepeatEditText.setTransformationMethod(new PasswordTransformationMethod());

                        return true;
                }
                return false;
            }
        });

        loginText = findViewById(R.id.loginText);
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterPage.this, LoginPage.class);
                startActivity(intent);
            }
        });

        unknownUserText = findViewById(R.id.unknownUserText);
        unknownUserText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setIsAnonymous(true);

                pageNavigator.setFromPageName("login_anonymous");

                Intent intent = new Intent(RegisterPage.this, LoginPage.class);
                startActivity(intent);
            }
        });

        loadingLayout = findViewById(R.id.loading_layout);

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameF = nameEditText.getText().toString().trim();
                String surnameF = surnameEditText.getText().toString().trim();
                String usernameF = usernameEditText.getText().toString().trim();
                String emailF = emailEditText.getText().toString().trim();
                String passwordF = passwordEditText.getText().toString().trim();
                String passwordRepeatF = passwordRepeatEditText.getText().toString().trim();

                if (formController(nameF, surnameF, usernameF, emailF, passwordF, passwordRepeatF)) {
                    RegisterPage.this.runOnUiThread(new Runnable() {
                        public void run() {
                            loadingLayout.setVisibility(View.VISIBLE);
                        }
                    });

                    controlRegister(
                            nameF,
                            surnameF,
                            usernameF,
                            emailF,
                            passwordF,
                            "", "", "", "",
                            false);
                }

            }
        });
    }

    public void controlRegister(String name, String surname, String username, String email, String password,
                                String accessToken, String accessTokenExpiration,
                                String refreshToken, String refreshTokenExpiration, Boolean rememberMe) {
        Register model = new ViewModelProvider(this).get(Register.class);

        RegisterPage.this.runOnUiThread(new Runnable() {
            public void run() {
                loadingLayout.setVisibility(View.VISIBLE);
            }
        });

        model.getRegisterStatus(name, surname, username, email, password)
                .observe(this, state -> {
                    Log.i("REGISTER", "STATE1 : " + state);

                    if (state) {
                        Intent intent = new Intent(RegisterPage.this, LoginPage.class);

                        /*try {
                            Log.i("REGISTER",
                                    "NAME : " + name + " SURNAME : " + surname +
                                            " USERNAME : " + username + " EMAIL : " + email +
                                            " PASSWORD : " + password + " ACCESSTOKEN : " + accessToken +
                                            " ACCESSTOKENEXP : " + accessTokenExpiration +
                                            " REFRESHTOKEN : " + refreshToken +
                                            " REFRESHTOKENEXP : " + refreshTokenExpiration +
                                            " REMEMBERME : " + rememberMe);


                            userRoomController.setUserToRoom(name, surname, username, email, password,
                                    accessToken, accessTokenExpiration, refreshToken, refreshTokenExpiration,
                                    rememberMe);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }*/

                        startActivity(intent);
                    } else {
                        if (errorHandlerModel.getRegisterErrorMessage() != null && !errorHandlerModel.getRegisterErrorMessage().equals("")) {
                            Toast.makeText(RegisterPage.this, errorHandlerModel.getRegisterErrorMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

        RegisterPage.this.runOnUiThread(new Runnable() {
            public void run() {
                loadingLayout.setVisibility(View.GONE);
            }
        });
    }

    public Boolean formController(String name, String surname, String username,
                                  String email, String password, String passwordRepeat) {
        FormValidator formValidator = new FormValidator();

        if (!formValidator.validateName(name)) {
            nameEditText.setError("İsminizi doğru formatta girdiğinizden emin olun");
            return false;
        } else if (!formValidator.validateName(surname)) {
            surnameEditText.setError("Soyisminizi doğru formatta girdiğinizden emin olun");
            return false;
        } else if (!formValidator.validateEmail(email)) {
            emailEditText.setError("Geçerli bir e-posta adresi giriniz");
            return false;
        } else if (!formValidator.validatePassword(password)) {
            passwordEditText.setError("En az (8 haneli, 1 büyük harf, 1 küçük harf, 1 sayı)");
            return false;
        } else if (!formValidator.validatePasswordMatch(password, passwordRepeat)) {
            passwordRepeatEditText.setError("Parolalarınız eşleşmelidir");
            return false;
        } else {
            return true;
        }
    }
}
