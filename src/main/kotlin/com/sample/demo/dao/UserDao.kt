package com.sample.demo.dao

import com.sample.demo.entity.UserEntity
import org.seasar.doma.Dao
import org.seasar.doma.Insert
import org.seasar.doma.Select
import org.seasar.doma.boot.ConfigAutowireable

@ConfigAutowireable
@Dao
interface UserDao {
    @Select
    fun findById(param: FindByIdParam): UserEntity

    @Select
    fun existsByName(param: ExistsByNameParam): Boolean

    @Insert(sqlFile = true)
    fun save(param: SaveParam): Int
}

data class FindByIdParam(
    val id: Long
)

data class ExistsByNameParam(
    val name: String
)

data class SaveParam(
    val name: String,
    val age: Int
)