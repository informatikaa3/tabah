package com.faeddah.tabah;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.faeddah.tabah.ui.Auth.AuthLogin;

public class Auth extends BaseActivity{

    private AuthLogin mAuthLogin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        if (savedInstanceState == null) {
            loadAuthFragment();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void findViews() {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {


    }

    private void loadAuthFragment() {
        if (mAuthLogin == null) {
            mAuthLogin = new AuthLogin();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentFrame, mAuthLogin, mAuthLogin.TAG)
                .commit();
    }
}