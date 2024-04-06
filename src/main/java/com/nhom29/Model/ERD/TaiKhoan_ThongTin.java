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
    private String email;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn( name = "TaiKhoan_Id")
    private Set<TaiKhoan> taiKhoan = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn( name = "UyQuyen_ID")
    private Set<UyQuyen> uyQuyen = new HashSet<>();
}
