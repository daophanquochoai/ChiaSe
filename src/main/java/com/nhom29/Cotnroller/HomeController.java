package com.nhom29.Cotnroller;

import com.nhom29.DTO.BaiDangDTO;
import com.nhom29.DTO.Infomation;
import com.nhom29.DTO.Pagination;
import com.nhom29.Model.ERD.BaiDang;
import com.nhom29.Model.ERD.HinhAnh;
import com.nhom29.Model.ERD.Tag;
import com.nhom29.Service.Inter.BaiDangInter;
import com.nhom29.Service.Inter.TagInter;
import com.nhom29.Service.Inter.ThongTinInter;
import com.nhom29.Service.Oauth2.CurrentUser;
import com.nhom29.Service.Oauth2.security.OAuth2UserDetailCustom;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    public static Integer numberPage = 10;
    private final ThongTinInter thongTinInter;
    private final BaiDangInter baiDangInter;
    private final TagInter tagInter;
    private final ValueApp valueApp;

    public static String uploadDir =System.getProperty("user.dir") + "/src/main/resources/static/images";

    // view dang nhap
    @GetMapping("/login")
    public String hello(){
        return "login";
    }
    @GetMapping("/home")
    public String home(Model model, @CurrentUser OAuth2UserDetailCustom oAuth2UserDetailCustom,
                       @RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "sort", defaultValue = "thoigiantao") String sort,
                       Authentication authentication
    ) {
        if( oAuth2UserDetailCustom == null ){
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            if( thongTinInter.layThongTinByUserName(userDetails.getUsername()).isEmpty())  return "redirect:/logout";
            // infomation about user logined
            model.addAttribute("info", thongTinInter.layThongTinByUserName(userDetails.getUsername()).get());
        }else{
            if (thongTinInter.layThongTin(oAuth2UserDetailCustom.getId()).isEmpty()) {
                return "redirect:/logout";
            }
            // infomation about user logined
            model.addAttribute("info", thongTinInter.layThongTin(oAuth2UserDetailCustom.getId()).get());
        }
        //infomation about BaiDang was paginationed and sort feilded
        model.addAttribute("page", baiDangInter.timBaiDangPhanTrang((page-1), numberPage, "like"));
        // All properti tag
        model.addAttribute("tags", tagInter.getAllTag());
        // DTO fropm BaiDang
        model.addAttribute("baidang", new BaiDangDTO());
        Pagination pagination = new Pagination(baiDangInter.getNumberPage(),page);
        // infomation about pagination
        model.addAttribute("pagination", pagination);
        // value from application.p
        Infomation infomation = new Infomation(valueApp.URLImage, valueApp.shortCutIcon);
        model.addAttribute("image", infomation);
        return "home";
    }
    // view bi chan
    @GetMapping("error")
    public String denied(){
        return "error";
    }
    // view bi chan
    @PostMapping("/upload")
    @Transactional
    public String uploadFile(@ModelAttribute("baidang") BaiDangDTO baiDangDTO,
                             @RequestParam(value = "fileInput", required = false) MultipartFile[] files,
                             @RequestParam(value = "tags", required = false) List<String> tags) {
        try {
            BaiDang b = new BaiDang();
            if (thongTinInter.layThongTin(baiDangDTO.getId()).isEmpty()) {
                throw new RuntimeException("Lỗi xác thực!!");
            }
            b.setThongTin(thongTinInter.layThongTin(baiDangDTO.getId()).get());
            b.setThoigiantao(LocalDateTime.now());
            b.setTieude(baiDangDTO.getTieuDe());
            b.setNoidung(baiDangDTO.getNoiDung());

            // Xử lý tải tập tin và thêm hình ảnh vào bài đăng
            if (files != null) {
                Arrays.asList(files).forEach(file -> {
                    try {
                        String imageUUID;
                        if (!file.isEmpty()) {
                            imageUUID = file.getOriginalFilename();
                            Path filePath = Paths.get(uploadDir, imageUUID);
                            Files.write(filePath, file.getBytes());
                            HinhAnh h = new HinhAnh();
                            h.setUrl(imageUUID);
                            b.getHinhAnh().add(h);
                        }
                    } catch (Exception ex) {
                        log.warn("{}", ex.getMessage());
                    }
                });
            }

            // Thêm các Tag vào bài đăng
            if (tags != null) {
                for (String tagName : tags) {
                    Tag tag = tagInter.getTagByName(tagName);
                    if (tag != null) {
                        b.getTag().add(tag);
                    } else {
                        // Nếu tag chưa tồn tại, bạn có thể tạo mới và thêm vào danh sách
                        Tag newTag = new Tag();
                        newTag.setTenTag(tagName);
                        b.getTag().add(newTag);
                    }
                }
            }

            // Lưu bài đăng vào cơ sở dữ liệu
            baiDangInter.saveBaiDang(b);
        } catch (Exception ex) {
            log.warn("{}", ex.getMessage());
            return "error"; // Trả về trang lỗi nếu có lỗi xảy ra
        }
        return "redirect:/error";
    }
    // redict page question
    @GetMapping("/question")
    public String pageQuestion(){
        return "question";
    }

    // redict page detail question
    @GetMapping("/question/{baiDangId}/{title}")
    public String pageDetaiQuestion(Model model,@CurrentUser OAuth2UserDetailCustom oAuth2UserDetailCustom,
                                    @PathVariable Integer baiDangId,
                                    @PathVariable String title){
        if (thongTinInter.layThongTin(oAuth2UserDetailCustom.getId()).isEmpty()) {
            return "redirect:/logout";
        }
        // infomation about user logined
        model.addAttribute("info", thongTinInter.layThongTin(oAuth2UserDetailCustom.getId()).get());
        // information from application file
        Infomation infomation = new Infomation(valueApp.getURLImage(), valueApp.getShortCutIcon());
        model.addAttribute("image", infomation);
        return "detailQuestion";
    }

    @GetMapping("/email")
    public String pageEmail(@RequestParam(value = "error", defaultValue = "") String error, Model model){
            model.addAttribute("error", error);
            return "layLaiMatKhau";
    }
}
