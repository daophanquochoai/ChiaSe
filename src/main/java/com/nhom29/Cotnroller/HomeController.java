package com.nhom29.Cotnroller;

import com.nhom29.DTO.BaiDangDTO;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    public static String uploadDir =System.getProperty("user.dir") + "/src/main/resources/static/images";

    // view dang nhap
    @GetMapping("/login")
    public String hello(){
        return "login";
    }
    @GetMapping("/home")
    public String home(Model model, @CurrentUser OAuth2UserDetailCustom oAuth2UserDetailCustom, @RequestParam(value = "page", defaultValue = "1") int page) {
        if (thongTinInter.layThongTin(oAuth2UserDetailCustom.getId()).isEmpty()) {
            return "redirect:/logout";
        }
        System.out.println("====================================");
        log.info("{}", page);
        System.out.println("====================================");
        model.addAttribute("page", baiDangInter.timBaiDangPhanTrang((page-1), numberPage, "TieuDe"));
        model.addAttribute("info", thongTinInter.layThongTin(oAuth2UserDetailCustom.getId()).get());
        model.addAttribute("tags", tagInter.getAllTag());
        model.addAttribute("baidang", new BaiDangDTO());
        Pagination pagination = new Pagination(baiDangInter.getNumberPage(),page);
        model.addAttribute("pagination", pagination);
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

}
