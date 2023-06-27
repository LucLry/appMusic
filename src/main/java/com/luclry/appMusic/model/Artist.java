package com.luclry.appMusic.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "Artist")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Artist {

    @Id
    private String id;
    private String name;
    private List<String> genres;

    private String imageUrl;
    @JsonProperty("images")
    private void unpackUrlImageUrlTextValue(JsonNode images){
        imageUrl = images.get(0).get("url").textValue();
    }


}
