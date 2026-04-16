package org.example.netflx_1;
import jakarta.persistence.*;

@Entity
@Table(name = "filmler")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ad;
    private String tur;
    private int toplamDakika;
    private double puan;
    private String afisUrl;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAd() { return ad; }
    public void setAd(String ad) { this.ad = ad; }
    public String getTur() { return tur; }
    public void setTur(String tur) { this.tur = tur; }
    public int getToplamDakika() { return toplamDakika; }
    public void setToplamDakika(int toplamDakika) { this.toplamDakika = toplamDakika; }
    public double getPuan() { return puan; }
    public void setPuan(double puan) { this.puan = puan; }
    public String getAfisUrl() { return afisUrl; }
    public void setAfisUrl(String afisUrl) { this.afisUrl = afisUrl; }
}
