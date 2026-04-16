package org.example.netflx_1;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "film_yorumlar")
public class Yorum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kullanici_id")
    private Long kullaniciId;

    @Column(name = "film_id")
    private Long filmId;

    private String yorum;
    private int puan;
    private LocalDateTime tarih = LocalDateTime.now();
    @Transient
    private String kullaniciAd;

    @Transient
    private String kullaniciMail;

    // BOŞ CONSTRUCTOR (Hibernate için şart)
    public Yorum() {}

    public String getKullaniciAd() { return kullaniciAd; }
    public void setKullaniciAd(String kullaniciAd) { this.kullaniciAd = kullaniciAd; }
    public String getKullaniciMail() { return kullaniciMail; }
    public void setKullaniciMail(String kullaniciMail) { this.kullaniciMail = kullaniciMail; }
    // GETTER VE SETTER'LAR
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getKullaniciId() { return kullaniciId; }
    public void setKullaniciId(Long kullaniciId) { this.kullaniciId = kullaniciId; }

    public Long getFilmId() { return filmId; }
    public void setFilmId(Long filmId) { this.filmId = filmId; }

    public String getYorum() { return yorum; }
    public void setYorum(String yorum) { this.yorum = yorum; }

    public int getPuan() { return puan; }
    public void setPuan(int puan) { this.puan = puan; }

    public LocalDateTime getTarih() { return tarih; }
    public void setTarih(LocalDateTime tarih) { this.tarih = tarih; }
}