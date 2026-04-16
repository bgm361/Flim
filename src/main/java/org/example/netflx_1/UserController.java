package org.example.netflx_1;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserRepository userRepository;
    private final YorumRepository yorumRepository;

    @Autowired
    public UserController(UserRepository userRepository, YorumRepository yorumRepository) {
        this.userRepository = userRepository;
        this.yorumRepository = yorumRepository;
    }

    // ------------------ SAYFA AÇILIŞLARI (GET) ------------------

    @GetMapping("/")
    public String index() { return "karsilama"; }

    @GetMapping("login")
    public String loginPage() { return "login"; }

    @GetMapping("kayit")
    public String registerPage() { return "kayit"; }

    @GetMapping("sifresifirlama")
    public String resetPage() { return "sifresifirla"; }

    @GetMapping("profil")
    public String profilSayfasi(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        userRepository.findById(userId).ifPresent(user -> {
            model.addAttribute("ad", user.getAd());
            model.addAttribute("soyad", user.getSoyad());
            model.addAttribute("kredi", user.getKalanDakika());
        });
        return "profil";
    }

    @GetMapping("ana-panel")
    public String anaPanel(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        userRepository.findById(userId).ifPresent(user -> {
            model.addAttribute("ad", user.getAd());
            model.addAttribute("soyad", user.getSoyad());
            model.addAttribute("kredi", user.getKalanDakika());
        });
        return "ana-panel";
    }

    // ------------------ YORUM ÇEKME (FİLTRELİ) ------------------

    @GetMapping("/yorumlar-getir")
    @ResponseBody
    public List<Yorum> tumYorumlariGetir() {
        try {
            return yorumRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // ✅ YENİ: Sadece seçili filme ait yorumları getirir (Film karmaşasını bitirir)
    @GetMapping("/yorumlar-getir/{filmId}")
    @ResponseBody
    public List<Yorum> filmYorumlariniGetir(@PathVariable Long filmId) {
        try {
            List<Yorum> yorumlar = yorumRepository.findByFilmIdOrderByIdDesc(filmId);
            for (Yorum y : yorumlar) {
                userRepository.findById(y.getKullaniciId()).ifPresent(u -> {
                    y.setKullaniciAd(u.getAd()); // İsim eklendi
                    y.setKullaniciMail(u.getEmail()); // Mail eklendi (Adminler görsün diye)
                });
            }
            return yorumlar;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // ------------------ YORUM KAYDETME ------------------

    @PostMapping("/yorum-kaydet")
    @ResponseBody
    public String yorumKaydet(@RequestParam Long filmId,
                              @RequestParam String yorumMetni,
                              @RequestParam int puan,
                              HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "error_login";

        try {
            Yorum yeniYorum = new Yorum();
            yeniYorum.setKullaniciId(userId);
            yeniYorum.setFilmId(filmId);
            yeniYorum.setYorum(yorumMetni);
            yeniYorum.setPuan(puan);
            yeniYorum.setTarih(LocalDateTime.now());

            yorumRepository.save(yeniYorum);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error_sql";
        }
    }

    // ------------------ YORUM DÜZENLEME & SİLME ------------------

    @PostMapping("/yorum-duzenle")
    @ResponseBody
    public String yorumDuzenle(@RequestParam Long yorumId,
                               @RequestParam String yeniMetin,
                               @RequestParam(required = false, defaultValue = "0") int yeniPuan, // ✅ Puan opsiyonel yapıldı
                               HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "error_login";

        Optional<Yorum> yorumOpt = yorumRepository.findById(yorumId);
        if (yorumOpt.isPresent()) {
            Yorum yorum = yorumOpt.get();
            // Güvenlik kontrolü: Sadece kendi yorumunu düzenleyebilir
            if (yorum.getKullaniciId().equals(userId)) {
                yorum.setYorum(yeniMetin);
                if(yeniPuan > 0) yorum.setPuan(yeniPuan);
                yorumRepository.save(yorum);
                return "success";
            }
        }
        return "error_yetki";
    }

    // ✅ YENİ: Yorum Silme Metodu
    @DeleteMapping("/yorum-sil/{id}")
    @ResponseBody
    public String yorumSil(@PathVariable Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "error_login";

        Optional<Yorum> yorumOpt = yorumRepository.findById(id);
        if (yorumOpt.isPresent()) {
            Yorum yorum = yorumOpt.get();
            if (yorum.getKullaniciId().equals(userId)) {
                yorumRepository.delete(yorum);
                return "success";
            }
        }
        return "error_yetki";
    }

    // ------------------ DİĞER İŞLEMLER (LOGIN, KAYIT, DAKİKA) ------------------

    @PostMapping("dakika-ekle")
    @ResponseBody
    public String dakikaEkle(@RequestParam int miktar, @RequestParam boolean sabit, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "Hata";
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if(sabit) user.setKalanDakika(miktar);
            else user.setKalanDakika(user.getKalanDakika() + miktar);
            userRepository.save(user);
            return String.valueOf(user.getKalanDakika());
        }
        return "Hata";
    }

    @PostMapping("login")
    public String login(@RequestParam String email, @RequestParam String sifre, HttpSession session) {
        User user = userRepository.findByEmail(email.trim().toLowerCase());
        if (user != null && user.getSifre().equals(sifre.trim())) {
            session.setAttribute("userId", user.getId());
            session.setAttribute("ad", user.getAd());
            return "redirect:/ana-panel";
        }
        return "redirect:/login?error";
    }

    @PostMapping("kayit")
    public String register(@RequestParam String ad, @RequestParam String soyad,
                           @RequestParam String email, @RequestParam String sifre,
                           HttpSession session) {
        User user = new User();
        user.setAd(ad);
        user.setSoyad(soyad);
        user.setEmail(email.toLowerCase());
        user.setSifre(sifre);
        user.setKalanDakika(1000);
        User savedUser = userRepository.save(user);
        session.setAttribute("userId", savedUser.getId());
        session.setAttribute("ad", savedUser.getAd());
        return "redirect:/ana-panel";
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping("sifresifirla")
    public String resetPassword(@RequestParam String email,
                                @RequestParam String yeniSifre,
                                @RequestParam String yeniSifreTekrar,
                                @RequestParam String captchaInput) {
        if (!yeniSifre.equals(yeniSifreTekrar)) return "redirect:/sifresifirlama?error=mismatch";
        if (!"ce38a".equalsIgnoreCase(captchaInput)) return "redirect:/sifresifirlama?error=wrongCaptcha";

        User user = userRepository.findByEmail(email.trim().toLowerCase());
        if (user != null) {
            user.setSifre(yeniSifre.trim());
            userRepository.save(user);
            return "redirect:/login?success=passwordUpdated";
        }
        return "redirect:/sifresifirlama?error=userNotFound";
    }

    @PostMapping("profil-guncelle")
    public String profilGuncelle(@RequestParam String ad, @RequestParam String soyad, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        userRepository.findById(userId).ifPresent(user -> {
            user.setAd(ad);
            user.setSoyad(soyad);
            userRepository.save(user);
            session.setAttribute("ad", user.getAd());
        });
        return "redirect:/ana-panel?success";
    }
}