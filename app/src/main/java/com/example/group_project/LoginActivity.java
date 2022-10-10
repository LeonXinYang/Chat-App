package com.example.group_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText editText_Email;
    EditText editText_Password;
    Button button_Login;
    String number = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Get views
        TextView text_Register = (TextView) findViewById(R.id.textView_Register);
        editText_Email = (EditText) findViewById(R.id.editText_Email_Login);
        editText_Password = (EditText) findViewById(R.id.editText_Password_Login);
        button_Login = (Button) findViewById(R.id.button_Login);


        //Register Successful
        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            editText_Email.setText(email);
            editText_Password.setText(password);
        }


        text_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });

        button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_Login.setClickable(false);
                login();
            }
        });

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("Users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                number = String.valueOf(snapshot.getChildrenCount());
                myRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//        myRef.child("Hello World").setValue("Hello");

        //Authentication
        mAuth = FirebaseAuth.getInstance();
    }



    private void login(){
        String email = editText_Email.getText().toString();
        String password = editText_Password.getText().toString();

        if (TextUtils.isEmpty(email)){
            editText_Email.setError("Email cannot be empty!");
            editText_Email.requestFocus();
            button_Login.setClickable(true);
        }else if (TextUtils.isEmpty(password)){
            editText_Password.setError("Password cannot be empty!");
            editText_Password.requestFocus();
            button_Login.setClickable(true);
        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        GlobalData.setUser_firebase(mAuth.getCurrentUser());

                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = firebaseDatabase.getReference("Users");

                        myRef.child(mAuth.getCurrentUser().getUid()).child("UserID").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                System.out.println("11123213");
                                if (snapshot.getValue(String.class) == null){
                                    myRef.child(mAuth.getCurrentUser().getUid()).child("UserID").setValue(String.valueOf(10000 + Integer.parseInt(number)));
                                }
                                myRef.removeEventListener(this);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        button_Login.setClickable(true);
                    }
                }
            });
        }
    }

}