package com.apereoclient.web.controller.rest;

import com.apereoclient.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/")
public class ProfileRestController {

    private final ProfileService profileService;

    public ProfileRestController(ProfileService profileService) {
        this.profileService = profileService;
    }

    /**
     * @see ProfileService#showProfileWithAccessToken(String, boolean)
     */
    @GetMapping("profile")
    public ResponseEntity<Object> profile(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String accessToken,
                                          @RequestParam(value = "jwt", required = false) boolean jwt) {
        if (accessToken.trim().length() == 0) return ResponseEntity.badRequest().build();

        Object response = profileService.showProfileWithAccessToken(accessToken, jwt);
        return ResponseEntity.ok(response);
    }



}
