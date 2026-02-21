package com.HelpTapProj.backEnd.repository;

import com.HelpTapProj.backEnd.model.User;
import com.HelpTapProj.backEnd.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;


import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findByNationalRegistration(String nationalRegistration);

    Optional<User> findByIdentifier(String identifier);

    boolean existsByEmail(String email);

    boolean existsByNationalRegistration(String nationalRegistration);

    boolean existsByIdentifier(String identifier);

    List<User> findByRole(UserRole role);
}
