package com.nhom29.Service.Impl;
import com.nhom29.Model.ERD.Tag;
import com.nhom29.Repository.TagRepository;
import com.nhom29.Service.Inter.TagInter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagService implements TagInter {
    // contructor repo
    private final TagRepository tagRepo;
    @Override
    public List<Tag> getAllTag() {
        return tagRepo.findAll();
    }

    @Override
    public Tag saveTag(String tag, List<Tag> tags) {
        try{
            Optional<Tag> t = tags.stream().filter( temp -> temp.getTenTag().trim().equals(tag)).findFirst();
            if( t.isPresent() ) return t.get();
            else{
                Tag tagNew = new Tag();
                tagNew.setTenTag(tag);
                return tagRepo.save(tagNew);
            }
        }catch (Exception ex){
            log.warn(ex.getMessage());
            return null;
        }
    }

    @Override
    public Tag getTagByName(String name) {
        Optional<Tag> tag = tagRepo.findTagByName(name);
        if( tag.isEmpty()) return null;
        return tag.get();
    }
}
