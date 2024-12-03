package ru.itmentor.spring.boot_security.demo.repository;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    // READ USER by username
    @Transactional(readOnly = true)
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "user_entity-graph")
    User findByUsername(String username);

    // READ ALL USERS with ROLES
    @Transactional(readOnly = true)
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "user_entity-graph")
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles")
    List<User> findAllWithRoles();
}

