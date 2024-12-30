package com.sample.demo.controller

import com.sample.demo.dto.UserRequest
import com.sample.demo.entity.UserEntity
import com.sample.demo.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService
) {
    @GetMapping("/users/{userId}")
    fun getUser(@PathVariable userId: Long): UserEntity {
        return userService.getUser(userId)
    }

    @PostMapping("/users")
    fun createUser(@RequestBody request: UserRequest): ResponseEntity<HttpStatus> {
        userService.createUser(
            name = request.name,
            age = request.age
        )
        return ResponseEntity.ok(HttpStatus.CREATED)
    }
}