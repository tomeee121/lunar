package tb.lunar.web.controller;

import org.springframework.data.domain.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tb.lunar.infrastructure.jpa.entity.Spaceship;
import tb.lunar.infrastructure.jpa.repo.SpaceshipRepository;
import tb.lunar.web.dto.SpaceshipDTO;

import java.util.List;

@RestController
@RequestMapping("/api/spaceships")
@Validated
public class SpaceshipController {

    private final SpaceshipRepository repo;

    public SpaceshipController(SpaceshipRepository repo) {
        this.repo = repo;
    }

    @GetMapping(params = {"page", "size"})
    public Page<SpaceshipDTO> listPaged(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "contains") String match,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        String entitySort = switch (sortBy.toLowerCase()) {
            case "booster" -> "booster";
            case "maxcapacity" -> "maximumCapacity";
            case "weight" -> "weight";
            default -> "name";
        };

        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.fromString(sortDir), entitySort));

        // 1) get one by id from dropdown
        if (id != null) {
            return repo.findById(id)
                    .map(s -> new PageImpl<>(
                            List.of(SpaceshipDTO.fromEntity(s)),
                            pageable,
                            1
                    ))
                    .orElse((PageImpl<SpaceshipDTO>) Page.<SpaceshipDTO>empty(pageable));
        }

        // 2) text filter
        if (q != null && !q.isBlank()) {
            Page<Spaceship> p = "exact".equalsIgnoreCase(match)
                    ? repo.findByNameIgnoreCase(q, pageable)
                    : repo.findByNameContainingIgnoreCase(q, pageable);
            return p.map(SpaceshipDTO::fromEntity);
        }

        // 3) no filter
        return repo.findAllBy(pageable).map(SpaceshipDTO::fromEntity);
    }


    @GetMapping("/all")
    public List<SpaceshipDTO> listAllSimple() {
        return repo.findAllWithFuel().stream()
                .map(SpaceshipDTO::fromEntity)
                .toList();
    }
}
