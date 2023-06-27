package com.luclry.appMusic.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.luclry.appMusic.model.Artist;
import org.springframework.http.MediaType;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;


import java.net.URI;
import java.util.*;

@Service
public class MusicService {

    private String spotifyToken;
    private Date spotifyTokenExpirationDate;
    WebClient spotifyClient = WebClient.create("https://api.spotify.com/v1");
    private final String clientId = "045e3e7ad6754a83994d803a0a2b8d83";
    private final String clientSecret = "9b5eed14d00a43e2a6dd4f92a5291b6c";
    private final int maxIdsForOneGet = 2;


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
     * Split a set into subset of inputed size (max)
     *
     * @param {Set<String>} original : original set that will be split
     * @param {int} subsetSize : max number of elements in each subsets
     * @return {List<Set<String>>} List of subsets with size <= subsetSize
     */
    public static List<Set<String>> splitSetIntoSubsets(Set<String> original, int subsetSize){

        if (subsetSize<=0) {
            throw new IllegalArgumentException("Incorrect subset size");
        }

        if (original == null || original.isEmpty()){
            return Collections.emptyList();
        }
        List<Set<String>> setList = new ArrayList<>();
        Iterator<String> it = original.iterator();

        int subsetsCount = (int) Math.ceil((double) original.size()/subsetSize);
        for (int i = 0; i < subsetsCount; i++){
            HashSet<String> s = new HashSet<String>();

            for (int j = 0; j < subsetSize && it.hasNext(); j++) {
                s.add(it.next());
            }
            setList.add(s);
        }
        return setList;
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

        public List<Artist> getSpotifyArtistById(List<String> spotifyArtistIdList) throws Exception {
        Set<String> spotifyArtistIdSet = new HashSet<>(spotifyArtistIdList);

        ObjectMapper mapper = new ObjectMapper();

        List<Set<String>> artistSubsets = splitSetIntoSubsets(spotifyArtistIdSet, maxIdsForOneGet);

        List<Artist> artistList = new ArrayList<Artist>();

        for (int i = 0; i < artistSubsets.size(); i++){
            String spotifyArtistIds = String.join(",", artistSubsets.get(i));

            JsonNode response = spotifyGetRequest("/artists?ids=" + spotifyArtistIds);
            List<Artist> bufferList = mapper.readValue(response.get("artists").toString(), new TypeReference<List<Artist>>(){}); // Convert Json array to a list of artists

            artistList.addAll(bufferList); // addAll merges response array into resultArray
        }

        ObjectNode resFinal = mapper.createObjectNode();
        resFinal.put("artists", artistList.toString());

        return artistList;
    }

//    public JsonNode getSpotifyArtistById(List<String> spotifyArtistIdList) throws Exception {
//        Set<String> spotifyArtistIdSet = new HashSet<>(spotifyArtistIdList);
//
//        ObjectMapper mapper = new ObjectMapper();
//        ArrayNode resultArray = mapper.createArrayNode();
//
//        List<Set<String>> artistSubsets = splitSetIntoSubsets(spotifyArtistIdSet, maxIdsForOneGet);
//
//        for (int i = 0; i < artistSubsets.size(); i++){
//            String spotifyArtistIds = String.join(",", artistSubsets.get(i));
//            JsonNode response = spotifyGetRequest("/artists?ids=" + spotifyArtistIds);
//
//            resultArray.addAll((ArrayNode) response.get("artists")); // addAll merges response array into resultArray
//        }
//
//        ObjectNode resFinal = mapper.createObjectNode();
//        resFinal.put("artists", resultArray);
//
//        return resFinal;
//    }

    public JsonNode ExpectedAnswer(List<String> spotifyArtistIdList) throws Exception {
        Set<String> spotifyArtistIdSet = new HashSet<>(spotifyArtistIdList);

        String spotifyArtistIds = String.join(",", spotifyArtistIdSet);
        return spotifyGetRequest("/artists?ids=" + spotifyArtistIds);
    }

    public JsonNode getSpotifyUserProfileById(String spotifyUserId) throws Exception{
        return spotifyGetRequest("/users/" + spotifyUserId);
    }


}
