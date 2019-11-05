package com.faeddah.tabah.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Artikel implements Parcelable {

    private String judul;
    private String artikel;
    private String key;
    private String imgUrl;
    private Date terbit;

    public Artikel() {
    }

    public Artikel(String judul, String artikel, String key, String imgUrl, Date terbit) {
        this.judul = judul;
        this.artikel = artikel;
        this.key = key;
        this.imgUrl = imgUrl;
        this.terbit = terbit;
    }


    @Override
    public String toString() {
        return "ArtikelFeed{" +
                "judul='" + judul + '\'' +
                ", artikel='" + artikel + '\'' +
                ", key='" + key + '\'' +
                '}';
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getArtikel() {
        return artikel;
    }

    public void setArtikel(String artikel) {
        this.artikel = artikel;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getTerbit() {
        return terbit;
    }

    public void setTerbit(Date terbit) {
        this.terbit = terbit;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


    // coba parcelable jeder
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.judul);
        dest.writeString(this.artikel);
        dest.writeString(this.key);
        dest.writeString(this.imgUrl);
        dest.writeLong(this.terbit != null ? this.terbit.getTime() : -1);
    }

    protected Artikel(Parcel in) {
        this.judul = in.readString();
        this.artikel = in.readString();
        this.key = in.readString();
        this.imgUrl = in.readString();
        long tmpTerbit = in.readLong();
        this.terbit = tmpTerbit == -1 ? null : new Date(tmpTerbit);
    }

    public static final Parcelable.Creator<Artikel> CREATOR = new Parcelable.Creator<Artikel>() {
        @Override
        public Artikel createFromParcel(Parcel source) {
            return new Artikel(source);
        }

        @Override
        public Artikel[] newArray(int size) {
            return new Artikel[size];
        }
    };
}
