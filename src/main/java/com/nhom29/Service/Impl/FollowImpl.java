package com.nhom29.Service.Impl;

import com.nhom29.Model.ERD.BaiDang;
import com.nhom29.Model.ERD.ThongTin;
import com.nhom29.Repository.BaiDangRepository;
import com.nhom29.Repository.ThongTinRepository;
import com.nhom29.Service.Inter.FollowInter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowImpl implements FollowInter {
    private final BaiDangRepository baiDangRepo;
    private final ThongTinRepository thongTinRepo;

    @Override
    public void follow(Long baidangId, Long thongtinId) {
        Optional<BaiDang> baiDang = baiDangRepo.findById(baidangId);
        if( baiDang.isEmpty() ) throw new RuntimeException("Not found baidang");
        Optional<ThongTin> thongTin = thongTinRepo.findById(thongtinId);
        if( thongTin.isEmpty() ) throw new RuntimeException("Not found thongtin");
        baiDang.get().getLuu().add(thongTin.get());
        baiDangRepo.save(baiDang.get());
    }

    @Override
    public void unfollow(Long baidangId, Long thongtinId) {
        Optional<BaiDang> baiDang = baiDangRepo.findById(baidangId);
        if( baiDang.isEmpty() ) throw new RuntimeException("Not found baidang");
        Optional<ThongTin> thongTin = thongTinRepo.findById(thongtinId);
        if( thongTin.isEmpty() ) throw new RuntimeException("Not found thongtin");
        baiDang.get().getLuu().add(thongTin.get());
        baiDang.get().setLuu(baiDang.get().getLuu().stream().filter(t -> t.getId() != thongtinId).collect(Collectors.toList()));
        baiDangRepo.save(baiDang.get());
    }

    @Override
    public void like(Long baidangId, Long thongtinId) {
        Optional<BaiDang> baiDang = baiDangRepo.findById(baidangId);
        if( baiDang.isEmpty() ) throw new RuntimeException("Not found baidang");
        Optional<ThongTin> thongTin = thongTinRepo.findById(thongtinId);
        if( thongTin.isEmpty() ) throw new RuntimeException("Not found thongtin");
        baiDang.get().getLike().add(thongTin.get());
        baiDangRepo.save(baiDang.get());
    }

    @Override
    public void unlike(Long baidangId, Long thongtinId) {
        Optional<BaiDang> baiDang = baiDangRepo.findById(baidangId);
        if( baiDang.isEmpty() ) throw new RuntimeException("Not found baidang");
        Optional<ThongTin> thongTin = thongTinRepo.findById(thongtinId);
        if( thongTin.isEmpty() ) throw new RuntimeException("Not found thongtin");
        baiDang.get().getLike().add(thongTin.get());
        baiDang.get().setLike(baiDang.get().getLike().stream().filter(t -> t.getId() != thongtinId).collect(Collectors.toList()));
        baiDangRepo.save(baiDang.get());
    }
}
