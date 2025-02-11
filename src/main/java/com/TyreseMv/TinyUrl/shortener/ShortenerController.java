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
    public String Shorten(@RequestParam String url, RedirectAttributes redirectAttributes, @CurrentOwner String email, Authentication authentication) {

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
            String message = "This is your new URL: localhost:8080/"+HashedURL;
            redirectAttributes.addFlashAttribute("message", message);
            urlObj.setUser(currentUser); // sets user to null object
            urlRepository.save(urlObj); //attempt to save the user to the url
            return "redirect:/dashboard/shortener";
        }
        String message = "This URL has already been shortened";
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/dashboard/shortener";

    }


    @PostMapping("/shortener")
    public String Shorten(@RequestParam String url, RedirectAttributes redirectAttributes) {

        String HashedURL = HasherModel.HashURL(url);

        if (hashedURLRepository.urlExists(HashedURL)){
            String message = "This Url already exists: localhost:8080/"+HashedURL;
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/shortener";
        }
        hashedURLRepository.addUrl(HashedURL, url);
        String message = "This is your new URL: localhost:8080/"+HashedURL;
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/shortener";
    }

    @GetMapping("/{HashedURL}")
    public String Redirect(@PathVariable String HashedURL) {
        Url url = urlRepository.findByShortUrl(HashedURL);
        String longUrl = (url == null) ? hashedURLRepository.getUrl(HashedURL) : url.getLongUrl();

        return "redirect:"+longUrl;
    }

}
