package com.sample.demo.controller

import com.sample.demo.entity.UserEntity
import com.sample.demo.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService
) {
    @GetMapping("/user/{userId}")
    fun getUser(@PathVariable userId: Long): UserEntity {
        return userService.getUser(userId)
    }
}