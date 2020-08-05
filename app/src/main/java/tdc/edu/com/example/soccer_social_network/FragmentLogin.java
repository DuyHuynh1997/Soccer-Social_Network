package tdc.edu.com.example.soccer_social_network;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class FragmentLogin extends Fragment {
    EditText edtEmail,edtPassword;
    Button btnLogin;
    TextView txtRegisted;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    String email,password;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {


        View flagment = null;
        flagment = inflater.inflate(R.layout.login_layout, container, false);

        edtEmail = flagment.findViewById(R.id.edtEmail_login_layout);
        edtPassword = flagment.findViewById(R.id.edtPassword_login_layout);
        btnLogin = flagment.findViewById(R.id.btnSignIn_login_layout);
        txtRegisted = flagment.findViewById(R.id.txtRegisterredNow_login_layout);
        fAuth = FirebaseAuth.getInstance();

        progressBar = flagment.findViewById(R.id.progressBar);


        txtRegisted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(),Registered.class));
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
                            Toast.makeText(getActivity(),"Logged in Successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity().getApplicationContext(),MenuAcitvity.class));


                        }else {
                            Toast.makeText(getActivity(),"Error !" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });
        return flagment;

    }

    public void updateAdmin(){

    }
}