package com.nhom29.Model.ERD;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BinhLuan {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private ThongTin thongTin;
    @Column(columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime date;
    @Column(columnDefinition = "NTEXT", nullable = false)
    private String noidung;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "BinhLuanID")
    private Set<HinhAnh> hinhAnh  = new HashSet<>();
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn( name = "baidangId", nullable = false)
    private BaiDang baidang;
}