package com.luclry.appMusic.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

@Service
public class MusicService {

    private String spotifyToken;
    private Date spotifyTokenExpirationDate;
    WebClient spotifyClient = WebClient.create("https://api.spotify.com");
    private final String clientId = "045e3e7ad6754a83994d803a0a2b8d83";
    private final String clientSecret = "9b5eed14d00a43e2a6dd4f92a5291b6c";


    /**
     * @return {String} spotify token to be used by other WS
     * @throws Exception
     * @Set values for {Date} spotifyTokenExpirationDate  and {String} spotifyToken
     *
     */
    public String getSpotifyToken() throws Exception {

        if (spotifyToken == null || spotifyTokenExpirationDate.before(new Date())) {
            MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();

            bodyValues.add("grant_type", "client_credentials");
            bodyValues.add("client_id", clientId);
            bodyValues.add("client_secret", clientSecret);

            JsonNode response = spotifyClient.post()
                    .uri(new URI("https://accounts.spotify.com/api/token"))
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromFormData(bodyValues))
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

            spotifyToken = response.get("access_token").asText();
            spotifyTokenExpirationDate = new Date((new Date()).getTime() + response.get("expires_in").asInt() * 1000);
        }

        return spotifyToken;
    }

    public JsonNode getSpotifyArtistById(String spotifyArtistId) throws Exception {

        JsonNode response = spotifyClient.get()
                .uri("/v1/artists/" + spotifyArtistId)
                .header("Authorization", "Bearer " + getSpotifyToken())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
        return response;
    }

    public JsonNode getSpotifyUserProfile(String spotifyUserId) throws Exception{
        JsonNode response = spotifyClient.get()
                .uri("/v1/users/" + spotifyUserId)
                .header("Authorization", "Bearer " + getSpotifyToken())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
        return response;
    }
}
