package com.nhom29.Service.Inter;

import com.nhom29.Model.ERD.ThongTin;
import java.util.Optional;

public interface ThongTinInter {
    public Optional<ThongTin> layThongTin(Long id);
}