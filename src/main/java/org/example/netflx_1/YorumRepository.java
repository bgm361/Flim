package org.example.netflx_1;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface YorumRepository extends JpaRepository<Yorum, Long> {
    // SQL: SELECT * FROM yorumlar ORDER BY id DESC
    List<Yorum> findAllByOrderByIdDesc();
    List<Yorum> findByFilmIdOrderByIdDesc(Long filmId);
}