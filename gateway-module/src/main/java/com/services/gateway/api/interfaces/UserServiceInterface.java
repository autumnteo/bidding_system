package com.services.gateway.api.interfaces;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.services.gateway.api.model.UserResponse;

@FeignClient(name= "user/user", url = "http://localhost:5000")
@Service
@RequestMapping(value="/api")
public interface UserServiceInterface {
    @RequestMapping(method = RequestMethod.GET, value = "/login/{username}")
    @ResponseBody
    Optional<UserResponse> fetchByUsername(@PathVariable("username") String username);
}
