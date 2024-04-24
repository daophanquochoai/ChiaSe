package com.nhom29.Service.Impl;

import com.nhom29.Cotnroller.HomeController;
import com.nhom29.Model.ERD.BaiDang;
import com.nhom29.Repository.BaiDangRepository;
import com.nhom29.Service.Inter.BaiDangInter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class BaiDangImpl implements BaiDangInter {
    @Autowired
    private BaiDangRepository baiDangRepository;

    @Override
    public Page<BaiDang> timBaiDangPhanTrang(int offset, int pageSize, String feild) {
        return baiDangRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(feild).descending()));

    }

    @Override
    public Optional<BaiDang> saveBaiDang(BaiDang baiDang) {
        return Optional.of(baiDangRepository.save(baiDang));
    }

    @Override
    public Integer getNumberPage() {
        Integer number = baiDangRepository.getNumberPage();
        return  (int) Math.ceil((double) number / (double) HomeController.numberPage) == 0 ? 1 : (int) Math.ceil((double) number / (double) HomeController.numberPage);
    }

    @Override
    public Optional<BaiDang> layChiTietBaiDang(Long id) {
        return baiDangRepository.findById(id);
    }
}
