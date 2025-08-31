package tb.lunar.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tb.lunar.infrastructure.jpa.repo.SpaceshipRepository;
import tb.lunar.web.dto.SpaceshipDTO;

import java.util.List;

@RestController
@RequestMapping("/api/spaceships")
public class SpaceshipController {

    private final SpaceshipRepository spaceshipRepository;

    public SpaceshipController(SpaceshipRepository spaceshipRepository) {
        this.spaceshipRepository = spaceshipRepository;
    }

    @GetMapping
    public List<SpaceshipDTO> listAll() {
        return spaceshipRepository.findAll().stream()
                .map(SpaceshipDTO::fromEntity)
                .toList();
    }
}