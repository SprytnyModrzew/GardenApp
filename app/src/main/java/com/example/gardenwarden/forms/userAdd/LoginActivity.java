package com.example.gardenwarden.forms.userAdd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gardenwarden.R;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final TextView login = findViewById(R.id.login_name);
        final TextView password = findViewById(R.id.login_pass);
        final TextView email = findViewById(R.id.login_email);

        Button login_button = findViewById(R.id.login_button_login);

        if(getIntent().getBooleanExtra("error",false)){
            error();
        }

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!login.getText().toString().equals("") && !password.getText().toString().equals("")){
                    Intent intent = new Intent();

                    intent.putExtra("login",login.getText().toString());
                    intent.putExtra("password",password.getText().toString());
                    intent.putExtra("command","login");
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

        Button register_button = findViewById(R.id.login_button_register);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!login.getText().toString().equals("") && !password.getText().toString().equals("") && !email.getText().toString().equals("") ){
                    Intent intent = new Intent();

                    intent.putExtra("login",login.getText().toString());
                    intent.putExtra("password",password.getText().toString());
                    intent.putExtra("email",email.getText().toString());
                    intent.putExtra("command","register");
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    public void error(){
        Snackbar snackbar = Snackbar.make(findViewById(R.id.login_layout), R.string.error_login, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
