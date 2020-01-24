package com.faeddah.tabah.ui.Profile;


import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class EditProfileFragment extends BaseFragment {

    public static final String TAG = "profile_edit";
    private String uid,nama,email,notlp, imgurl, alamat, documentId, oldfoto, newnama, newtelp, newalamat,newimg;
    private TextInputEditText edtemail, edtnama, edtnotlp, edtalamat, edtpassword;
    private EditText edtpasswordganti, edtpasswordsipganti;
    private TextView tvpesan;
    private TextView tvgantifoto;
    private ImageView imgProfil;
    private Button upgrade, gantipw, update;
    private ProgressBar progressBar;
    private AlertDialog dialog;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private StorageReference storageReference;
    private FirebaseStorage storage;
    private static final String namaCollection = "users_detail";
    private static final String storagePath = "foto_users/";
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int WRITE_EXTERNAL_PERMISION_CODE = 200;
    private static final int GALERY_CODE = 300;
    private static final int CAMERA_REQUEST = 1888;
    private static final int WRITE_EXTERNAL_REQUEST = 1999;
    private Map<String,Object> updates = new HashMap<>();
    private Uri imageUri, newImageUri;
    private Bitmap capturedfoto;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_editprofile, container,false);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        storageReference = getInstance().getReference();

        findViews(view);
        initViews(view);
        initListeners(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void findViews(View view) {
        edtemail = view.findViewById(R.id.edt_email);
        edtnama =  view.findViewById(R.id.edt_nama);
        edtnotlp = view.findViewById(R.id.edt_tlp);
        edtalamat = view.findViewById(R.id.edt_alamat);
        edtpassword = view.findViewById(R.id.edt_password);
        imgProfil = view.findViewById(R.id.img_profil);
        upgrade = view.findViewById(R.id.btn_upgrade);
        gantipw = view.findViewById(R.id.btn_gantipw);
        tvgantifoto = view.findViewById(R.id.tv_gantifoto);
        update = view.findViewById(R.id.btn_updatedata);


    }

    @Override
    public void initViews(View view) {

        if(!getArguments().isEmpty()){
            edtemail.setText(getArguments().getString("email"));
            edtnama.setText(getArguments().getString("nama"));
            edtalamat.setText(getArguments().getString("alamat"));
            edtnotlp.setText(getArguments().getString("telp"));
            Glide.with(getContext())
                    .load(getArguments().getString("imgurl"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(new RequestOptions().centerCrop())
                    .into(imgProfil);
        }
    }

    @Override
    public void initListeners(View view) {

        gantipw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogGantiPw();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });

        upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Prerequisite upgrade account ", Toast.LENGTH_SHORT).show();
            }
        });

        tvgantifoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihFoto();
            }
        });

    }

    private void showDialogGantiPw(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_profile_dialog_gantipw, null);
        edtpasswordganti =  view.findViewById(R.id.edt_password);
        edtpasswordsipganti = view.findViewById(R.id.edt_sip_password);
        tvpesan= view.findViewById(R.id.tv_pesan);
        progressBar = view.findViewById(R.id.progress);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.BLACK, android.graphics.PorterDuff.Mode.MULTIPLY);
        builder.setView(view);
        builder.setTitle("Ganti Password");
        builder.setPositiveButton("Ganti", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pw = edtpasswordganti.getText().toString();
                String pwsip = edtpasswordsipganti.getText().toString();
                if (TextUtils.isEmpty(pw)){
                    tvpesan.setVisibility(View.VISIBLE);
                    tvpesan.setText(getString(R.string.hint_harus_diisi));
                    edtpasswordganti.getText().clear();
                    edtpasswordsipganti.getText().clear();
                    return;
                }

                if (TextUtils.isEmpty(pwsip)){
                    tvpesan.setVisibility(View.VISIBLE);
                    tvpesan.setText(getString(R.string.hint_harus_diisi));
                    edtpasswordganti.getText().clear();
                    edtpasswordsipganti.getText().clear();
                    return;
                } else if (!pw.equals(pwsip)) {
                    tvpesan.setVisibility(View.VISIBLE);
                    tvpesan.setText(getString(R.string.hint_password_beda));
                    edtpasswordganti.getText().clear();
                    edtpasswordsipganti.getText().clear();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                user.updatePassword(pwsip)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    edtpasswordganti.getText().clear();
                                    edtpasswordsipganti.getText().clear();
                                    progressBar.setVisibility(View.GONE);
                                    tvpesan.setVisibility(View.VISIBLE);
                                    tvpesan.setText("Password berhasil diganti");
                                } else {
                                    edtpasswordganti.getText().clear();
                                    edtpasswordsipganti.getText().clear();
                                    progressBar.setVisibility(View.GONE);
                                    tvpesan.setVisibility(View.VISIBLE);
                                    tvpesan.setText("Gagal ...");
                                }
                            }
                        });
            }
        });
    }

    private void showSnackBarMessage(String message) {

        if (getView() != null) {

            Snackbar.make(getView(),message,Snackbar.LENGTH_SHORT).show();
        }
    }

    // Keperluan Ambil Gambar
    private void pilihFoto(){
        String opsi[] = {"Kamera", "Galeri"};
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(getContext());
        dialog.setTitle("Pilih foto :")
                .setItems(opsi, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){
                            // dari kamera
                            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                            }
                            else {
                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                            }
                        } else if (which == 1){
                            // dari galeri
                            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_PERMISION_CODE );
                            } else {
                                Intent galeryIntent = new Intent(Intent.ACTION_PICK);
                                galeryIntent.setType("image/*");
                                startActivityForResult(galeryIntent, GALERY_CODE);
                            }
                        } else {
                            dialog.dismiss();
                        }
                    }
                }).create().show();
    }

    private void updateData(){
        // upload foto
        progressBar = getView().findViewById(R.id.progressbar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.BLACK, android.graphics.PorterDuff.Mode.MULTIPLY);
        progressBar.setVisibility(View.VISIBLE);
        String rString = UUID.randomUUID().toString();
        final String namafile = storagePath+rString;


        Bitmap bitmap = ((BitmapDrawable) imgProfil.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageReference.child(namafile).putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "gagalUpload", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot
                        .getMetadata()
                        .getReference()
                        .getDownloadUrl()
                        .addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()){

                                }
                            }
                        }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        newImageUri = uri;
                         // update firestore
                        db.collection(namaCollection)
                                .whereEqualTo("uid", user.getUid())
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                documentId = document.getId();
                                                oldfoto = document.get("imgUrl").toString();
                                            } if (isAdded()){
                                                newnama = edtnama.getText().toString();
                                                newtelp = edtnotlp.getText().toString();
                                                newimg = newImageUri.toString();
                                                newalamat = edtalamat.getText().toString();

                                                updates.put("nama", newnama);
                                                updates.put("alamat", newalamat);
                                                updates.put("telp", newtelp);
                                                updates.put("imgUrl", newimg);
                                                db.collection(namaCollection).document(documentId).
                                                        update(updates)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                                                        .setDisplayName(newnama)
                                                                        .setPhotoUri(newImageUri)
                                                                        .build();
                                                                user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        progressBar.setVisibility(View.GONE);
                                                                        showSnackBarMessage("Berhasil di perbaharui");
//                                                                        StorageReference storageReferenceDel = storage.getReferenceFromUrl(oldfoto);
//                                                                        storageReferenceDel.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                                            @Override
//                                                                            public void onSuccess(Void aVoid) {
//                                                                                Toast.makeText(getContext(), "Berhasil Diupdate", Toast.LENGTH_SHORT).show();
//                                                                            }
//                                                                        });
                                                                    }
                                                                }).addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        progressBar.setVisibility(View.GONE);
                                                                    }
                                                                });
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.w(TAG, "Error updating document", e);
                                                            }
                                                        });

                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                    }
                });
            }
        });
    }

    // Handle Permission sama Result
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST: {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "Temp Pict");
                values.put(MediaStore.Images.Media.DESCRIPTION, "Temp Desc");
                imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }

            break;
            case WRITE_EXTERNAL_REQUEST: {
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent, "Pilih gambar : "), GALERY_CODE);
            }
            break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        Toast.makeText(getContext(), "request code : " + String.valueOf(requestCode)+ " || result code : " + String.valueOf(resultCode), Toast.LENGTH_SHORT).show();
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == CAMERA_REQUEST ){
                assert data !=null;
                capturedfoto =  (Bitmap) data.getExtras().get("data");
                imgProfil.setDrawingCacheEnabled(true);
                imgProfil.buildDrawingCache();
                imgProfil.setImageBitmap(capturedfoto);
            } else if (requestCode == GALERY_CODE){
                imageUri = data.getData();
                imgProfil.setImageURI(imageUri);
            }
        }
    }
}
