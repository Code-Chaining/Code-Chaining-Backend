package shop.codechaining.codechaining.global.annotation

import jakarta.validation.Constraint
import shop.codechaining.codechaining.global.error.validator.UnicodeNotBlankValidator
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [UnicodeNotBlankValidator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class UnicodeNotBlank(
    val message: String = "공백 문자는 허용되지 않습니다.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Any>> = []
)
