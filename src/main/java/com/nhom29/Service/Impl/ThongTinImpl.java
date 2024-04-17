package com.nhom29.Service.Impl;

import com.nhom29.Model.ERD.ThongTin;
import com.nhom29.Repository.TaiKhoanRepository;
import com.nhom29.Service.Inter.ThongTinInter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ThongTinImpl implements ThongTinInter {
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;
    @Override
    public Optional<ThongTin> layThongTin(Long id) {
        return taiKhoanRepository.findById(id);
    }
}
