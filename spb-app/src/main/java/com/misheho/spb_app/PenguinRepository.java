package com.misheho.spb_app;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.misheho.spb_app.Penguin;

public interface PenguinRepository extends JpaRepository<Penguin, Long>{
    List<Penguin> findBySpecies(String species);


}
