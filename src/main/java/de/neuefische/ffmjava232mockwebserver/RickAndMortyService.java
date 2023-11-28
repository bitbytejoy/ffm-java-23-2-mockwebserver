package de.neuefische.ffmjava232mockwebserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Service
public class RickAndMortyService {
    private WebClient webClient;

    public RickAndMortyService(@Value("${rickandmorty.base.url}") String baseUrl) {
        webClient = WebClient.create(baseUrl);
    }

    public RickAndMortyCharacter getCharacter() {
        RickAndMortyCharacter character = Objects.requireNonNull(
                webClient
                        .get()
                        .uri("/api/character/2")
                        .retrieve()
                        .toEntity(RickAndMortyCharacter.class)
                        .block()
        ).getBody();

        return character;
    }
}
