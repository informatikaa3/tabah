package com.faeddah.tabah.ui.Sell;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;


import com.faeddah.tabah.BaseFragment;
import com.faeddah.tabah.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AntarFragment extends BaseFragment {

    private Spinner dropkota;
    private Spinner droppengepul;
    private Button btnsave;
    private Bitmap myBitmap;
    private ImageView imageView;
    private LinearLayout linertop;

    private String message = "";
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
    }

    @Override
    public void initViews(View view) {
        String[] items = new String[]{"1", "2", "three"};
        String[] pengepul = new String[]{"asep","ujang","nativ"};
    //create an adapter to describe how the items are displayed, adapters are used in several places in android.
    //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, pengepul);
    //set the spinners adapter to the previously created one.
        dropkota.setAdapter(adapter);
        droppengepul.setAdapter(adapter1);
    }

    @Override
    public void initListeners(View view) {
        btnsave.setVisibility(View.VISIBLE);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrdialog(getContext());
//                btnsave.setVisibility(View.GONE);
            }
        });
    }

    public void qrdialog(Context ctx){
        Bitmap bitmap = null;
        try
        {
            message = "22333111";
            bitmap = CreateImage(message);
            myBitmap = bitmap;
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
}
