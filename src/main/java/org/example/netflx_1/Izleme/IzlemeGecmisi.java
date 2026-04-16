package org.example.netflx_1.Izleme;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "izleme_gecmisi")
public class IzlemeGecmisi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long kullaniciId;
    private Long filmId;
    private Integer izlenenDakika;
    private Integer toplamSure;
    private Integer kalanDakika;
    private String durum;
    private LocalDateTime sonIzlemeTarihi;

    // Getter ve Setter'lar
    public Long getKullaniciId() { return kullaniciId; }
    public void setKullaniciId(Long kullaniciId) { this.kullaniciId = kullaniciId; }
    public Long getFilmId() { return filmId; }
    public void setFilmId(Long filmId) { this.filmId = filmId; }
    public Integer getIzlenenDakika() { return izlenenDakika; }
    public void setIzlenenDakika(Integer izlenenDakika) { this.izlenenDakika = izlenenDakika; }
    public Integer getToplamSure() { return toplamSure; }
    public void setToplamSure(Integer toplamSure) { this.toplamSure = toplamSure; }
    public Integer getKalanDakika() { return kalanDakika; }
    public void setKalanDakika(Integer kalanDakika) { this.kalanDakika = kalanDakika; }
    public String getDurum() { return durum; }
    public void setDurum(String durum) { this.durum = durum; }
    public LocalDateTime getSonIzlemeTarihi() { return sonIzlemeTarihi; }
    public void setSonIzlemeTarihi(LocalDateTime sonIzlemeTarihi) { this.sonIzlemeTarihi = sonIzlemeTarihi; }
}