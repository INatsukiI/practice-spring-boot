package com.sample.demo.service

import com.sample.demo.dao.UserDao
import com.sample.demo.entity.UserEntity
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class UserServiceTest {
    private lateinit var sut: UserService

    @MockK
    private lateinit var userDao: UserDao

    @BeforeAll
    fun setup() {
        MockKAnnotations.init(this)
        sut = UserService(
            userDao = userDao
        )
    }

    @BeforeEach
    fun beforeEach() {
        every { userDao.findById(any()) } returns userEntity()
    }

    @AfterEach
    fun afterEach() {
       clearAllMocks()
    }

    @AfterAll
    fun tearDown() {
        unmockkAll()
    }

    @Test
    @DisplayName("ユーザー作成処理")
    fun `ユーザー作成処理`() {
        val expected = userEntity(id = 1)
        val actual = sut.getUser(1)

        assertThat(actual).isEqualTo(expected)
    }


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