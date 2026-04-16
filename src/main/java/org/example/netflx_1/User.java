package org.example.netflx_1;

import jakarta.persistence.*;

@Entity
@Table(name = "kullanicilar") // database'deki tablo adı
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ad;
    private String soyad;
    private String email;
    private String sifre;

    @Column(name = "favori_tur")
    private String favoriTur;

    @Column(name = "kalan_dakika")
    private int kalanDakika;


    // --- GETTER SETTER ---

    public Long getId() {
        return id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getFavoriTur() {
        return favoriTur;
    }

    public void setFavoriTur(String favoriTur) {
        this.favoriTur = favoriTur;
    }

    public int getKalanDakika() {
        return kalanDakika;
    }

    public void setKalanDakika(int kalanDakika) {
        this.kalanDakika = kalanDakika;
    }
}