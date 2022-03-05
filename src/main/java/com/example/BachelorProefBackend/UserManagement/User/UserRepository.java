package com.example.BachelorProefBackend.UserManagement.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User_entity, Long> {

   User_entity findById(long id);
   User_entity findByEmail(String email); //email act as username

   List<User_entity> findAll();

   List<User_entity> findUser_entityByRolesId(long roleId);


}
