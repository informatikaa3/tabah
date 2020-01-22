package com.faeddah.tabah.model;
import com.google.errorprone.annotations.FormatString;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Shopping {
    private String judulBarang, deskripsiBarang, uidOwner,uidBarang, imgUrl;
    private String hargaBarang,stokBarang;
    private Date tanggalPosting;
    public Shopping() {}

    public Shopping(String judulBarang, String deskripsiBarang, String uidOwner, String uidBarang, String imgUrl, String stokBarang, String hargaBarang, Date tanggalPosting) {
        this.judulBarang = judulBarang;
        this.deskripsiBarang = deskripsiBarang;
        this.uidOwner = uidOwner;
        this.uidBarang = uidBarang;
        this.imgUrl = imgUrl;
        this.stokBarang = stokBarang;
        this.hargaBarang = hargaBarang;
        this.tanggalPosting = tanggalPosting;
    }

    public String getJudulBarang() {
        return judulBarang;
    }

    public void setJudulBarang(String judulBarang) {
        this.judulBarang = judulBarang;
    }

    public String getDeskripsiBarang() {
        return deskripsiBarang;
    }

    public void setDeskripsiBarang(String deskripsiBarang) {
        this.deskripsiBarang = deskripsiBarang;
    }

    public String getUidOwner() {
        return uidOwner;
    }

    public void setUidOwner(String uidOwner) {
        this.uidOwner = uidOwner;
    }

    public String getUidBarang() {
        return uidBarang;
    }

    public void setUidBarang(String uidBarang) {
        this.uidBarang = uidBarang;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

//    public String getHargaBarang() {
//        return hargaBarang;
//    }
//
//    public void setHargaBarang(String hargaBarang) {
//        this.hargaBarang = hargaBarang;
//    }
//
//    public String getStokBarang() {
//        return stokBarang;
//    }
//
//    public void setStokBarang(String stokBarang) {
//        this.stokBarang = stokBarang;
//    }

    @ServerTimestamp
    public Date getTanggalPosting() {
        return tanggalPosting;
    }

    public void setTanggalPosting(Date tanggalPosting) {
        this.tanggalPosting = tanggalPosting;
    }
}


