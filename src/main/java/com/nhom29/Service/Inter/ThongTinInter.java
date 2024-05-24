package com.nhom29.Service.Inter;

import com.nhom29.DTO.pageUsers_ThongTin;
import com.nhom29.Model.ERD.ThongTin;

import java.util.Optional;

public interface ThongTinInter {
    Optional<ThongTin> layThongTin(Long id);
    Optional<ThongTin> layThongTinByUserName( String username);
    Optional<ThongTin> layThongTInByEmail(String email);
    void updateThongTin(ThongTin thongTin);
    void luuThongTin( ThongTin thongTin);
    pageUsers_ThongTin layThongTinTheoPageVaQ(Integer page, String q);
}
