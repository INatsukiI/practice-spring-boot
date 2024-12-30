package com.sample.demo.service

import com.sample.demo.dao.ExistsByNameParam
import com.sample.demo.dao.FindByIdParam
import com.sample.demo.dao.SaveParam
import com.sample.demo.dao.UserDao
import com.sample.demo.entity.UserEntity
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userDao: UserDao
) {
    fun getUser(userId: Long): UserEntity {
        return userDao.findById(
            param = FindByIdParam(
                id = userId
            )
        )
    }

    fun createUser(name: String, age: Int) {
        if (userDao.existsByName(ExistsByNameParam(name))) {
            throw Exception("ユーザー名が重複しています")
        }

        userDao.save(
            param = SaveParam(
                name = name,
                age = age
            )
        )
    }
}