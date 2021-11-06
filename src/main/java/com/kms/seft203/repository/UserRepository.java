package com.kms.seft203.repository;

import com.kms.seft203.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query("select u from User u where u.email = ?1")
    Optional<User> findByEmail(String email);

    @Query("update User u set u.enabled = true where u.id = ?1")
    @Modifying
    void enabledAccount(Integer id);

    @Query("select u from User u where u.verificationCode = ?1")
    Optional<User> findByVerificationCode(String code);
}
