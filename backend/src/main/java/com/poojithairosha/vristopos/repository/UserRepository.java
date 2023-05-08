package com.poojithairosha.vristopos.repository;

import com.poojithairosha.vristopos.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Page<User> findAllByNameContainingOrEmailContainingOrMobileContaining(String name, String email, String mobile, PageRequest pr);
}
