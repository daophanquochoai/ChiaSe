package com.nhom29.Model.ERD;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ThongBao_ThongTin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "thongTin")
    private ThongTin thongTin;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "thongBao")
    private ThongBao thongbao;
    private Boolean status;
}
