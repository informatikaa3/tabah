package com.faeddah.tabah.model;

import java.util.Date;

public class Shopping {
    private String judul_barang;
    private String deskripsi_barang;
    private String key_sell;
    private String imgUrl;
    private int idBarang, idUser, stok, harga_barang;
    private Date tanggal_input;

    public Shopping() {
    }


    public String getJudul_barang() {
        return judul_barang;
    }

    public void setJudul_barang(String judul_barang) {
        this.judul_barang = judul_barang;
    }

    public String getDeskripsi_barang() {
        return deskripsi_barang;
    }

    public void setDeskripsi_barang(String deskripsi_barang) {
        this.deskripsi_barang = deskripsi_barang;
    }

    public String getKey_sell() {
        return key_sell;
    }

    public void setKey_sell(String key_sell) {
        this.key_sell = key_sell;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(int idBarang) {
        this.idBarang = idBarang;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public int getHarga_barang() {
        return harga_barang;
    }

    public void setHarga_barang(int harga_barang) {
        this.harga_barang = harga_barang;
    }

    public Date getTanggal_input() {
        return tanggal_input;
    }

    public void setTanggal_input(Date tanggal_input) {
        this.tanggal_input = tanggal_input;
    }
}


