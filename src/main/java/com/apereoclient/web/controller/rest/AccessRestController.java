package com.apereoclient.web.controller.rest;

import com.apereoclient.service.AccessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/access")
public class AccessRestController {

    private final AccessService accessService;

    public AccessRestController(AccessService accessService) {
        this.accessService = accessService;
    }

    /**
     * @see AccessService#validateAccessWithAccessToken(String, String)
     */
    @GetMapping("/accessToken")
    public ResponseEntity<Boolean> accessToken(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String accessToken,
                                              @RequestParam(value = "groups") String groups) {

        return ResponseEntity.ok(accessService.validateAccessWithAccessToken(accessToken, groups));
    }

    /**
     * @see AccessService#validateAccessWithJwt(String, String)
     */
    @GetMapping("/jwt")
    public ResponseEntity<Boolean> jwt(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String jwt,
                                      @RequestParam(value = "groups") String groups) {

        return ResponseEntity.ok(accessService.validateAccessWithJwt(jwt, groups));
    }



}
