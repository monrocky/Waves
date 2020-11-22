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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText userId,password,email,phoneNumber;
    TextView textView,textView1;
    Button button;
    FirebaseAuth fAuth;
    DatabaseReference reff;
    Member member;
    public void LoginPage(View view){
        Intent intent= new Intent(this, LoginActivity.class);
        startActivity(intent);

    }
    public void ForgotPassword(View view){
        Toast.makeText(MainActivity.this,"Server under maintenance. Please try again later",Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userId=(EditText)findViewById(R.id.userID);
        password=(EditText)findViewById(R.id.password);
        email=(EditText)findViewById(R.id.email);
        phoneNumber=(EditText)findViewById(R.id.phoneNumber);
        button=(Button)findViewById(R.id.button);
        textView=(TextView)findViewById(R.id.textView);
        textView1=(TextView)findViewById(R.id.textView1);
        reff= FirebaseDatabase.getInstance().getReference().child("Member");
        member=new Member();

        fAuth=FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser()!=null){
            startActivity( new Intent(getApplicationContext(),UserActivity.class));
            finish();
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String Email= email.getText().toString().trim();
                final String Password=password.getText().toString().trim();
                if(TextUtils.isEmpty(Email)){
                    email.setError("Email is required");
                    return ;

                }
                if(TextUtils.isEmpty(Password)){
                    password.setError("Password is required");
                    return ;

                }
                if(Password.length()<8){
                    password.setError("Password must have atleast 8 characters");
                    return;
                }
                fAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        member.setEmail(Email);
                        member.setPassword(Password);
                        member.setUserId(userId.getText().toString().trim());
                        member.setPhoneNumber(phoneNumber.getText().toString().trim());
                        reff.push().setValue(member);
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"User Created",Toast.LENGTH_SHORT).show();
                            startActivity( new Intent(getApplicationContext(),UserActivity.class));
                        }else{
                            Toast.makeText(MainActivity.this,"Error! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
