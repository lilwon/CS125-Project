package com.example.cs125_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// More likely to be Login screen
// May need to "bypass" to directly go to Dashboard or something
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button sleepBtn;

    private TextView registerBtn;
    private EditText editTextEmail, editTextPassword;
    private Button login;
    private FirebaseAuth mAuth;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    boolean loginCheck;
    CheckBox saveLoginCheck;

    DrawerLayout dl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dl = findViewById(R.id.drawer_layout);

        registerBtn = (TextView) findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(this);
        login = (Button) findViewById(R.id.loginBtn);
        login.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.emailLogin);
        editTextPassword = (EditText) findViewById(R.id.passwordLogin);
        // Get shared pref
        sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        saveLoginCheck = (CheckBox)findViewById(R.id.rememberCheckBox);
        editor = sharedPreferences.edit();

        loginCheck = sharedPreferences.getBoolean("saveLogin", true);

        if ( loginCheck == true ) {

            editTextEmail.setText(sharedPreferences.getString("username", null));
            editTextPassword.setText(sharedPreferences.getString("password", null));
        }

        mAuth = FirebaseAuth.getInstance();



        /* Temp hide
        sleepBtn = (Button) findViewById(R.id.sleepBtn);
        sleepBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openSleepActivity();
            }
        });
         */
    }

    public void ClickMenu(View view) {
        openDrawer(dl);
    }

    public static void openDrawer(DrawerLayout dl) {
        dl.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view) {
        closeDrawer(dl);
    }

    public static void closeDrawer(DrawerLayout dl) {

        if ( dl.isDrawerOpen(GravityCompat.START )) {
            dl.closeDrawer(GravityCompat.START) ;
        }
    }

    public void ClickHome(View view) {
        closeDrawer(dl);
    }

    public void ClickDashboard(View view) {
        redirectActivity(this, Dashboard.class);
    }


    public static void redirectActivity(Activity activity, Class aClass) {
        Intent i = new Intent(activity, aClass);

        // Set to reoder because giving issues for other Activities
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        activity.startActivity(i);

    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(dl);
    }

    public void openSleepActivity() {
        Intent i = new Intent(this, sleep_activity.class);
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.registerBtn:
                startActivity(new Intent(this, RegisterUser.class));
                break;

            case R.id.loginBtn:
                userLogin();
                break;

        }
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()){
            editTextEmail.setError("Email required!");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Invalid email!");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password required!");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    // Save login information for current phone/user so dont need to constantly put username/pw
                    if ( saveLoginCheck.isChecked() ) {
                        editor.putBoolean("saveLogin", true);
                        editor.putString("username", email);
                        editor.putString("password", password);
                        editor.commit();
                    }

                    User u = new User();
                    u.email = email;

                    Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                    // give ProfileActivity class the email from MainActivity/login
                    i.putExtra("email", u.email);

                    Log.v("Info", email);
                    startActivity(i);
                    // startActivity(new Intent(MainActivity.this, ProfileActivity);
                    //startActivity(new Intent(MainActivity.this, UserInfo.class));
                }
                else
                    Toast.makeText(MainActivity.this, "Failed to register! Try again", Toast.LENGTH_LONG).show();
            }
        });


    }

}