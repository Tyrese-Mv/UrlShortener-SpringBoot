package com.TyreseMv.TinyUrl.persistance;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsByUsernameAndEmail(String username, String email);

    User findByEmail(String email);
}
