package com.TyreseMv.TinyUrl.persistance;

import org.springframework.data.repository.CrudRepository;

public interface UrlRepository extends CrudRepository<Url, Long> {

    Url findByShortUrl(String shortUrl);

//    int getNumberOfUrlsByUser(User user);

}
