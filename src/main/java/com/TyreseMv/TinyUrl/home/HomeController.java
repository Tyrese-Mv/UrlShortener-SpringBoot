package com.TyreseMv.TinyUrl.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {
    @GetMapping("/")
    public String Home() {
        return "Home";
    }

    @GetMapping("/dashboard/reports")
    public String Report() {
        return "analytics/reports";
    }

    @GetMapping("/dashboard")
    public String Dashboard() {
        return "dashboard/dashboard_home";
    }

    @GetMapping("/privacy-policy")
    public String PrivacyPolicy() {
        return "privacy-policy";
    }

    @GetMapping("/terms-of-service")
    public String TermsOfService() {
        return "terms-of-service";
    }

    @PostMapping("/delete-facebook-data")
    public String DeleteFacebookData() {
        return "delete-facebook-data";
    }
}
