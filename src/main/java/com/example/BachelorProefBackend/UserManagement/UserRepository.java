package com.example.BachelorProefBackend.UserManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User_entity, Long> {
   @Query(value = "select * from User_entity u where u.is_student", nativeQuery = true)
   List<User_entity> getAllStudents();

   @Query(value = "select * from User_entity u where u.is_administrator", nativeQuery = true)
   List<User_entity> getAllAdministrators();

   @Query(value = "select * from User_entity u where u.is_promotor", nativeQuery = true)
   List<User_entity> getAllPromotors();

   @Query(value = "select * from User_entity u where u.is_coordinator", nativeQuery = true)
   List<User_entity> getAllCoordinators();


}
