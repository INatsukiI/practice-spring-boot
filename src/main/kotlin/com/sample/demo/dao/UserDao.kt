package com.sample.demo.dao

import com.sample.demo.entity.UserEntity
import org.seasar.doma.Dao
import org.seasar.doma.Select
import org.seasar.doma.boot.ConfigAutowireable

@ConfigAutowireable
@Dao
interface UserDao {
    @Select
    fun findById(param: FindByIdParam): UserEntity
}

data class FindByIdParam(
    val id: Long
)