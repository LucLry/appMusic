package com.luclry.appMusic.model;



import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name= "Artist")
public class Artist {
    @Id
    private String id;
    private String name;
    private String[] genres;

}
