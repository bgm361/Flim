package org.example.netflx_1.Izleme;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface IzlemeRepository extends JpaRepository<IzlemeGecmisi, Long> {

    Optional<IzlemeGecmisi> findByKullaniciIdAndFilmIdAndDurum(Long kullaniciId, Long filmId, String durum);

    List<IzlemeGecmisi> findByKullaniciId(Long userId);
}