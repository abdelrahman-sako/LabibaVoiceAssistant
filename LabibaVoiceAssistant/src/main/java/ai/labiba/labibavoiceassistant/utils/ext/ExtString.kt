package ai.labiba.labibavoiceassistant.utils.ext

import ai.labiba.labibavoiceassistant.enums.LabibaLanguages
import ai.labiba.labibavoiceassistant.other.Constants
import androidx.annotation.Keep
import java.util.regex.Pattern

@Keep
internal fun String.cleanup() = this.replace("َ", "")
    .replace("ً", "")
    .replace("ُ", "")
    .replace("ٌ", "")
    .replace("ِ", "")
    .replace("ٍ", "")
    .replace("ْ", "")
    .replace("ّ", "")
    .replace("<br>", "\n").trim()

@Keep
internal fun String.removeUrls(): String {
    var currentString = this

    val urlPattern =
        "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)"
    val p = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE)
    val m = p.matcher(currentString)
    var i = 0
    while (m.find()) {
        currentString = currentString.replace((m.group(i) ?: "").toRegex(), "").trim { it <= ' ' }
        i++
    }
    return currentString
}
