package com.example.sqlite_bai2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sqlite_bai2.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button btLogin, btRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setID();
        final DatabaseHelper databaseHelper = new DatabaseHelper(this);
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String check = databaseHelper.checkAccount(email,password);
                if(!check.equals("")){
                    Toast.makeText(MainActivity.this,"Xin chào "+check,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this,"Tài khoản hoặc mật khẩu sai ",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    protected void setID(){
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btLogin = findViewById(R.id.btLogin);
        btRegister = findViewById(R.id.btRegister);
    }
}