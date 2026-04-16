package org.example.netflx_1.Izleme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/izleme")
public class IzlemeController {

    @Autowired
    private IzlemeRepository izlemeRepository;

    @PostMapping("/guncelle")
    public String guncelle(@RequestBody IzlemeGecmisi veri) {
        // SQL'de bu kullanıcı ve bu film için "DEVAM_EDIYOR" kaydı var mı?
        var mevcut = izlemeRepository.findByKullaniciIdAndFilmIdAndDurum(veri.getKullaniciId(), veri.getFilmId(), "DEVAM_EDIYOR");

        if (mevcut.isPresent()) {
            IzlemeGecmisi guncellenecek = mevcut.get();
            int yeniDakika = (guncellenecek.getIzlenenDakika() == null ? 0 : guncellenecek.getIzlenenDakika()) + 10;
            guncellenecek.setIzlenenDakika(yeniDakika);
            guncellenecek.setKalanDakika(120 - yeniDakika); // Örnek 120 dk
            guncellenecek.setSonIzlemeTarihi(LocalDateTime.now());
            if (yeniDakika >= 120) guncellenecek.setDurum("TAMAMLANDI");
            izlemeRepository.save(guncellenecek);
            return "Guncellendi";
        } else {
            IzlemeGecmisi yeni = new IzlemeGecmisi();
            yeni.setKullaniciId(veri.getKullaniciId());
            yeni.setFilmId(veri.getFilmId());
            yeni.setIzlenenDakika(10);
            yeni.setToplamSure(120);
            yeni.setKalanDakika(110);
            yeni.setDurum("DEVAM_EDIYOR");
            yeni.setSonIzlemeTarihi(LocalDateTime.now());
            izlemeRepository.save(yeni);
            return "Yeni Kayit Açıldı";
        }
    }
}
