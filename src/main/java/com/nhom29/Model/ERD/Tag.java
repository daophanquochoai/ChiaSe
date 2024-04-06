package com.nhom29.Model.ERD;

import jakarta.persistence.*;
import java.util.Set;
import java.util.HashSet;
@Entity
public class Tag {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(columnDefinition = "varchar(50)")
    private String TenTag;

    @ManyToMany(mappedBy = "tag")
    private Set<BaiDang> baiDang = new HashSet<>();
}
