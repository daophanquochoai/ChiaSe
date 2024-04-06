package com.nhom29.Model.ERD;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.util.HashSet;
import java.util.Set;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BaiDang {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(columnDefinition = "nvarchar(255)")
    private String TieuDe;
    @Column(columnDefinition = "nvarchar(500)")
    private String NoiDung;
    @Column(columnDefinition = "DATE")
    private LocalDate ThoiGianTao;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "HinhAnh_Id")
    private Set<HinhAnh> hinhAnh = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "BaiDang_Tag",
            joinColumns = {
                    @JoinColumn(name = "BaiDang_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "Id")
            }
    )
    private Set<Tag> tag = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "BaiDang_User",
            joinColumns = {
                    @JoinColumn(name = "BaiDang_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "Email")
            }
    )
    private Set<ThongTin> luu = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "BaiDang_User_Like",
            joinColumns = {
                    @JoinColumn(name = "BaiDang_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "Email")
            }
    )
    private Set<ThongTin> like = new HashSet<>();

    @OneToMany
    @JoinColumn(name = "BinhLuan")
    private Set<BinhLuan> binhLuans = new HashSet<>();

}
