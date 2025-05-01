package com.TyreseMv.TinyUrl.users;

import com.TyreseMv.TinyUrl.persistance.User;
import com.TyreseMv.TinyUrl.persistance.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private final UserRepository userRepository;

    public OAuth2LoginSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    public OAuth2LoginSuccessHandler(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        /*
        * 1. Extract the OAuth2User from the authentication object
        * 2. Use the OAuth2User to get the user details
        * 3. Use the details to check if user already exist from UserRepository
        * 4. Add the user to UserRepository if they don't exist
        * */

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String username = oAuth2User.getAttribute("name");
        String email = oAuth2User.getAttribute("email");

        User existingUser = userRepository.findByEmail(email);
        if (existingUser == null) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            userRepository.save(newUser);
        }
    }
}
