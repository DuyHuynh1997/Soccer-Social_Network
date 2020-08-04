package tdc.edu.com.example.soccer_social_network;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Registered extends AppCompatActivity {
    EditText edtmEmail,edtMatKhau;
    Button btnRegister;
    TextView txtLogin;
    FirebaseAuth fAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registered_layout);


        edtmEmail = findViewById(R.id.edtEmail_registered_layout);
        edtMatKhau = findViewById(R.id.edtPassword_registered_layout);
        btnRegister = findViewById(R.id.btnRegister_registered_layout);
        txtLogin = findViewById(R.id.txtLoginNow_registered_layout);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(),MenuAcitvity.class));
            finish();
        }

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtmEmail.getText().toString().trim();
                String password = edtMatKhau.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    edtmEmail.setError("Email chưa nhập.");
                    return;
                }
                if (TextUtils.isEmpty(password))
                {
                    edtMatKhau.setError("Mật Khẩu chưa nhập.");
                    return;
                }

                if(password.length() < 6)
                {
                    edtMatKhau.setError("Mật Khẩu phải trên 6 ký tự");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //registered the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Registered.this,"User Created.",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MenuAcitvity.class));
                        }
                        else {
                            Toast.makeText(Registered.this,"Error !" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}