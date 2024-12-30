package com.sample.demo.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UserRequest(
    @field:NotBlank(message = "Name is required")
    @field:Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    val name: String,

    @field:Min(value = 0, message = "Age must be greater than or equal to 0")
    @field:Max(value = 150, message = "Age must be less than or equal to 150")
    val age: Int
)