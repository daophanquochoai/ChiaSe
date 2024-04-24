package com.nhom29.Service.Inter;

import com.nhom29.Model.ERD.ThongTin;

public interface MatKhauTokenInter {
    void createPasswordResetTokenForUser(ThongTin thongTin, String token);
    void updateAceept( Boolean accept, Long id);
}
