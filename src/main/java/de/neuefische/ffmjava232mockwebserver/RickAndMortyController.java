package de.neuefische.ffmjava232mockwebserver;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rick-and-morty")
@RequiredArgsConstructor
public class RickAndMortyController {
    private final RickAndMortyService rickAndMortyService;

    @GetMapping("/character")
    public RickAndMortyCharacter getCharacter() {
        return rickAndMortyService.getCharacter();
    }
}
