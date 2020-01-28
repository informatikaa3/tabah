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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.faeddah.tabah.ui.CountDrawable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
public class Home extends BaseActivity {

    public static final String TAG = Home.class.getSimpleName();
    private AppBarConfiguration appBarConfiguration;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener stateListener;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private DrawerLayout drawerLayout;
    private LayerDrawable icon;
    private NavigationView navigationView;
    private NavController navController;
    private MenuInflater menuInflater;
    private MenuItem itemLogout,itemProfile, swMenuItem;
    private Menu menu;
    private TextView tvProfileNamaNav, tvSaldoNav;
    private ImageView imgProfilNav;
    private static final String namaKoleksi = "users_detail";
    private String docid, saldo,nama,imgurl;
    private long terakhirklik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
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
                // status user login
                if (user != null) {
                    db.collection(namaKoleksi)
                            .whereEqualTo("uid", user.getUid())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            docid = document.getId();
                                        }
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                    // Snapshot data realtime
                                    final DocumentReference documentReference = db.collection(namaKoleksi).document(docid);
                                    documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                            if(e != null){
                                                Toast.makeText(Home.this, "Listen gagal", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            if(documentSnapshot !=null && documentSnapshot.exists()){
                                                nama = documentSnapshot.get("nama").toString();
                                                saldo = documentSnapshot.get("saldo").toString();
                                                imgurl = documentSnapshot.get("imgUrl").toString();
                                                tvSaldoNav.setText(saldo);
                                                tvProfileNamaNav.setText(nama);
                                                Glide.with(getApplicationContext())
                                                        .load(imgurl)
                                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                        .apply(new RequestOptions().centerCrop())
                                                        .into(imgProfilNav);
//                                                Toast.makeText(Home.this, "Captured Snapshot !!!!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });

                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    tvSaldoNav.setVisibility(View.VISIBLE);
                    itemLogout.setVisible(true);
                    itemProfile.setVisible(true);
                    itemProfile.setChecked(false);

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
                    Toast.makeText(getApplicationContext(), "Logout Berhasil", Toast.LENGTH_SHORT).show();
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
            setCount(this, "0",menu);
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
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



}

