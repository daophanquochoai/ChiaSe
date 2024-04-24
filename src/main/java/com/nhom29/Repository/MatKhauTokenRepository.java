package com.nhom29.Repository;

import com.nhom29.Model.QuenMatKhau.MatKhauToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface MatKhauTokenRepository extends JpaRepository<MatKhauToken, Long> {
    @Query("SELECT m FROM MatKhauToken m WHERE m.token = :manhandle AND m.thongTin.id = :id AND m.thoihan <= :thoihan")
    Optional<MatKhauToken> xacnhan(@Param("manhandle") String manhandle, @Param("id") Long id, @Param("thoihan") LocalDateTime thoihan);

    @Query("SELECT m FROM MatKhauToken m where m.aceept is true and m.thongTin.id = :id")
    Optional<MatKhauToken> kiemTraThayDoi( Long id);
    @Query("select m from MatKhauToken m where m.thongTin.id = :id")
    Optional<MatKhauToken> kiemTraTokenTonTai(Long id);

}
