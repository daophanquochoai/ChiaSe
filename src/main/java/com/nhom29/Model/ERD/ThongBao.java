package com.nhom29.Model.ERD;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThongBao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "NguoiNhanId")
    private ThongTin nguoinhan;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn( name = "BaiDangId")
    private BaiDang baidang;
    @Column( columnDefinition = "TEXT", nullable = false)
    private String noidung;
    @Column( nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime thoigiantao;
}
