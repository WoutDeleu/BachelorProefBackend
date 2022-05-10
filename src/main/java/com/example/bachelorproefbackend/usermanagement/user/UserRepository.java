package com.example.bachelorproefbackend.usermanagement.user;

import com.example.bachelorproefbackend.usermanagement.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

   UserEntity findById(long id);

   UserEntity findByEmail(String email); //email act as username

   boolean existsByEmail(String email);

   List<UserEntity> findAll();

   List<UserEntity> findUserEntityByRolesId(long roleId);

   int countUserEntitiesByRolesContaining(Role role);

   int countUserEntitiesByFinalSubjectIsNotNull();



}
