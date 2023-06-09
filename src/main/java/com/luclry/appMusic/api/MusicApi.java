package com.luclry.appMusic.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.luclry.appMusic.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class MusicApi {


    // declare musicService bean

    private MusicService musicService;

    //Same thing as @AutoWired
    MusicApi(MusicService musicService) {
        this.musicService = musicService;
    }

    MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();

    WebClient spotifyClient = WebClient.create("https://api.spotify.com");

    @GetMapping("/getArtist")
    public JsonNode getArtistById(@RequestParam ArrayList<String> spotifyArtistIdList) {
        try {
            return musicService.getSpotifyArtistById(spotifyArtistIdList);
        } catch (Exception e) {
            System.out.println("Erreur : " + e);
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getUser/{spotifyUserId}")
    public JsonNode getSpotifyUserById(@PathVariable String spotifyUserId) {
        try {
            return musicService.getSpotifyUserProfileById(spotifyUserId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getToken")
    public String getToken(){
        try{
            return musicService.getSpotifyToken();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
