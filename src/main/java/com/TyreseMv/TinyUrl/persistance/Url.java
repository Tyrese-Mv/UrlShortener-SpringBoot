package com.TyreseMv.TinyUrl.persistance;

import jakarta.persistence.*;

@Entity
public class Url {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;


    private String longUrl;

    @Column(unique = true)
    private String shortUrl;

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    public void setUser(User user) {
        this.user = user;
    }
}
