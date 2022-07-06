package com.xxxx.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mr.Z
 * @title: HellloController
 * @projectName yeb
 * @description: TODO
 * @date 2022/5/116:58
 */
@SuppressWarnings({"all"})
@RestController
public class HellloController {
    @GetMapping("hello")
    public String hello(){
            return "hello";
        }


    @GetMapping("/employee/basic/hello")
    public String hello2(){
            return "/employee/basic/hello";
        }

    @GetMapping("/employee/advanced/hello")
    public String hello3(){
        return "/employee/advanced/hello";
    }


}
