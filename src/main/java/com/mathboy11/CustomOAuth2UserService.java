package com.mathboy11;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        //Get the user from the userRequest
        OAuth2User oAuth2User = super.loadUser(userRequest);

        //Validate the user
        try {
            return processOAuth2User(userRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            //Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        Map<String, Object> userAttributes = oAuth2User.getAttributes();

        if (userAttributes.get("email") == null || userAttributes.get("email").equals("")) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        //Check if the user is valid
        if (userAttributes.get("email").equals("theostechtipsinquiries@gmail.com")) {
            return new DefaultOAuth2User(null, userAttributes, userNameAttributeName);
        } else {
            throw new UsernameNotFoundException("Error. Invalid Account.");
        }
    }
}
