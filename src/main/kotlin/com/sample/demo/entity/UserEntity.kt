package com.sample.demo.entity

import org.seasar.doma.Column
import org.seasar.doma.Entity
import org.seasar.doma.Id
import org.seasar.doma.Table
import java.time.LocalDateTime

@Entity(immutable = true)
@Table(name = "user")
data class UserEntity(
    @Id
    val id: Long,
    @Column(name = "name")
    val name: String,
    @Column(name = "age")
    val age: Int,
    @Column(name = "created_at")
    val createdAt: LocalDateTime,
    @Column(name = "updated_at")
    val updatedAt: LocalDateTime
)