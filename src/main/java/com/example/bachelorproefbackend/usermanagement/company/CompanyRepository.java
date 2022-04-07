package com.example.bachelorproefbackend.usermanagement.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company findById(long id);

    @Query(value="SELECT c FROM Company c WHERE c.approved = false")
    List<Company> findAllNonApproved();


}
