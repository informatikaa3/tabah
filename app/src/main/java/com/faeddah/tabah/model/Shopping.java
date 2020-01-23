package com.faeddah.tabah.model;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Shopping {
    private String judulBarang, deskripsiBarang, uidOwner,uidBarang, imgUrl;
    private long hargaBarang,stokBarang;
    private Date tanggalPosting;
    public Shopping() {}


    public Shopping(String judulBarang, String deskripsiBarang, String uidOwner, String uidBarang, String imgUrl, long hargaBarang, long stokBarang, Date tanggalPosting) {
        this.judulBarang = judulBarang;
        this.deskripsiBarang = deskripsiBarang;
        this.uidOwner = uidOwner;
        this.uidBarang = uidBarang;
        this.imgUrl = imgUrl;
        this.hargaBarang = hargaBarang;
        this.stokBarang = stokBarang;
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

    public long getHargaBarang() {
        return hargaBarang;
    }

    public void setHargaBarang(long hargaBarang) {
        this.hargaBarang = hargaBarang;
    }

    public long getStokBarang() {
        return stokBarang;
    }

    public void setStokBarang(long stokBarang) {
        this.stokBarang = stokBarang;
    }

    @ServerTimestamp
    public Date getTanggalPosting() {
        return tanggalPosting;
    }

    public void setTanggalPosting(Date tanggalPosting) {
        this.tanggalPosting = tanggalPosting;
    }
}


