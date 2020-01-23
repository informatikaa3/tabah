package com.faeddah.tabah;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.faeddah.tabah.ui.CountDrawable;
import com.faeddah.tabah.ui.Home.ArtikelDetail;
import com.faeddah.tabah.ui.Home.ArtikelFeed;
import com.faeddah.tabah.ui.Profile.EditProfileFragment;
import com.faeddah.tabah.ui.Profile.ProfileFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.Objects;

//import android.os.Bundle;

public class Home extends BaseActivity {

    public static final String TAG = Home.class.getSimpleName();
    private AppBarConfiguration appBarConfiguration;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener stateListener;
    private FirebaseUser user;
    private DrawerLayout drawerLayout;
    private LayerDrawable icon;
    private NavigationView navigationView;
    private NavController navController;
    private MenuInflater menuInflater;
    private MenuItem itemLogout,itemProfile, swMenuItem;
    private Menu menu;
    private TextView tvProfileNamaNav, tvSaldoNav;
    private ImageView imgProfilNav;

    private long terakhirklik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNavigation();
        initInstanceFirebase();
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
    public void onBackPressed() {
//        Toast.makeText(this, String.valueOf(getSupportFragmentManager().getBackStackEntryCount()), Toast.LENGTH_SHORT).show();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public void findViews() {

        itemLogout = navigationView.getMenu().findItem(R.id.nav_logout);
        itemProfile = navigationView.getMenu().findItem(R.id.nav_profile);
        tvProfileNamaNav = navigationView.getHeaderView(0).findViewById(R.id.tv_profilnama);
        tvSaldoNav = navigationView.getHeaderView(0).findViewById(R.id.tv_saldo);
        imgProfilNav = navigationView.getHeaderView(0).findViewById(R.id.img_profil);
    }

    @Override
    public void initViews() {

        stateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
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
//                    swMenuItem.setChecked(false);

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
                    auth.signOut();
                    finish();
                    startActivity(getIntent());
                    return true;
                }
            });

    }




    private void initNavigation(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout_home);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_home);
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_sell,
                R.id.nav_shopping, R.id.nav_settings, R.id.nav_helpcenter)
                .setDrawerLayout(drawerLayout)
                .build();
        NavigationUI.setupWithNavController(navigationView,navController);
    }

    private void initInstanceFirebase(){
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_home);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
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
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_home);

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
                    return NavigationUI.onNavDestinationSelected(item, navController)
                            || super.onOptionsItemSelected(item);
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
        startActivity(intent);
    }



}

