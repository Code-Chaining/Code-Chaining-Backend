package shop.codechaining.codechaining.global.error.validator

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import shop.codechaining.codechaining.global.annotation.UnicodeNotBlank

class UnicodeNotBlankValidator : ConstraintValidator<UnicodeNotBlank, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        if (value == null) {
            return false
        }

        if (value.trim().isEmpty()) {
            return false
        }

        if (value.contains('\u3164')) {
            return false
        }

        return true
    }

}
