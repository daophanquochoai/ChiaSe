package com.nhom29.Cotnroller;

import com.nhom29.Configuration.Security;
import com.nhom29.DTO.ThongTinDangKi;
import com.nhom29.Model.ERD.TaiKhoan;
import com.nhom29.Model.ERD.TaiKhoan_ThongTin;
import com.nhom29.Model.ERD.ThongTin;
import com.nhom29.Service.Inter.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Optional;

@Controller
@RequestMapping("/xuli")
@RequiredArgsConstructor
@Slf4j
public class XuLiController {
    private final MailInter mailInter;
    private final Security security;
    private final ThongTinInter thongTinInter;
    private final TaiKhoanInter taiKhoanInter;
    private final UyQuyenInter uyQuyenInter;
    private final CloudinaryInter cloudinaryInter;
    private final MatKhauTokenInter matKhauTokenInter;
    private final FollowInter followInter;

    // string random identify
    private final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
    // create regex check email
    private final String Email_Regex = "^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    private final Pattern pattern =Pattern.compile(Email_Regex);
    // save info identyfi and user need change password

    public String random( int len){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for( int i  = 0 ; i < len ; i++ ){
            int randomNumber = random.nextInt(CHARACTERS.length());
            char ramdomChar = CHARACTERS.charAt(randomNumber);
            sb.append(ramdomChar);
        }
        return sb.toString();
    }
//    đã nhập email vào và post lên kiểm tra
    @PostMapping("/email")
    public String guiMa(@RequestParam(value = "emailReceiver") String emailReceiver){
        if( thongTinInter.layThongTInByEmail(emailReceiver).isEmpty()){
            return "redirect:/email?error=2";
        }
        ThongTin thongTin = thongTinInter.layThongTInByEmail(emailReceiver).get();
        String identify = random(6);
        matKhauTokenInter.createPasswordResetTokenForUser(thongTin, identify);
        mailInter.sendMail(emailReceiver, identify);
        return "redirect:/xuli/identify/" + thongTin.getId();
    }

    @PostMapping("/maxacnhan/{id}")
    public String xacNhanIdentify(@RequestParam("maxacnhan") List<String> maxacnhan,
                                  @PathVariable("id") Long id, Model model){
        String a = new String();
        for( String i : maxacnhan){
            a += i;
        }
        Long acceptState = mailInter.accept(a, id);
        if( acceptState != 0){
            model.addAttribute("thongtin", thongTinInter.layThongTin(id));
            model.addAttribute("matkhauToken", acceptState);
            matKhauTokenInter.updateAceept(Boolean.TRUE, acceptState);
            return "thayDoiMatKhau";
        }else{
            return "redirect:/email?error=1";
        }
    }

    @GetMapping("identify/{id}")
    public String xacNhan(@PathVariable String id, Model model){
        model.addAttribute("id", id);
        return "xacNhan";
    }

    @PostMapping("doimatkhau")
    @Transactional
    public String doiMatKhau(
            @RequestParam("xacnhan") String xacnhan,
            @RequestParam("id") Long id,
            @RequestParam("token") Long token
    ){
        try{
            Optional<ThongTin> thongtin = thongTinInter.layThongTin(id);
            if( thongtin.isEmpty()) return "redirect:/email?error=3";
            if( mailInter.check(id) ) {
                matKhauTokenInter.updateAceept(false, token);
                return "redirect:/login";
            }
            return "redirect:/email?error=3";
        }catch (Exception ex){
            return "redirect:/email?error=3";
        }
    }

