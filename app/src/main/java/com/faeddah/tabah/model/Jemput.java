package com.faeddah.tabah.model;

public class Jemput {
    private String nama;
    private String alamat;
    private String harga;
    private String kategori_sampah;
    private String telp;
    private String kota;
    private String uid;

    public Jemput(){}

    public Jemput(String nama,String alamat, String harga,String kategori_sampah, String telp, String kota, String uid){
        this.nama = nama;
        this.alamat = alamat;
        this.harga = harga;
        this.kategori_sampah = kategori_sampah;
        this.telp = telp;
        this.kota = kota;
        this.uid = uid;
    }

    public String getNama(){ return nama; }
    public String getAlamat(){ return alamat; }
    public String getHarga(){ return harga; }
    public String getKategori_sampah(){
        return kategori_sampah;
    }
    public String getTelp(){ return telp; }
    public String getKota(){ return kota; }
    public  String getUid(){ return uid; }


}
