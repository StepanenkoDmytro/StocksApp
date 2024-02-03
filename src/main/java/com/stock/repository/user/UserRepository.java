package com.stock.repository.user;

import com.stock.model.user.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "userPortfolios")
    Optional<User> findFullByEmail(String name);
//@Query("SELECT DISTINCT u FROM USER u JOIN FETCH u.accounts a JOIN FETCH a.coins WHERE u.email = ?1")
//Optional<User> findFullByEmail(String email);


//    @EntityGraph(attributePaths = "roles")
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
