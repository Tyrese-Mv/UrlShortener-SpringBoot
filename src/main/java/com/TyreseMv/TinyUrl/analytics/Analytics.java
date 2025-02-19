package com.TyreseMv.TinyUrl.analytics;

import com.TyreseMv.TinyUrl.config.CurrentOwner;
import com.TyreseMv.TinyUrl.persistance.Url;
import com.TyreseMv.TinyUrl.persistance.UrlRepository;
import com.TyreseMv.TinyUrl.persistance.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class Analytics {


    private final UserRepository userRepository;

    public Analytics(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @GetMapping("/numberOfUrls")
    public String numberOfUrls(@CurrentOwner String email, Model model){
        int num = userRepository.findByEmail(email).getUrls().size();

        model.addAttribute("TotalUrls", num);
        return "analytics/numberOfUrls";

    }

    @GetMapping("/clicksPerUrl")
    public String clicksPerUrl(@CurrentOwner String email, Model model){
        List<Url> urls = userRepository.findByEmail(email).getUrls();
        model.addAttribute("urls", urls);

        return "analytics/clicksPerUrl";
    }
}
