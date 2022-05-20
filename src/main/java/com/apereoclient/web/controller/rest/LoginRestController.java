package com.apereoclient.web.controller.rest;

import com.apereoclient.exeption.BadRequestException;
import com.apereoclient.model.data.LoginBindingEntity;
import com.apereoclient.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.apereoclient.logging.LogMessage.BAD_LOGIN_REQUEST;

@Slf4j
@RestController
@RequestMapping("/api/v1/login")
public class LoginRestController {

    private final LoginService loginService;

    public LoginRestController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * @see LoginService#attemptLogin(LoginBindingEntity, boolean)
     */
    @PostMapping
    public ResponseEntity<Object> login(@RequestBody @Valid LoginBindingEntity loginEntity,
                                        BindingResult bindingResult,
                                        @RequestParam(value = "jwt", required = false) boolean jwt) {

        if (bindingResult.hasErrors()) {
            log.debug(BAD_LOGIN_REQUEST);
            throw new BadRequestException(BAD_LOGIN_REQUEST);
        }

        Object responseObject = loginService.attemptLogin(loginEntity, jwt);

        return ResponseEntity.ok(responseObject);
    }



}
