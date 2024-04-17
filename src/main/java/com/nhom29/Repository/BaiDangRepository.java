package com.nhom29.Repository;

import com.nhom29.Model.ERD.BaiDang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BaiDangRepository extends JpaRepository<BaiDang, Long> {
    @Query("select count(p) from BaiDang p")
    Integer getNumberPage();
}
