package com.nhom29.Model.ERD;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BaiDang {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "nvarchar(255)")
    private String tieude;
    @Column(columnDefinition = "nvarchar(500)")
    private String noidung;
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime thoigiantao;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "BaiDang_ID")
    private Set<HinhAnh> hinhAnh = new HashSet<>();
    @ManyToMany( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "BaiDang_Tag",
            joinColumns = {
                    @JoinColumn(name = "BaiDang_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "Id")
            }
    )
    private List<Tag> tag = new ArrayList<>();
    @ManyToMany( fetch = FetchType.EAGER)
    @JoinTable(
            name = "BaiDang_User",
            joinColumns = {
                    @JoinColumn(name = "BaiDang_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "Email")
            }
    )
    private List<ThongTin> luu = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "BaiDang_User_Like",
            joinColumns = {
                    @JoinColumn(name = "BaiDang_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "Email")
            }
    )
    private List<ThongTin> like = new ArrayList<>();

    @OneToMany( fetch = FetchType.EAGER)
    @JoinColumn(name = "BinhLuan")
    private List<BinhLuan> binhluan = new ArrayList<>();
    @ManyToOne
    @Cascade({org.hibernate.annotations.CascadeType.DETACH})
    @JoinColumn(name = "NguoiDang")
    private ThongTin thongTin;

}
