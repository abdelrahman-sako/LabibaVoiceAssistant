package ai.labiba.labibavoiceassistant.utils.ext

import android.util.Patterns
import java.util.regex.Pattern

fun String.isPasswordValid(
    limit: Int = 0,
    regex: String = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#\$%^&*()_+\\-=,.?]).*\$"
): Boolean {
    if (isNullOrEmpty())
        return false
    if (limit > 0 && length < limit)
        return false
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

fun String.isEmailValid(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}