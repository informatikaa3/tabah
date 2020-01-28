package com.faeddah.tabah.ui.Shopping;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class ShoppingAdd extends BaseFragment {

    private TextInputEditText  edtnama, edtharga, edtstok, edtdeskripsi;
    private ImageView imgpreview;
    private Button btnupload, btnjual;
    private Map<String, Object> dataJual = new HashMap<>();
    private FirebaseUser user;
    private FirebaseFirestore db;
    private StorageReference storageReference;
    private static final String namaCollection = "item_jualbarang";
    private static final  String storagePath = "foto_jualbarang/";
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int WRITE_EXTERNAL_PERMISION_CODE = 200;
    private static final int GALERY_CODE = 300;
    private static final int CAMERA_REQUEST = 1888;
    private static final int WRITE_EXTERNAL_REQUEST = 1999;
    private Uri imageUri;
    private Bitmap capturedfoto;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_add_produk, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        storageReference = getInstance().getReference();
        findViews(view);
        initViews(view);
        initListeners(view);

        return view;
    }

    @Override
    public void findViews(View view) {
        edtnama = view.findViewById(R.id.edt_namabarang);
        edtharga = view.findViewById(R.id.edt_hargabarang);
        edtstok = view.findViewById(R.id.edt_stokbarang);
        edtdeskripsi = view.findViewById(R.id.edt_deskripsibarang);
        imgpreview = view.findViewById(R.id.preview_barang);
        btnupload = view.findViewById(R.id.btn_addfoto);
        btnjual = view.findViewById(R.id.btn_addbarang);

    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void initListeners(View view) {
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihFoto();
            }
        });

        btnjual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadAllData();
            }
        });

    }

    private void pilihFoto(){
        String opsi[] = {"Kamera", "Galeri"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
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

    @Override
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
                imgpreview.setDrawingCacheEnabled(true);
                imgpreview.buildDrawingCache();
                capturedfoto =  (Bitmap) data.getExtras().get("data");
                imgpreview.setImageBitmap(capturedfoto);
            } else if (requestCode == GALERY_CODE){
                imageUri = data.getData();
                imgpreview.setImageURI(imageUri);
            }
        }
    }


    private void uploadAllData(){
        String rString = UUID.randomUUID().toString();
        final String namafile = storagePath+rString;

        Bitmap bitmap = ((BitmapDrawable) imgpreview.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageReference.child(namafile).putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "gagalUpload", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot
                        .getMetadata()
                        .getReference()
                        .getDownloadUrl()
                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        dataJual.put("judulBarang", edtnama.getText().toString());
                        dataJual.put("hargaBarang", Integer.parseInt(edtharga.getText().toString()));
                        dataJual.put("stokbarang", Integer.parseInt(edtstok.getText().toString()));
                        dataJual.put("deskripsiBarang", edtdeskripsi.getText().toString());
                        dataJual.put("imgUrl",uri.toString());
                        dataJual.put("uidOwner", user.getUid());
                        dataJual.put("tanggalPosting", new Date());
                        db.collection(namaCollection).document().set(dataJual);
                        Toast.makeText(getContext(), "Barang berhasil di tambahkan ... ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}
