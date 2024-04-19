package com.nhom29.Cotnroller;

import com.nhom29.Model.ERD.ThongTin;
import com.nhom29.Service.Inter.MailInter;
import com.nhom29.Service.Inter.ThongTinInter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/xuli")
@RequiredArgsConstructor
@Slf4j
public class XuLiController {
    private final MailInter mailInter;
    private final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
    private String identify = new String();
    private final ThongTinInter thongTinInter;
    private ThongTin thongTin;

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
    @PostMapping("/email")
    public String guiMa(@RequestParam("emailReceiver") String emailReceiver){
        if( thongTinInter.layThongTInByEmail(emailReceiver).isEmpty()){
            return "redirect:/email?error=2";
        }
        thongTin = thongTinInter.layThongTInByEmail(emailReceiver).get();
        identify = random(6);
        mailInter.sendMail(emailReceiver, identify);
        return "redirect:/xuli/identify/" + emailReceiver;
    }

    @PostMapping("/maxacnhan")
    public String xacNhanIdentify(@RequestParam("maxacnhan") List<String> maxacnhan){
        String a = new String();
        for( String i : maxacnhan){
            a += i;
        }
        if( mailInter.accept(a, identify)){
            return "thayDoiMatKhau";
        }else{
            return "redirect:/email?error=1";
        }
    }

    @GetMapping("identify/{email}")
    public String xacNhan(@PathVariable String email){
        return "xacNhan";
    }
}
