package de.neuefische.ffmjava232mockwebserver;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RickAndMortyTests {
    private static MockWebServer mockWebServer;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public static void beforeAll() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    public static void afterAll() throws IOException {
        mockWebServer.shutdown();
    }

    @DynamicPropertySource
    static void backendProperties(DynamicPropertyRegistry registry) {
        registry.add("rickandmorty.base.url", () -> mockWebServer.url("/").toString());
    }

    @Test
    public void whenGetCharacter_thenReturnCharacter() throws Exception {
        // GIVEN
        mockWebServer.enqueue(new MockResponse()
                .setBody("""
                            {
                               "id": 2,
                               "name": "Morty Smith",
                               "status": "Alive",
                               "species": "Human",
                               "type": "",
                               "gender": "Male",
                               "origin": {
                                 "name": "Earth",
                                 "url": "https://rickandmortyapi.com/api/location/1"
                               },
                               "location": {
                                 "name": "Earth",
                                 "url": "https://rickandmortyapi.com/api/location/20"
                               },
                               "image": "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
                               "episode": [
                                 "https://rickandmortyapi.com/api/episode/1",
                                 "https://rickandmortyapi.com/api/episode/2"
                               ],
                               "url": "https://rickandmortyapi.com/api/character/2",
                               "created": "2017-11-04T18:50:21.651Z"
                             }
                        """)
                .addHeader("Content-Type", "application/json"));

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/rick-and-morty/character"))
                // THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "id": 2,
                            "name": "Morty Smith",
                            "origin": {
                              "name": "Earth",
                              "url": "https://rickandmortyapi.com/api/location/1"
                            },
                            "episode": [
                              "https://rickandmortyapi.com/api/episode/1",
                              "https://rickandmortyapi.com/api/episode/2"
                            ]
                          }
                        """));
    }
}
