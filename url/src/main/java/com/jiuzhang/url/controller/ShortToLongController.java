package com.jiuzhang.url.controller;

import com.jiuzhang.url.service.ILongToShortService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * @auther: WZ
 * @Date: 2020/9/8 15:21
 * @Description: 短网址处理重定向
 */
@Controller
@RequestMapping("/s")
@Api
@CrossOrigin
public class ShortToLongController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShortToLongController.class);

    @Autowired
    private ILongToShortService longToShortService;


    /**
     * 重定向到原来长网址
     *
     * @param shortUrl
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/{shortUrl}")
    public void redirect(@PathVariable String shortUrl, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String longUrl = longToShortService.shortToLong(shortUrl);
        if (longUrl == null) {
            throw new NoSuchElementException("Cannot find long URL mapping to " + shortUrl);
        } else {
            response.sendRedirect(longUrl);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public void return400(NoSuchElementException ex) {
        LOGGER.error(ex.getMessage());
    }

}
