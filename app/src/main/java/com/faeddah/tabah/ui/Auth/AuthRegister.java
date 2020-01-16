package com.faeddah.tabah.ui.Auth;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.concurrent.Executor;

public class AuthRegister extends BaseFragment {

    public static final String TAG = AuthRegister.class.getSimpleName();
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener stateListener;
    private FirebaseUser user;
    private UserProfileChangeRequest userProfile;
    private TextView tvLogin;
    private Button btnRegister;
    private TextInputEditText edtEmail, edtPassword, edtPasswordSip, edtNama;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_auth_register, container, false);
        findViews(view);
        initViews(view);
        initListeners(view);
//        AppCompatActivity activity = (AppCompatActivity) view.getContext();
//        Toast.makeText(getContext(), String.valueOf(activity.getSupportFragmentManager().getBackStackEntryCount()), Toast.LENGTH_SHORT).show();
        return view;
    }

    @Override
    public void onStart() {
        auth.addAuthStateListener(stateListener);
        super.onStart();
    }

    @Override
    public void onStop() {
        auth.removeAuthStateListener(stateListener);
        super.onStop();
    }

    @Override
    public void findViews(View view) {
        tvLogin = view.findViewById(R.id.tv_backtologin);
        btnRegister = view.findViewById(R.id.btn_register);
        edtNama = view.findViewById(R.id.edt_nama);
        edtEmail = view.findViewById(R.id.edt_email);
        edtPassword = view.findViewById(R.id.edt_password);
        edtPasswordSip = view.findViewById(R.id.edt_sip_password);
        progressBar = view.findViewById(R.id.progresbar);

    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void initListeners(View view) {
        final AppCompatActivity activity = (AppCompatActivity) view.getContext();
        auth = FirebaseAuth.getInstance();
        stateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User sedang login
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User sedang logout
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity.getSupportFragmentManager().popBackStackImmediate();

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regexEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String nama = String.valueOf(edtNama.getText());
                String email = String.valueOf(edtEmail.getText());
                String pw = String.valueOf(edtPassword.getText());
                String pwsip = String.valueOf(edtPasswordSip.getText());


                if (TextUtils.isEmpty(nama)){
                    edtNama.setError(getString(R.string.hint_harus_diisi));
                    return;
                }

                if (TextUtils.isEmpty(email)){
                    edtEmail.setError(getString(R.string.hint_harus_diisi));
                    return;
                } else if (!email.trim().matches(regexEmail)){
                    edtEmail.setError(getString(R.string.hint_email_tidak_valid));
                    return;
                }

                if (TextUtils.isEmpty(pw)){
                    edtPassword.setError(getString(R.string.hint_harus_diisi));
                    return;
                }

                if (TextUtils.isEmpty(pwsip)){
                    edtPasswordSip.setError(getString(R.string.hint_harus_diisi));
                    return;
                } else if (!pw.equals(pwsip)) {
                    edtPasswordSip.setError(getString(R.string.hint_password_beda));
                    return;
                }
                
                edtPasswordSip.onEditorAction(EditorInfo.IME_ACTION_DONE);
                edtNama.setCursorVisible(false);
                edtEmail.setCursorVisible(false);
                edtPassword.setCursorVisible(false);
                edtPasswordSip.setCursorVisible(false);
                regis(email,pwsip,nama);
            }
        });

    }

    private void regis (final String email, String pw, final String nama){
        progressBar.setVisibility(1);
        auth.createUserWithEmailAndPassword(email, pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.e("sep" ,String.valueOf(task.isSuccessful()));

                if (!task.isSuccessful()){
                    updateUI(0);
                    try {
                        throw task.getException();
                    } catch(FirebaseAuthWeakPasswordException e) {
                        edtPassword.setError(getString(R.string.hint_password_lemah));
                        edtPassword.requestFocus();
                    } catch(FirebaseAuthInvalidCredentialsException e) {
                        edtEmail.setError(getString(R.string.hint_email_tidak_valid));
                        edtEmail.requestFocus();
                    } catch(FirebaseAuthUserCollisionException e) {
                        showSnackBarMessage(getString(R.string.hint_email_terdaftar));
                    } catch(Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                } else {
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    userProfile = new UserProfileChangeRequest.Builder()
                            .setDisplayName(nama)
                            .setPhotoUri(Uri.parse("https://firebasestorage.googleapis.com/v0/b/tabah-b8b1f.appspot.com/o/defaultUser.png?alt=media&token=89135dc6-0a5d-418b-bd38-6e937b7ee79e"))
                            .build();
                    user.updateProfile(userProfile);
                    progressBar.setVisibility(8);
                    showSnackBarMessage(email+ " Berhasil Didaftarkan");
                    updateUI(1);
                }
            }
        });
    }


    private void showSnackBarMessage(String message) {

        if (getView() != null) {

            Snackbar.make(getView(),message,Snackbar.LENGTH_SHORT).show();
        }
    }

    private void updateUI(Integer state){
        if (state == 1) {
            edtNama.getText().clear();
            edtEmail.getText().clear();
            edtPassword.getText().clear();
            edtPasswordSip.getText().clear();
            edtNama.setCursorVisible(true);
            edtEmail.setCursorVisible(true);
            edtPassword.setCursorVisible(true);
            edtPasswordSip.setCursorVisible(true);
        } else {
            edtNama.setCursorVisible(true);
            edtEmail.setCursorVisible(true);
            edtPassword.setCursorVisible(true);
            edtPasswordSip.setCursorVisible(true);
        }
        
    }


}