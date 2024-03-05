package ai.labiba.labibavoiceassistant.enums

import ai.labiba.labibavoiceassistant.sdkSetupClasses.LabibaVA
import android.view.View

enum class LabibaLanguages(val value: String) {
    ARABIC(LabibaVA.Arabic),
    ENGLISH(LabibaVA.English),
    RUSSIAN(LabibaVA.Russian),
    GERMANY(LabibaVA.Germany),
    CHINESE(LabibaVA.Chinese);

    private val languageCode = arrayOf("ar", "en", "ru", "de", "zh")
    private val srLanguageCodes = arrayOf("ar-JO", "en-US", "ru", "de-DE", "zh")
    private val ttsLanguageCodes = arrayOf("ar-XA", "en-US", "ru-RU", "de-DE", "cmn-CN")
    private val languageTitles = arrayOf("العربية", "English", "русский", "Deutsch", "俄文")

    private val convName =
        arrayOf(
            LabibaVA.Arabic,
            LabibaVA.English,
            LabibaVA.Russian,
            LabibaVA.Germany,
            LabibaVA.Chinese
        )
    private val layoutDirection = arrayOf(
        View.LAYOUT_DIRECTION_RTL,
        View.LAYOUT_DIRECTION_LTR,
        View.LAYOUT_DIRECTION_LTR,
        View.LAYOUT_DIRECTION_LTR,
        View.LAYOUT_DIRECTION_LTR
    )

    //for adapter purposes
    var isSelected = false

    fun getTitle(): String {
        return languageTitles[ordinal]
    }

    fun getSrCode(): String {
        return srLanguageCodes[ordinal]
    }

    fun getLanguageCode(): String {
        return languageCode[ordinal]
    }

    fun getTTsCode(): String {
        return ttsLanguageCodes[ordinal]
    }

    fun getConvName(): String {
        return convName[ordinal]
    }

    fun getLayoutDirection(): Int {
        return layoutDirection[ordinal]
    }

    companion object {
        private val map = values().associateBy(LabibaLanguages::value)
        fun fromString(type: String) = map[type]
    }
}