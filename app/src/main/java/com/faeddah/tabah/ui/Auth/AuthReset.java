package com.faeddah.tabah.ui.Auth;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class AuthReset extends BaseFragment {

    public static final String TAG = AuthReset.class.getSimpleName();
    private Button btnResetEmail;
    private EditText edtEmail;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth_resetpw,container,false);
        findViews(view);
        initViews(view);
        initListeners(view);
        return view;
    }

    @Override
    public void findViews(View view) {
         btnResetEmail = view.findViewById(R.id.btn_reset);
         edtEmail = view.findViewById(R.id.edt_email);
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void initListeners(View view) {
        btnResetEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                final String email = String.valueOf(edtEmail.getText());
                String regexEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (TextUtils.isEmpty(email)){
                    edtEmail.setError(getString(R.string.hint_harus_diisi));
                    return;
                } if (!email.trim().matches(regexEmail)){
                    edtEmail.setError(getString(R.string.hint_email_tidak_valid));
                    return;
                }
                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            showDialog("Berhasil", "Silahkan buka email "+email+" untuk mereset password anda, nuhun.");
                        } else {
                            showDialog("Gagal bos .. ", "Email tidak terdaftar");
                        }

                    }
                });
            }
        });

    }

    public void showDialog(String title, String msg){
        android.app.AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getFragmentManager().popBackStackImmediate();

                    }
                })
                .create();
        dialog.show();

    }
}
