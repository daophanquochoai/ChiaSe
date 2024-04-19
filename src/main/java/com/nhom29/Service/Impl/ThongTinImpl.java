package com.nhom29.Service.Impl;

import com.nhom29.Model.ERD.ThongTin;
import com.nhom29.Repository.TaiKhoanRepository;
import com.nhom29.Service.Inter.ThongTinInter;
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

    @Override
    public Optional<ThongTin> layThongTinByUserName(String username) {
        return taiKhoanRepository.findByUsername(username);
    }

    @Override
    public Optional<ThongTin> layThongTInByEmail(String email) {
        return taiKhoanRepository.findByEmail(email);
    }
}
