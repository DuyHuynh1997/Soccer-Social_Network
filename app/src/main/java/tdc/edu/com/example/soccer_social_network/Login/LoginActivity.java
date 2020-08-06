package tdc.edu.com.example.soccer_social_network.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import tdc.edu.com.example.soccer_social_network.MenuAcitvity;
import tdc.edu.com.example.soccer_social_network.R;


public class LoginActivity extends AppCompatActivity {
    EditText edtEmail,edtPassword;
    Button btnLogin;
    TextView txtRegisted,txtFogotPassword;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    String email,password;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);


        edtEmail = findViewById(R.id.edtEmail_login_layout);
        edtPassword = findViewById(R.id.edtPassword_login_layout);
        btnLogin = findViewById(R.id.btnSignIn_login_layout);
        txtRegisted = findViewById(R.id.txtRegisterredNow_login_layout);
        fAuth = FirebaseAuth.getInstance();
        txtFogotPassword = findViewById(R.id.txtForgotPassword_login_layout);
        progressBar = findViewById(R.id.progressBar);


        txtRegisted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        txtFogotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText resetmail = new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Reset password ?");
                passwordResetDialog.setMessage("Enter Your Email to Recived Reset Link.");
                passwordResetDialog.setView(resetmail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String mail = resetmail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(LoginActivity.this,"Reset Link Sent To Your Email",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this,"Error! Reset Link is Not Sent" + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                passwordResetDialog.create().show();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = edtEmail.getText().toString().trim();
                password = edtPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    edtEmail.setError("Email chưa nhập.");
                    return;
                }
                if (TextUtils.isEmpty(password))
                {
                    edtPassword.setError("Mật Khẩu chưa nhập.");
                    return;
                }

                if(password.length() < 6)
                {
                    edtPassword.setError("Mật Khẩu phải trên 6 ký tự");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate the user

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(LoginActivity.this,"Logged in Successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this,MenuAcitvity.class));
                            finish();

                        }else {
                            Toast.makeText(LoginActivity.this,"Error !" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });
    }


}
