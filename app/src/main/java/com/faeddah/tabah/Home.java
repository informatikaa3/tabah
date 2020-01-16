package com.faeddah.tabah;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.faeddah.tabah.ui.CountDrawable;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//import android.os.Bundle;

public class Home extends BaseActivity {

    public static final String TAG = Home.class.getSimpleName();
    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener stateListener;
    private FirebaseUser user;
    private DrawerLayout drawer;
    private LayerDrawable icon;
    private NavigationView navigationView;
    private MenuInflater menuInflater;
    private MenuItem itemLogout,itemProfile, swMenuItem;
    private Menu menu;
    private NavController navController;
    private TextView tvProfileNamaNav, tvSaldoNav;
    private ImageView imgProfilNav;
    private long terakhirklik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        findViews();
        initViews();
        initListeners();

    }
    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(stateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        auth.addAuthStateListener(stateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (stateListener != null) {
            auth.removeAuthStateListener(stateListener);
        }
    }

    @Override
    public void findViews() {

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        // di navslide
        itemLogout = navigationView.getMenu().findItem(R.id.nav_logout);
        itemProfile = navigationView.getMenu().findItem(R.id.nav_profile);
        tvProfileNamaNav = navigationView.getHeaderView(0).findViewById(R.id.tv_profilnama);
        tvSaldoNav = navigationView.getHeaderView(0).findViewById(R.id.tv_saldo);
        imgProfilNav = navigationView.getHeaderView(0).findViewById(R.id.img_profil);
    }

    @Override
    public void initViews() {
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_sell,
                R.id.nav_shopping, R.id.nav_settings, R.id.nav_helpcenter)
                .setDrawerLayout(drawer)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        auth = FirebaseAuth.getInstance();
        stateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // status user login
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    itemLogout.setVisible(true);
                    itemProfile.setVisible(true);
                    tvSaldoNav.setVisibility(1);
                    tvProfileNamaNav.setText(user.getDisplayName());
                    tvSaldoNav.setText("Rp.10.000");
                    Glide.with(getApplicationContext())
                            .load(user.getPhotoUrl())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .apply(new RequestOptions().centerCrop())
                            .into(imgProfilNav);

                } else {
                    // status user logout
                    tvProfileNamaNav.setTextSize(20);
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    @Override
    public void initListeners() {
        itemLogout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Intent intent = new Intent(Home.this,Home.class);
                    startActivity(intent);
//                    finish();
                    signOut();
                    return true;
                }
            });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (user != null) {
            // set badge count
            setCount(this, "99+",menu);
        } else {
            // set icon menu untuk login
            swMenuItem = menu.findItem(R.id.swMenuItem);
            swMenuItem.setIcon(R.drawable.asset_account);
        }
        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (SystemClock.elapsedRealtime() - terakhirklik < 1000) {
            return super.onOptionsItemSelected(item);
        }
        terakhirklik = SystemClock.elapsedRealtime();
        if (user != null) {
            return super.onOptionsItemSelected(item);
        } else {
            switch (item.getItemId()){
                case R.id.swMenuItem:
                    showLogin();
                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }
        }

    }

    public void setCount(Context context, String count , Menu menu) {
        swMenuItem = menu.findItem(R.id.swMenuItem);
        icon = (LayerDrawable) swMenuItem.getIcon();

        CountDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.swMenuItem);
        if (reuse != null && reuse instanceof CountDrawable) {
            badge = (CountDrawable) reuse;
        } else {
            badge = new CountDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.lonceng_count, badge);
    }

    private void showLogin(){
        Intent intent = new Intent(this, Auth.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void signOut(){
        Toast.makeText(this, "Logout berhasil ...", Toast.LENGTH_LONG).show();
        auth.signOut();
    }





}

