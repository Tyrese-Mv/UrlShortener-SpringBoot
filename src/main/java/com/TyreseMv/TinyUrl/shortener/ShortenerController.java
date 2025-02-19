package com.TyreseMv.TinyUrl.shortener;

import com.TyreseMv.TinyUrl.config.CurrentOwner;
import com.TyreseMv.TinyUrl.persistance.Url;
import com.TyreseMv.TinyUrl.persistance.UrlRepository;
import com.TyreseMv.TinyUrl.persistance.User;
import com.TyreseMv.TinyUrl.persistance.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ShortenerController {

    private final UrlRepository urlRepository;

    private final UserRepository userRepository;

    private final HashedURLRepository hashedURLRepository;

    private final String baseUrl = "localhost:8080/";

    public ShortenerController(UrlRepository urlRepository, UserRepository userRepository, HashedURLRepository hashedURLRepository) {
        this.urlRepository = urlRepository;
        this.userRepository = userRepository;
        this.hashedURLRepository = hashedURLRepository;
    }

    @GetMapping("/shortener")
    public String Shortener() {
        return "Shortener";
    }
    @GetMapping("/dashboard/shortener")
    public String PersonalShortener() {
        return "dashboard/dashboard_shortener";
    }


    // Post for dashboard form
    @PostMapping("/dashboard/shortener")
    public String Shorten(@RequestParam String url, @CurrentOwner String email, Authentication authentication, Model model) {

        String HashedURL = HasherModel.HashURL(url);

        Url urlObj = new Url();
        urlObj.setLongUrl(url);
        urlObj.setShortUrl(HashedURL);

        User currentUser = userRepository.findByEmail(email); // returns null

        if (currentUser == null){
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            String username = oAuth2User.getAttribute("name");
            String email2 = oAuth2User.getAttribute("email");
            currentUser = new User();
            currentUser.setUsername(username);
            currentUser.setEmail(email2);
            currentUser = userRepository.save(currentUser);
        }

        if (this.urlRepository.findByShortUrl(HashedURL) == null){
            String message = "This is your new URL:";
            String ShortUrl = baseUrl+HashedURL;
            model.addAttribute("ShortUrl", ShortUrl).addAttribute("message", message);

            urlObj.setUser(currentUser); // sets user to null object
            urlRepository.save(urlObj); //attempt to save the user to the url
            return "/dashboard/shortener";
        }
        String message = "This URL has already been shortened";
        model.addAttribute("message", message);
        return "/dashboard/shortener";

    }


    @PostMapping("/shortener")
    public String Shorten(@RequestParam String url, RedirectAttributes redirectAttributes, Model model) {

        String HashedURL = HasherModel.HashURL(url);

        if (hashedURLRepository.urlExists(HashedURL)){
            String message = "This Url already exists: ";
            model.addAttribute("message", message).addAttribute("ShortUrl", baseUrl+HashedURL);
            return "/shortener";
        }
        hashedURLRepository.addUrl(HashedURL, url);
        String message = "This is your new URL: ";
        model.addAttribute("message", message).addAttribute("ShortUrl", baseUrl+HashedURL);
        return "/shortener";
    }

    @GetMapping("/{HashedURL}")
    public String Redirect(@PathVariable String HashedURL) {
        Url url = urlRepository.findByShortUrl(HashedURL);

        if (url == null && hashedURLRepository.getUrl(HashedURL) == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        String longUrl;
        if (url == null){
            longUrl =  hashedURLRepository.getUrl(HashedURL);
        }else{
            longUrl = url.getLongUrl();
            url.setClicks();
            urlRepository.save(url);
        }
        return "redirect:"+longUrl;
    }

}
