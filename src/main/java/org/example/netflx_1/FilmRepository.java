package org.example.netflx_1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FilmRepository extends JpaRepository<Film, Long> {

    @Query("SELECT AVG(f.puan) FROM Film f")
    Double ortalamaPuan();
}

