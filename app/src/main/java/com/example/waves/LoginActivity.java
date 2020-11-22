package com.example.waves;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    TextView textView,textView1;
    Button button;
    FirebaseAuth fAuth;
    public void SignupPage(View view){
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);

    }
    public void ForgotPassword(View view){
        Toast.makeText(LoginActivity.this,"Server under maintenance. Please try again later",Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        button=(Button)findViewById(R.id.button);
        textView=(TextView)findViewById(R.id.textView);
        textView1=(TextView)findViewById(R.id.textView1);
        fAuth=FirebaseAuth.getInstance();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email= email.getText().toString().trim();
                String Password=password.getText().toString().trim();
                if(TextUtils.isEmpty(Email)){
                    email.setError("Email is Required");
                    return ;

                }
                if(TextUtils.isEmpty(Password)){
                    password.setError("Password is Required");
                    return ;

                }
                if(Password.length()<8){
                    password.setError("Password must have atleast 8 characters");
                    return;
                }
                fAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"Logged in successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),UserActivity.class));
                        }else{
                            Toast.makeText(LoginActivity.this,"Error! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
