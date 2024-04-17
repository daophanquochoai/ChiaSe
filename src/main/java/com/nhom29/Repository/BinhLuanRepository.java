package com.nhom29.Repository;

import com.nhom29.Model.ERD.BinhLuan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BinhLuanRepository extends JpaRepository<BinhLuan, Long> {
}
