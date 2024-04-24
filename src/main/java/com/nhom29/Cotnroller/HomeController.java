package com.nhom29.Cotnroller;

import com.nhom29.DTO.BaiDangDTO;
import com.nhom29.DTO.Infomation;
import com.nhom29.DTO.Pagination;
import com.nhom29.DTO.ThongTinDangKi;
import com.nhom29.Model.ERD.*;
import com.nhom29.Service.Inter.*;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    public static Integer numberPage = 10;
    private final ThongTinInter thongTinInter;
    private final BaiDangInter baiDangInter;
    private final TagInter tagInter;
    private final ValueApp valueApp;
    private final BinhLuanInter binhLuanInter;
    private final CloudinaryInter cloudinaryInter;


    public static String uploadDir =System.getProperty("user.dir") + "/src/main/resources/static/images";

    // view dang nhap
    @GetMapping("/login")
    public String hello( Model model){
        Infomation infomation = new Infomation(valueApp.URLImage, valueApp.shortCutIcon);
        model.addAttribute("image", infomation);
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
//    @Transactional
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
                        if (!file.isEmpty()) {
                            String url = cloudinaryInter.uploadFile(file);
                            HinhAnh h = new HinhAnh();
                            h.setUrl(url);
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
                        newTag.setThoigiantao(LocalDateTime.now());
                        System.out.println("=====================");
                        log.info("{}",newTag);
                        System.out.println("======================");
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
        return "redirect:/home";
    }
    // redict page question
    @GetMapping("/question")
    public String pageQuestion(){
        return "question";
    }

    // redict page detail question
    @GetMapping("/question/{baiDangId}")
    public String pageDetaiQuestion(Model model,@CurrentUser OAuth2UserDetailCustom oAuth2UserDetailCustom,
                                    @PathVariable Long baiDangId,
                                    Authentication authentication,
                                    @RequestParam(value = "page", defaultValue = "3") Integer soluong,
                                    @RequestParam(value = "sort", defaultValue = "macdinh") String sort
                                    ){
        ThongTin thongTin = new ThongTin();
        if( oAuth2UserDetailCustom == null ){
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            if( thongTinInter.layThongTinByUserName(userDetails.getUsername()).isEmpty())  return "redirect:/logout";
            thongTin = thongTinInter.layThongTinByUserName(userDetails.getUsername()).get();
            // infomation about user logined
            model.addAttribute("info", thongTinInter.layThongTinByUserName(userDetails.getUsername()).get());
        }else{
            if (thongTinInter.layThongTin(oAuth2UserDetailCustom.getId()).isEmpty()) {
                return "redirect:/logout";
            }
            thongTin = thongTinInter.layThongTin(oAuth2UserDetailCustom.getId()).get();
            // infomation about user logined
            model.addAttribute("info", thongTinInter.layThongTin(oAuth2UserDetailCustom.getId()).get());
        }
        // deltai baidang
        Optional<BaiDang> baiDang = baiDangInter.layChiTietBaiDang(baiDangId);
        if( baiDang.isEmpty()) return "redirect:/home/error=baidangId";

        // user followed baidang
        ThongTin finalThongTin = thongTin;
        Boolean fl = baiDang.get().getLuu().stream().anyMatch(t -> t.getId().equals(finalThongTin.getId()));
        model.addAttribute("follow", fl);

        // user liked baidang
        Boolean like = baiDang.get().getLike().stream().anyMatch(t -> t.getId().equals(finalThongTin.getId()));
        model.addAttribute("like", like);

        // information about baidang
        model.addAttribute("chiTietBaiDang", baiDang.get());
        // upload new question
        model.addAttribute("baidang", new BaiDangDTO());
        // All properti tag
        model.addAttribute("tags", tagInter.getAllTag());

        // information from application file
        Infomation infomation = new Infomation(valueApp.getURLImage(), valueApp.getShortCutIcon());
        model.addAttribute("image", infomation);
        // pagination binhluan
        model.addAttribute("dachsachbinhluan", binhLuanInter.layBinhLuanTheoBaiDangVaPhanTrang(baiDangId,soluong,sort));
        // infomation about page and size binhluan
        Pagination pagination = new Pagination(baiDang.get().getBinhLuans().size(), soluong);
        model.addAttribute("pagination", pagination);
        // format binhluan
        BinhLuan binhLuan = new BinhLuan();
        model.addAttribute("format", binhLuan);
        // sort
        model.addAttribute("sort", sort);
        return "chiTietCauHoi";
    }

    @PostMapping("/upload/comment")
    @Transactional
    public String uploadComment(@ModelAttribute BinhLuan binhLuan,
                                @RequestParam(value = "fileInput", required = false) MultipartFile[] files,
                                @RequestParam(value = "nguoibinhluanId") Long nguoibinhluanId,
                                @RequestParam(value = "baidangId") Long baidangId,
                                @RequestParam(value = "page", defaultValue = "3") Integer soluong,
                                @RequestParam(value = "sort", defaultValue = "macdinh") String sort) throws InterruptedException {
        BinhLuan bl = new BinhLuan();
        if( binhLuan.getNoidung() != null ){
            bl.setNoidung(binhLuan.getNoidung());
        }
        bl.setDate(LocalDateTime.now());
        if (files != null) {
            Arrays.asList(files).forEach(file -> {
                try {
                    if (!file.isEmpty()) {
                        String url = cloudinaryInter.uploadFile(file);
                        HinhAnh h = new HinhAnh();
                        h.setUrl(url);
                        bl.getHinhAnh().add(h);
                    }
                } catch (Exception ex) {
                    log.warn("{}", ex.getMessage());
                }
            });
        }
        Optional<ThongTin> thongTin = thongTinInter.layThongTin(nguoibinhluanId);
        if( thongTin.isEmpty() ) return "redirect:/question/" + baidangId + "?error=nguoibinhluan";
        bl.setThongTin(thongTin.get());
        Optional<BaiDang> baiDang = baiDangInter.layChiTietBaiDang(baidangId);
        if( baiDang.isEmpty()) return "redirect:/question/" + baidangId + "?error=baidang";
        bl.setBaidang(baiDang.get());
        BinhLuan b = binhLuanInter.luuBinhLuan(bl);
        return "redirect:/question/" + baidangId + "?page=" + soluong + "&sort=" + sort;
    }
    @GetMapping("/email")
    public String pageEmail(@RequestParam(value = "error", defaultValue = "") String error, Model model){
            model.addAttribute("error", error);
            return "layLaiMatKhau";
    }

    @GetMapping("/taotaikhoan")
    public String pageTaoTaiKhoan(@RequestParam(value = "error", defaultValue = "") String error, Model model){
        ThongTinDangKi thongtin = new ThongTinDangKi();
        model.addAttribute("thongtin", thongtin);
        Infomation infomation = new Infomation(valueApp.URLImage, valueApp.shortCutIcon);
        model.addAttribute("image", infomation);
        return "taoTaiKhoan";
    }

    @GetMapping("/tag")
    public String pageTag(Model model,@CurrentUser OAuth2UserDetailCustom oAuth2UserDetailCustom,
                          Authentication authentication){
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
        // information from application file
        Infomation infomation = new Infomation(valueApp.getURLImage(), valueApp.getShortCutIcon());
        model.addAttribute("image", infomation);
        return "tag";
    }
}
