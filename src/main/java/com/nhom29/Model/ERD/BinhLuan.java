package com.nhom29.Model.ERD;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;
import java.util.HashSet;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BinhLuan {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @OneToOne
    @JoinColumn(name = "UserID")
    private ThongTin thongTin;
    @Column(columnDefinition = "DATE")
    private LocalDate date;
    @Column(columnDefinition = "TEXT")
    private String noidung;
    @OneToMany
    @JoinColumn(name = "BinhLuanID")
    private Set<HinhAnh> hinhAnh  = new HashSet<>();
}
