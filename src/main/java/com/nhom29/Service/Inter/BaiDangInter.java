package com.nhom29.Service.Inter;

import com.nhom29.Model.ERD.BaiDang;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface BaiDangInter {
    Page<BaiDang> timBaiDangPhanTrang(int offset, int pageSize, String feild);
    Optional<BaiDang> saveBaiDang(BaiDang baiDang);
    Integer getNumberPage();

}
