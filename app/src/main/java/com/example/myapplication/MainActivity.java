package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Database.DBHandler;
import com.example.myapplication.Models.CurrentUser;
import com.example.myapplication.Models.Task;
import com.example.myapplication.Models.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHandler = new DBHandler(this);

        init();

    }

    private void init() {
        EditText emailET = findViewById(R.id.emailInput);
        EditText usernameET = findViewById(R.id.userNameInput);
        EditText passwordET = findViewById(R.id.passwordInput);
        EditText phoneInputET = findViewById(R.id.phoneInput);
        String email = emailET.getText().toString();
        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();
        String phoneInput = phoneInputET.getText().toString();

        User user = new User();
        user.setPhoneNumber(phoneInput);
        user.setName(username);
        user.setPassword(password);
        user.setEmail(email);

        findViewById(R.id.signInButton).setOnClickListener(v -> login(user));
        findViewById(R.id.registerButton).setOnClickListener(v -> register(user));

    }

    private void register(User u) {
        Boolean checkUser = dbHandler.checkUsername(u.getName());
        if(!checkUser) {
            Boolean insert = dbHandler.insert(u);
            if(insert)
                Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void login(User u){
        if(dbHandler.checkUsernamePassword(u.getName(), u.getPassword())) {
            CurrentUser.getInstance().setUser(u);
            startActivity(new Intent(this, TaskActivity.class));
        }else
            Toast.makeText(getApplicationContext(), "Login Unsuccessful", Toast.LENGTH_SHORT).show();
    }
}