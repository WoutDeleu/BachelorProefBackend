package com.example.bachelorproefbackend.usermanagement.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

   UserEntity findById(long id);
   UserEntity findByEmail(String email); //email act as username

   List<UserEntity> findAll();

   List<UserEntity> findUser_entityByRolesId(long roleId);



}
