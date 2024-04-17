package com.nhom29.Model.ERD;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaiKhoan_ThongTin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @OneToOne(mappedBy = "taiKhoanThongTin")
    private ThongTin thongtin;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn( name = "TaiKhoan_Id")
    private TaiKhoan taiKhoan;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn( name = "UyQuyen_ID")
    private UyQuyen uyQuyen ;
}
