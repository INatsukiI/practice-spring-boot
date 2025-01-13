package com.sample.demo.service

import com.sample.demo.dao.UserDao
import com.sample.demo.entity.UserEntity
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.throwable.shouldHaveMessage
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import org.assertj.core.api.Assertions.catchThrowable
import java.time.LocalDateTime

internal class UserServiceTest : FunSpec({
    lateinit var sut: UserService
    lateinit var userDao: UserDao

    beforeContainer {
        userDao = mockk()
        sut = UserService(userDao)
    }

    beforeEach {
        every { userDao.save(any()) } returns 1
        every { userDao.findById(any()) } returns userEntity()
    }

    afterEach {
        clearAllMocks()
    }

    afterContainer {
        unmockkAll()
    }

    context("ユーザー作成") {
        test("作成成功") {
            every { userDao.existsByName(any()) } returns false
            sut.createUser(
                name = "test",
                age = 20
            )

            verify(exactly = 1) { userDao.save(any()) }
        }

        test("作成失敗(名前重複)") {
            every { userDao.existsByName(any()) } returns true

            val actual = catchThrowable {
                sut.createUser(
                    name = "test",
                    age = 20
                )
            }

            actual.shouldBeInstanceOf<Exception>()
            actual.shouldHaveMessage("ユーザー名が重複しています")
            verify(exactly = 0) { userDao.save(any()) }
        }
    }

    test("ユーザー取得") {
        val expected = userEntity(id = 1)
        val actual = sut.getUser(1)
        actual.shouldBeEqual(expected)
    }

}) {
    companion object {
        private fun userEntity(
            id: Long = 1
        ) = UserEntity(
            id = id,
            name = "test",
            age = 20,
            createdAt = LocalDateTime.of(2022, 1, 1, 0, 0, 0),
            updatedAt = LocalDateTime.of(2022, 1, 1, 0, 0, 0)
        )
    }
}