    // xu li tao tai khoan
    @PostMapping("/taotaikhoan")
    @Transactional
    public String taoTaiKhoan(
            @ModelAttribute("thongtin") ThongTinDangKi thongTinDangKi
            ){
        ThongTin thongTin = new ThongTin();
        Integer index = thongTinDangKi.getHovaten().lastIndexOf(" ");
        if( index > 0 ){
            thongTin.setTen(thongTinDangKi.getHovaten().substring(index+1));
            thongTin.setHo(thongTinDangKi.getHovaten().substring(0, index));
        }else{
            thongTin.setTen(thongTinDangKi.getHovaten().substring(index+1));
        }
        if( thongTinDangKi.getSodienthoai().length() == 10 && thongTinDangKi.getSodienthoai().charAt(0) == '0'){
            thongTin.setSdt(thongTinDangKi.getSodienthoai());
        }else{
            return "redirect:/taotaikhoan?error=sdt";
        }
        if( thongTinDangKi.getEmail() != null ){
            Matcher matcher = pattern.matcher(thongTinDangKi.getEmail());
            if( matcher.matches()){
                thongTin.setEmail(thongTinDangKi.getEmail());
            }else{
                return "redict:/taotaikhoan?error=email";
            }
        }
        if( thongTinDangKi.getTruonghoc()!=null){
            thongTin.setTruong(thongTinDangKi.getTruonghoc());
        }
        if( thongTinDangKi.getGioithieuveminh() != null){
            thongTin.setGioiThieu(thongTinDangKi.getGioithieuveminh());
        }
        TaiKhoan taiKhoan = new TaiKhoan();
        if( thongTinDangKi.getTaikhoan() != null ){
            if(taiKhoanInter.taiKhoanTrung(thongTinDangKi.getTaikhoan())){
                taiKhoan.setUsername(thongTinDangKi.getTaikhoan());
            }else return "redirect:/taotaikhoan?error=username";
        }else return "redirect:/taotaikhoan?error=username";
        if( thongTinDangKi.getMatkhau() != null){
            taiKhoan.setPassword(security.passwordEncoder().encode(thongTinDangKi.getMatkhau()));
        }else{
            return "redirect:/taotaikhoan?error=password";
        }
        thongTin.setProviderId("local");
        taiKhoan.setActive(true);
        if( uyQuyenInter.layQuyenUser("USER").isEmpty() ) return "redirect:/taotaikhoan?error=uyquyen";
        TaiKhoan_ThongTin taiKhoanThongTin = new TaiKhoan_ThongTin();
        taiKhoanThongTin.setUyQuyen(uyQuyenInter.layQuyenUser("USER").get());
        taiKhoanThongTin.setTaiKhoan(taiKhoan);
        thongTin.setTaiKhoanThongTin(taiKhoanThongTin);
        if( thongTinDangKi.getAvatar() == null){
            thongTin.setAnhDaiDien("https://kynguyenlamdep.com/wp-content/uploads/2022/06/avatar-con-ma-cute.jpg");
        }else{
            try{
                if( !thongTinDangKi.getAvatar().isEmpty()){
                    String url = cloudinaryInter.uploadFile(thongTinDangKi.getAvatar());
                    thongTin.setAnhDaiDien(url);
                }
            }catch (Exception ex){
                log.warn("{}", ex.getMessage());
            }
        }
        thongTinInter.luuThongTin(thongTin);
        return "redirect:/login";
    }
    @GetMapping("/follow/{baidangId}/{thongtinId}/{option}")
    public String follow(
            @PathVariable("baidangId") Long baidangId,
            @PathVariable("thongtinId") Long thongtinId,
            @PathVariable("option") Integer option
            ){
        try{
            if( option == 1){
                followInter.follow(baidangId, thongtinId);
            }else{
                followInter.unfollow(baidangId, thongtinId);
            }
            return "redirect:/question/" + baidangId;
        }catch (Exception ex){
            return "redirect:/error";
        }
    }
    @GetMapping("/like/{baidangId}/{thongtinId}/{option}")
    public String like(
            @PathVariable("baidangId") Long baidangId,
            @PathVariable("thongtinId") Long thongtinId,
            @PathVariable("option") Integer option
    ){
        try{
            if( option == 1){
                followInter.like(baidangId, thongtinId);
            }else{
                followInter.unlike(baidangId, thongtinId);
            }
            return "redirect:/question/" + baidangId;
        }catch (Exception ex){
            return "redirect:/error";
        }
    }
}

