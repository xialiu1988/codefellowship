package com.shuai88qi.xl2019.codefellowship;

import org.springframework.data.repository.CrudRepository;

public interface AppUserRepository extends CrudRepository<AppUser,Long> {
    AppUser findByUsername(String username);
}
