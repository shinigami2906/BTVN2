package com.example.sqlite_bai2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sqlite_bai2.database.DatabaseHelper;
import com.example.sqlite_bai2.model.Account;

public class MainActivity2 extends AppCompatActivity {
    EditText etRegisterEmail, etRegisterPassword, etName;
    Button btRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setID();
        final DatabaseHelper dbHelper = new DatabaseHelper(this);
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String email = etRegisterEmail.getText().toString().trim();
                String pass = etRegisterPassword.getText().toString().trim();
                Account account = new Account(name,email,pass);
                dbHelper.addAccount(account);
                Toast.makeText(MainActivity2.this,"Đăng ký thành công",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity2.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    protected void setID(){
        etName = findViewById(R.id.etName);
        etRegisterEmail = findViewById(R.id.etRegisterEmail);
        etRegisterPassword = findViewById(R.id.etRegisterPassword);
        btRegister = findViewById(R.id.btRegisterMain2);
    }
}