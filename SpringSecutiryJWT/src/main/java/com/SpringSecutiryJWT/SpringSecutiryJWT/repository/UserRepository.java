package com.SpringSecutiryJWT.SpringSecutiryJWT.repository;

import com.SpringSecutiryJWT.SpringSecutiryJWT.models.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long> {

    Optional<UserEntity> findByusername(String username);

   @Query("Select u FROM UserEntity u where u.username = ?1")
    Optional<UserEntity> getName(String username);
}
