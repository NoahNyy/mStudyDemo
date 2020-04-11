package org.noahnyy.demo.mybatisplus.singledatasource.mybatisplus.controller;


import org.noahnyy.demo.mybatisplus.singledatasource.mybatisplus.model.entity.UserEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import org.noahnyy.demo.mybatisplus.singledatasource.mybatisplus.service.IUserService;

import java.util.List;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author niuyy
 * @since 2019-08-28
 */
@RestController
@RequestMapping("/userEntity")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/list")
    public List<UserEntity> list() {
        userService.testSelect();
        return userService.list();
    }
}
