package com.misheho.spb_app;
import com.misheho.spb_app.Penguin;
import com.misheho.spb_app.PenguinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/species/{species}")
    public List<Penguin> getPenguinBySpecies(@PathVariable String species) {
        List<Penguin> penguins = penguinRepository.findBySpecies(species);
        if (penguins.isEmpty()) {
            throw new RuntimeException("Penguin species not found");
        }
        return penguins;
    }

    @PatchMapping("/{id}")
    public Penguin patchPenguinById(@PathVariable Long id, @RequestBody Penguin update) {
        Penguin penguin = penguinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Penguin not found"));

        if (update.getSpecies() != null) {
            penguin.setSpecies(update.getSpecies());
        }
        if (update.getHabitat() != null) {
            penguin.setHabitat(update.getHabitat());
        }

        return penguinRepository.save(penguin);
    }

    @PatchMapping("/species/{species}")
    public List<Penguin> patchPenguinBySpecies(@PathVariable String species, @RequestBody Penguin update) {
        List<Penguin> penguins = penguinRepository.findBySpecies(species);
        if (penguins.isEmpty()) {
            throw new RuntimeException("Penguin species not found");
        }
        for (Penguin penguin : penguins) {
            if (update.getSpecies() != null) {
                penguin.setSpecies(update.getSpecies());
            }
            if (update.getHabitat() != null) {
                penguin.setHabitat(update.getHabitat());
            }
        }
        return penguinRepository.saveAll(penguins);
    }

    @DeleteMapping("/{id}")
    public void deletePenguin(@PathVariable Long id) {
        penguinRepository.deleteById(id);
    }

    
}
