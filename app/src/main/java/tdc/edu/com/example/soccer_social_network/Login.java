//package tdc.edu.com.example.soccer_social_network;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.TextUtils;
//
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.android.material.navigation.NavigationView;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//
//
//public class Login extends AppCompatActivity {
//    EditText edtEmail,edtPassword;
//    Button btnLogin;
//    TextView txtRegisted;
//    ProgressBar progressBar;
//    FirebaseAuth fAuth;
//    String email,password;
//    FirebaseAuth mAuth;
//    FirebaseUser currentUser;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.login_layout);
//
//
//        edtEmail = findViewById(R.id.edtEmail_login_layout);
//        edtPassword = findViewById(R.id.edtPassword_login_layout);
//        btnLogin = findViewById(R.id.btnSignIn_login_layout);
//        txtRegisted = findViewById(R.id.txtRegisterredNow_login_layout);
//        fAuth = FirebaseAuth.getInstance();
//
//        progressBar = findViewById(R.id.progressBar);
//
//
//        txtRegisted.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(),Registered.class));
//            }
//        });
//
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                email = edtEmail.getText().toString().trim();
//                password = edtPassword.getText().toString().trim();
//
//                if(TextUtils.isEmpty(email))
//                {
//                    edtEmail.setError("Email chưa nhập.");
//                    return;
//                }
//                if (TextUtils.isEmpty(password))
//                {
//                    edtPassword.setError("Mật Khẩu chưa nhập.");
//                    return;
//                }
//
//                if(password.length() < 6)
//                {
//                    edtPassword.setError("Mật Khẩu phải trên 6 ký tự");
//                    return;
//                }
//
//                progressBar.setVisibility(View.VISIBLE);
//
//                //authenticate the user
//
//                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful())
//                        {
//                            Toast.makeText(Login.this,"Logged in Successfully",Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(getApplicationContext(),MenuAcitvity.class));
//
//                        }else {
//                            Toast.makeText(Login.this,"Error !" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                });
//
//            }
//        });
//    }
//
//
//
//}