package com.TyreseMv.TinyUrl.shortener;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ShortenerController {

    private final HashedURLRepository urlRepository;

    public ShortenerController(HashedURLRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @GetMapping("/shortener")
    public String Shortener() {
        return "Shortener";
    }

    @PostMapping("/shortener")
    public String Shorten(@RequestParam String url, RedirectAttributes redirectAttributes) {
        String HashedURL = HasherModel.HashURL(url);


        if (!this.urlRepository.addUrl(HashedURL, url)){
            String message = "This is your new URL: localhost:8080/"+HashedURL;
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/shortener";
        }
        String message = "This URL has already been shortened";
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/shortener";

    }

    @GetMapping("/{HashedURL}")
    public String Redirect(@PathVariable String HashedURL) {
        String longUrl = urlRepository.getUrl(HashedURL);
        return "redirect:"+longUrl;
    }



}
