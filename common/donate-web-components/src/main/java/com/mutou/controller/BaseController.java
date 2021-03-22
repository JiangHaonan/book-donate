package com.mutou.controller;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BaseController {

    public static final String UID_HEADER = "user-id";
    public static final String AUTH_HEADER = "Authorization";
    public static final String REFRESH_TOKEN_HEADER = "refresh-token";

    public static final Integer PAGE_SIZE = 20;

    public boolean checkUserIllegalOperation(HttpServletRequest request, String operateUserId) {
        return operateUserId.equals(request.getHeader(UID_HEADER));
    }
}
