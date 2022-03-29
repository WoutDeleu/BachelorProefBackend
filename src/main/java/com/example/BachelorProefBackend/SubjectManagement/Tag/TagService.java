package com.example.BachelorProefBackend.SubjectManagement.Tag;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }

    @GetMapping
    public List<Tag> getAllTags() {return tagRepository.findAll();}

    @GetMapping
    public Tag getTagById(long id) {return tagRepository.getById(id);}

    @PostMapping
    public void addNewTag(Tag tag) {
        tagRepository.save(tag);
    }

    @DeleteMapping
    public void deleteTag(long id){
        tagRepository.deleteById(id);
    }

    @PutMapping
    public void updateTag(long id, String name){
        if(!tagRepository.existsById(id)) throw new IllegalStateException("Tag does not exist (id: "+id+")");
        Tag tag = tagRepository.getById(id);
        if(name != null && name.length()>0) tag.setName(name);
    }
}
