package com.nhom29.Service.Impl;

import com.nhom29.DTO.Pagination;
import com.nhom29.DTO.pageUsers_ThongTin;
import com.nhom29.Model.ERD.ThongTin;
import com.nhom29.Repository.ThongTinRepository;
import com.nhom29.Service.Inter.ThongTinInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Override
    public pageUsers_ThongTin layThongTinTheoPageVaQ(Integer page, String q) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<ThongTin> list;
        if (q == null || q.isEmpty()) {
            q = "";
        }
        list = thongTinRepository.findAllUserWithQ(q, pageRequest);
        return new pageUsers_ThongTin(list, new Pagination(list.getTotalPages(),page+1));
    }
}
