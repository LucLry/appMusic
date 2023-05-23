package com.luclry.appMusic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    private String tokenId;
    private Date expirationDate;
    private String clientId;
    private String clientSecret;
}
