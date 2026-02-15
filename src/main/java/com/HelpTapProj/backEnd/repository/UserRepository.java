package com.HelpTapProj.backEnd.repository;

import com.HelpTapProj.backEnd.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;


import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    UserDetails findByEmail(String email);

    Optional<User> findByNationalRegistration(String nationalRegistration);

    Optional<User> findByIdentifier(String identifier);

    boolean existsByEmail(String email);

    boolean existsByNationalRegistration(String nationalRegistration);

    boolean existsByIdentifier(String identifier);

}
