package com.luclry.appMusic.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.luclry.appMusic.model.Artist;
import com.luclry.appMusic.service.MusicService;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

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
    public List<Artist> getArtistById(@RequestParam ArrayList<String> spotifyArtistIdList) {
        try {
            return musicService.getSpotifyArtistById(spotifyArtistIdList);
        } catch (Exception e) {
            System.out.println("Mistakes were made " + e);
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
