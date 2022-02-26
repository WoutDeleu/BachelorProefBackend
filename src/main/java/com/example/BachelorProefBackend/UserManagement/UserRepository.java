package com.example.BachelorProefBackend.UserManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User_entity, Long> {
   @Query("select u from User_entity u where u.isStudent")
   List<User_entity> getAllStudents();

   @Query("select u from User_entity u where u.isAdministrator")
   List<User_entity> getAllAdministrators();

   @Query("select u from User_entity u where u.isPromotor")
   List<User_entity> getAllPromotors();

   @Query("select u from User_entity u where u.isCoordinator")
   List<User_entity> getAllCoordinators();


}
