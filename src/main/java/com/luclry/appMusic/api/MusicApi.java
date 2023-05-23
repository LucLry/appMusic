package com.luclry.appMusic.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.luclry.appMusic.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Map;

@RestController
public class MusicApi {


    // declare musicService bean

    private MusicService musicService;

    //Same thing as @AutoWired
    MusicApi(MusicService musicService){
        this.musicService = musicService;
    }
    MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();

    WebClient spotifyClient = WebClient.create("https://api.spotify.com");

    @GetMapping("/getToken")
    public String getSpotifyToken()  {
        try {
            return musicService.getSpotifyToken();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
