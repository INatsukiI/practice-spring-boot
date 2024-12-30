package com.sample.demo.annotation

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [JapanesePhoneNumberValidator::class])
@MustBeDocumented
annotation class JapanesePhoneNumber(
    val message: String = "有効な日本の電話番号ではありません",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class JapanesePhoneNumberValidator : ConstraintValidator<JapanesePhoneNumber, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value == null) return true
        return value.matches(Regex("^0[789]0-[0-9]{4}-[0-9]{4}$"))
    }
}