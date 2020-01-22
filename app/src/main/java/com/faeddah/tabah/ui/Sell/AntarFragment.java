package com.faeddah.tabah.ui.Sell;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.faeddah.tabah.model.Antar;


import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.R;
import com.faeddah.tabah.model.Antar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AntarFragment extends BaseFragment {

    public static final String TAG = AntarFragment.class.getSimpleName();

    public String[] jenis = new String[]{"plastik", "kayu", "elektronik"};
    public String[] kota = new String[]{"bandung", "jakarta", "surabaya"};
    public String[] pengepul = new String[]{"1","ujang","nativ"};

    private FirebaseDatabase fd;
    private FirebaseFirestore db;
    private DatabaseReference database;
    private Spinner dropkota,droppengepul,dropjenis;
    private String fdjenis,fdkota,fdpengepul,keterangan;
    private Button btnsave;
    private Bitmap myBitmap;
    private ImageView imageView;
    private LinearLayout linertop;

    private String message,userUid;
    private String type = "";
    private int size = 660;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_sell_antar, container, false);
        findViews(view);
        initViews(view);
        initListeners(view);
        return view;
    }

    @Override
    public void findViews(View view) {
        btnsave = view.findViewById(R.id.btn_antar_save);
        dropkota = view.findViewById(R.id.drop_kota_sell_jemput);
        droppengepul = view.findViewById(R.id.drop_pengepul_sell);
        dropjenis = view.findViewById(R.id.drop_jenis_sell_jemput);
    }

    @Override
    public void initViews(View view) {
    //create an adapter to describe how the items are displayed, adapters are used in several places in android.
    //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapterll = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, jenis);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, kota);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, pengepul);
    //set the spinners adapter to the previously created one.
        dropjenis.setAdapter(adapterll);
        dropkota.setAdapter(adapter);
        droppengepul.setAdapter(adapter1);
    }


    @Override
    public void initListeners(View view) {
        btnsave.setVisibility(View.VISIBLE);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String spkota = dropkota.getSelectedItem().toString();
                String spjenis = dropjenis.getSelectedItem().toString();
                String sppengepul = droppengepul.getSelectedItem().toString();
                String key = "1";
                keterangan = "bayar";
                senddatatodb(new Antar(spjenis,keterangan,spkota.toString(),sppengepul,message));
                qrdialog(getContext());
            }
        });
    }

    public void qrdialog(Context ctx){
        Bitmap bitmap = null;
        try
        {
            if(message != null) {
                bitmap = CreateImage(message);
                myBitmap = bitmap;
            }else{
                AlertDialog dialog = new AlertDialog.Builder(ctx)
                        .setTitle("Warning")
                        .setMessage("Silahkan Login Terlebih dahulu")
                        .setNegativeButton("Keluar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                dialog.show();
            }
        }
        catch (WriterException we)
        {
            we.printStackTrace();
        }
        if (bitmap != null)
        {
//            imageView.setImageBitmap(bitmap);
            // SETAN ANJING TEU JALAN JALAN
            final AlertDialog.Builder alertadd = new AlertDialog.Builder(ctx);
            LayoutInflater factory = LayoutInflater.from(ctx);
            final View viewcek = factory.inflate(R.layout.fragment_sell_antar_qrcode, null);
            alertadd.setView(viewcek);
            imageView = viewcek.findViewById(R.id.image_imageview);
            imageView.setImageBitmap(bitmap);
            alertadd.setNeutralButton("Back", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dlg, int sumthin) {
                    dlg.dismiss();
                }
            });
            alertadd.show();
        }
    }

    public Bitmap CreateImage(String message) throws WriterException
    {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.QR_CODE, size, size);

        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int [] pixels = new int [width * height];
        for (int i = 0 ; i < height ; i++)
        {
            for (int j = 0 ; j < width ; j++)
            {
                if (bitMatrix.get(j, i))
                {
                    pixels[i * width + j] = 0xff000000;
                }
                else
                {
                    pixels[i * width + j] = 0xffffffff;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    public String getdbdata(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userUid = user.getUid();
        return userUid;
    }

    public void senddatatodb(Antar antar) {
//        database = FirebaseDatabase.getInstance().getReference();
//        database.child("transaksi_jbs").push().setValue(antar);
        this.db = FirebaseFirestore.getInstance();
        db.collection("transaksi_jbs").add(antar).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Log.d(TAG, "DocumentSnapshot successfully written!");
                DocumentReference docRef = task.getResult();
                message = docRef.getId();
                Log.v("KEY", message);
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error writing document", e);
            }
        });
        Toast.makeText(getContext(), "CEK DB MASUK", Toast.LENGTH_SHORT).show();
    }
}
