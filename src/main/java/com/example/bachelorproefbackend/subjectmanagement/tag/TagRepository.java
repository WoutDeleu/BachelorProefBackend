package com.example.bachelorproefbackend.subjectmanagement.tag;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

    boolean existsTagByName(String name);
    Tag getTagByName(String name);

}
