package com.faeddah.tabah.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Antar {
    public String jenis_sampah;
    public String keterangan;
    public String kota;
    public String uid_pengepul;
    public String uid_user;


    public Antar(String jenis_sampah,String keterangan,String kota,String uid_pengepul,String uid_user){

//        Map<String, Object> docData = new HashMap<>();
//        docData.put("jenis_sampah", jenis_sampah);
//        docData.put("keterangan", keterangan);
//        docData.put("kota", kota);
//        docData.put("uid_pengepul",uid_pengepul);
//        docData.put("uid_user",uid_user);

        this.jenis_sampah = jenis_sampah;
        this.keterangan = keterangan;
        this.kota = kota;
        this.uid_pengepul = uid_pengepul;
        this.uid_user = uid_user;
    }

//    public String getJenis_sampah(){ return jenis_sampah; }
//
//    public String getKeterangan(){ return keterangan; }
//
//    public String getKota(){ return kota; }
//
//    public String getUid_pengepul(){ return uid_pengepul; }
//
//    public String getUid_user(){ return uid_user; }


    public String getjenis_sampah(){ return jenis_sampah; }
    public void setJenis_sampah(String jenis_sampah){this.jenis_sampah=jenis_sampah;}

    public String getKeterangan(){ return keterangan; }
    public void setKeterangan(String keterangan){this.keterangan=keterangan;}

    public String getKota(){ return kota; }
    public void setKota(String kota){this.kota=kota;}

    public String getUid_pengepul(){ return uid_pengepul; }
    public void setUid_pengepul(String uid_pengepul){this.uid_pengepul=uid_pengepul;}

    public String getUid_user(){ return uid_user; }
    public void setUid_user(String uid_user){this.uid_user=uid_user;}


    @Override
    public String toString() {
        return " "+jenis_sampah+"\n" +
                " "+keterangan+"\n"+
                " "+kota +"\n" +
                " "+uid_pengepul+"\n"+
                " "+uid_user;
    }

}
