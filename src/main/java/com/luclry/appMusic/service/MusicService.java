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
    WebClient spotifyClient = WebClient.create("https://api.spotify.com/v1");
    private final String clientId = "045e3e7ad6754a83994d803a0a2b8d83";
    private final String clientSecret = "9b5eed14d00a43e2a6dd4f92a5291b6c";


    /**
     * @return {String} spotify token to be used by spotify WS
     * @throws Exception
     * @Set values for {Date} spotifyTokenExpirationDate  and {String} spotifyToken
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

    /**
     *
     * Common function used for common spotify Get requests (only URI is declared)
     * @param {String} uri of ws with method get
     * @return {JsonNode} response of Get request
     * @throws Exception
     */
    public JsonNode spotifyGetRequest (String uri) throws Exception{
        JsonNode response = spotifyClient.get()
                .uri(uri)
                .header("Authorization", "Bearer " + getSpotifyToken())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
        return response;
    }
    public JsonNode getSpotifyArtistById(String spotifyArtistId) throws Exception {
        return spotifyGetRequest("/artists/" + spotifyArtistId);
    }

    public JsonNode getSpotifyUserProfileById(String spotifyUserId) throws Exception{
        return spotifyGetRequest("/users/" + spotifyUserId);
    }


}
