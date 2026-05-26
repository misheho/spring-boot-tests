package com.misheho.spb_app;
import com.misheho.spb_app.Penguin;
import com.misheho.spb_app.PenguinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/penguins")
public class PenguinController {
@Autowired
    private PenguinRepository penguinRepository;

    @GetMapping
    public List<Penguin> getAllPenguins() {
        return penguinRepository.findAll();
    }

    @PostMapping
    public Penguin createPenguin(@RequestBody Penguin penguin) {
        return penguinRepository.save(penguin);
    }

    @GetMapping("/{id}")
    public Penguin getPenguinById(@PathVariable Long id) {
        return penguinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Penguin not found"));
    }
}
