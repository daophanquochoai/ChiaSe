package com.nhom29.Service.Inter;

import com.nhom29.Model.ERD.Tag;

import java.util.List;

public interface TagInter {
    List<Tag> getAllTag();
    Tag saveTag(String tag, List<Tag> tags);
    Tag getTagByName(String name);
}
