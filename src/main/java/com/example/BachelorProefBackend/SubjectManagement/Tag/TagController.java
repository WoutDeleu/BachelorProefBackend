package com.example.BachelorProefBackend.SubjectManagement.Tag;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path="subjectManagement/tag")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController (TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<Tag> getAllTags() {return tagService.getAllTags();}

    @PostMapping
    public void addNewTag(@RequestParam String name){
        tagService.addNewTag(new Tag(name));
    }

    @DeleteMapping(path="{tagId}")
    public void deleteTag(@PathVariable("tagId") long id){
        tagService.deleteTag(id);
    }

    @PutMapping(path="{tagId}")
    public void updateTag(@PathVariable("tagId") long id,
                             @RequestParam(required = false) String name){
        tagService.updateTag(id, name);
    }
}
