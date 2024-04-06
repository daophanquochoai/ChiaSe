package com.nhom29.Model.ERD;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;
import java.util.HashSet;
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ThongTin {
    @Id
    @Column(columnDefinition = "varchar(255)")
    private String Email;
    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String Ho;
    @Column(columnDefinition = "varchar(20)", nullable = false)
    private String Ten;
    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String Truong;
    @Column(columnDefinition = "varchar(10)")
    private String Sdt;
    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String AnhDaiDien;
    @Column(columnDefinition = "varchar(500)", nullable = false)
    private String GioiThieu;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Email_ID")
    private TaiKhoan_ThongTin taiKhoanThongTin;
    @ManyToMany(mappedBy = "luu")
    private Set<BaiDang> baiDang_Luu = new HashSet<>();
    @ManyToMany(mappedBy = "like")
    private Set<BaiDang> baiDang_Like = new HashSet<>();
}
