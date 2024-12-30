package com.sample.demo.service

import com.sample.demo.dao.UserDao
import com.sample.demo.entity.UserEntity
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
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
        every { userDao.save(any()) } returns 1
    }

    @AfterEach
    fun afterEach() {
       clearAllMocks()
    }

    @AfterAll
    fun tearDown() {
        unmockkAll()
    }

    @Nested
    @DisplayName("ユーザー作成")
    inner class CreateUser {
        @Test
        @DisplayName("作成成功")
        fun `作成成功`() {
            every { userDao.existsByName(any()) } returns false
            sut.createUser(
                name = "test",
                age = 20
            )

            verify { userDao.save(any()) }
        }

        @Test
        @DisplayName("作成失敗(名前重複)")
        fun `作成失敗(名前重複)`() {
            every { userDao.existsByName(any()) } returns true

            val actual = catchThrowable {
                sut.createUser(
                    name = "test",
                    age = 20
                )
            }

            assertThat(actual).isInstanceOf(Exception::class.java)
            assertThat(actual.message).isEqualTo("ユーザー名が重複しています")
            verify(exactly = 0) { userDao.save(any()) }
        }
    }

    @Test
    @DisplayName("ユーザー取得")
    fun `ユーザー取得`() {
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