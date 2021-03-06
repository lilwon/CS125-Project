package com.example.cs125_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener{
    private TextView regInfo, registerUser, signInTextView;
    private EditText editTextFullName, editTextAge, editTextDOB, editTextEmail, editTextPassword;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        regInfo = (TextView) findViewById(R.id.reginfoBanner);
        regInfo.setOnClickListener(this);

        signInTextView = (TextView) findViewById(R.id.signInView);
        signInTextView.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.registeruserBtn);
        registerUser.setOnClickListener(this);

        editTextFullName = (EditText) findViewById(R.id.fullnameInput);
        editTextAge = (EditText) findViewById(R.id.ageInput);
        editTextDOB = (EditText) findViewById(R.id.dobInput);
        editTextEmail = (EditText) findViewById(R.id.emailregInput);
        editTextPassword = (EditText) findViewById(R.id.passwordregInput);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.reginfoBanner:
                finish(); // return to Activity
                break;
            case R.id.registeruserBtn:
                regUser();
                break;
            case R.id.signInView:
                finish(); // return to sign in page
                break;
        }

    }

    private void regUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullname = editTextFullName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String dob = editTextDOB.getText().toString().trim();

        if (fullname.isEmpty()) {
            editTextFullName.setError("Full name required!");
            editTextFullName.requestFocus();
            return;
        }

        if (age.isEmpty()) {
            editTextAge.setError("Age required!");
            editTextAge.requestFocus();
            return;
        }

        if (dob.isEmpty()) {
            editTextDOB.setError("DOB required!");
            editTextDOB.requestFocus();
            return;
        }

        if (email.isEmpty()) {
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

        // Writes new users to Firebase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(fullname, age, dob, email, password);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterUser.this, "User registered successfully!", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(RegisterUser.this, "Failed to register! Try again", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(RegisterUser.this, "Failed to register! Try again", Toast.LENGTH_LONG).show();
                        }
                    }
                });

       startActivity(new Intent(RegisterUser.this, UserInfo.class));


    }
}