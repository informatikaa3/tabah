package com.faeddah.tabah.ui.Auth;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.faeddah.tabah.Auth;
import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.Home;
import com.faeddah.tabah.R;
import com.faeddah.tabah.dummydata.DataDev;
import com.faeddah.tabah.model.Dev;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.ArrayList;

public class AuthLogin extends BaseFragment {

    public static final String TAG = AuthLogin.class.getSimpleName();
    private FirebaseAuth auth;
    private FirebaseUser user;
    private TextView tvLupaPw, tvRegister;
    private TextInputEditText edtEmail, edtPassword;
    private Button btnLogin, btnCancel;
    private ProgressBar progressBar;
    private AuthRegister authRegister;
    private AuthReset authReset;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth_login, container, false);
        findViews(view);
        initViews(view);
        initListeners(view);
//        AppCompatActivity activity = (AppCompatActivity) view.getContext();
//        Toast.makeText(getContext(), String.valueOf(activity.getSupportFragmentManager().getBackStackEntryCount()), Toast.LENGTH_SHORT).show();
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void findViews(View view) {
        edtEmail = view.findViewById(R.id.edt_email);
        edtPassword = view.findViewById(R.id.edt_password);
        btnLogin = view.findViewById(R.id.btn_login);
        btnCancel = view.findViewById(R.id.btn_cancel);
        tvLupaPw = view.findViewById(R.id.tv_forget);
        tvRegister = view.findViewById(R.id.tv_register);
        progressBar = view.findViewById(R.id.progresbar);
    }

    @Override
    public void initViews(View view) {
        authRegister  = new AuthRegister();
        authReset = new AuthReset();
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void initListeners(View view) {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regexEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String email = String.valueOf(edtEmail.getText());
                String pw = String.valueOf(edtPassword.getText());

                if(TextUtils.isEmpty(email) ){
                    edtEmail.setError("harus di isi");
                    return;
                } else if (!email.trim().matches(regexEmail)){
                    edtEmail.setError("email tidak valid");
                    return;
                }

                if(TextUtils.isEmpty(pw)){
                    edtPassword.setError("harus di isi");
                    return;
                }
                masuk(email,pw);
            }
        });

        tvLupaPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSnackBarMessage("lupaPass");
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    pindahFragment(authRegister, authRegister.TAG);
            }
        });

        tvLupaPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    pindahFragment(authReset, authReset.TAG);
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                getActivity().finish();
            }
        });


    }


    private void showSnackBarMessage(String message) {

        if (getView() != null) {

            Snackbar.make(getView(),message,Snackbar.LENGTH_SHORT).show();
        }
    }

     private void pindahFragment(Fragment fragment, String tag){
         FragmentTransaction ft = getFragmentManager().beginTransaction();
         ft.add(fragment, tag);
         ft.replace(R.id.fragmentFrame,fragment,tag);

//        tambah ke stack (back button)
         ft.addToBackStack(tag);
         ft.commit();
     }

    private void masuk(final String email, String password){
        progressBar.setVisibility(1);
        edtEmail.getText().clear();
        edtPassword.getText().clear();
        edtEmail.onEditorAction(EditorInfo.IME_ACTION_DONE);
        edtPassword.onEditorAction(EditorInfo.IME_ACTION_DONE);
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.e(TAG, "signInWithEmail:failed", task.getException());
                            showSnackBarMessage("login gagal ....");
                            progressBar.setVisibility(8);
                        } else{
                            // kalo berhasil
                            Log.d(TAG, "signInWithEmail:Succes:");
                            progressBar.setVisibility(8);
                            Intent intent = new Intent(getActivity(), Home.class);
                            startActivity(intent);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getActivity().finish();
                            user = FirebaseAuth.getInstance().getCurrentUser();
                            Toast.makeText(getContext(), "Selamat datang " + user.getDisplayName() ,Toast.LENGTH_LONG).show();
                        }
                        // ... rest code
                    }
                });
    }

}
