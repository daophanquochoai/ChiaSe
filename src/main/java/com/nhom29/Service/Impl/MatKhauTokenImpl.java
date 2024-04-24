package com.nhom29.Service.Impl;

import com.nhom29.Model.ERD.ThongTin;
import com.nhom29.Model.QuenMatKhau.MatKhauToken;
import com.nhom29.Repository.MatKhauTokenRepository;
import com.nhom29.Service.Inter.MatKhauTokenInter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatKhauTokenImpl implements MatKhauTokenInter {
    private final MatKhauTokenRepository matKhauTokenRepo;
    @Override
    public void createPasswordResetTokenForUser(ThongTin thongTin, String token) {
        Optional<MatKhauToken> matKhauToken = matKhauTokenRepo.kiemTraTokenTonTai(thongTin.getId());
        MatKhauToken matkhau = new MatKhauToken();
        if( matKhauToken.isEmpty()){
            matkhau.setToken(token);
            matkhau.setThongTin(thongTin);
            matkhau.setThoihan(LocalDateTime.now());
            matkhau.setAceept(false);
            matKhauTokenRepo.save(matkhau);
        }else{
            matKhauToken.get().setToken(token);
            matKhauToken.get().setThoihan(LocalDateTime.now());
            matKhauToken.get().setAceept(false);
            matKhauTokenRepo.save(matKhauToken.get());
        }
    }

    @Override
    public void updateAceept(Boolean accept, Long id) {
        Optional<MatKhauToken> matKhauToken = matKhauTokenRepo.findById(id);
        if( matKhauToken.isEmpty()) throw new RuntimeException("Not found PasswordToken");
        matKhauToken.get().setAceept(accept);
        matKhauTokenRepo.save(matKhauToken.get());
    }
}
