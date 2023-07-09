package com.ahut.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : Scott Chen
 * @create 2023/7/9 11:11
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/demo")
    public String demo(){
        return "hello ,demo";
    }


    @PreAuthorize("hasAuthority('student:add')")
    @GetMapping("/add")
    public String add(){
        return "hello ,add";
    }

    @PreAuthorize("hasAuthority('student:update')")
    @GetMapping("/update")
    public String update(){
        return "hello ,update";
    }


    @PreAuthorize("hasAuthority('admin:delete')")
    @GetMapping("/delete")
    public String delete(){
        return "hello ,delete";
    }
}
