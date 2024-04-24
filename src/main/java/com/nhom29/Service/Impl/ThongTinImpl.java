package com.nhom29.Service.Impl;

import com.nhom29.Model.ERD.ThongTin;
import com.nhom29.Repository.ThongTinRepository;
import com.nhom29.Service.Inter.ThongTinInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ThongTinImpl implements ThongTinInter {
    @Autowired
    private ThongTinRepository thongTinRepository;
    @Override
    public Optional<ThongTin> layThongTin(Long id) {
        return thongTinRepository.findById(id);
    }

    @Override
    public Optional<ThongTin> layThongTinByUserName(String username) {
        return thongTinRepository.findByUsername(username);
    }

    @Override
    public Optional<ThongTin> layThongTInByEmail(String email) {
        return thongTinRepository.findByEmail(email);
    }

    @Override
    public void updateThongTin(ThongTin thongTin) {
        thongTinRepository.save(thongTin);
    }

    @Override
    public void luuThongTin(ThongTin thongTin) {
        thongTinRepository.save(thongTin);
    }
}
