package ai.labiba.labibavoiceassistant.sdkSetupClasses.builders

import ai.labiba.labibavoiceassistant.enums.LabibaLanguages

class LabibaVAVoice {
    internal var arabicVoiceName: String = "ar-XA-Wavenet-A"
    internal var englishVoiceName: String = "en-US-Wavenet-F"
    internal var russianVoiceName: String = "ru-RU-Wavenet-E"
    internal var germanVoiceName: String = "de-DE-Wavenet-F"
    internal var chineseVoiceName: String = "cmn-CN-Wavenet-D"

    class Builder {
        private var _arabicVoiceName: String = LabibaVAVoice().arabicVoiceName
        private var _englishVoiceName: String = LabibaVAVoice().englishVoiceName
        private var _russianVoiceName: String = LabibaVAVoice().russianVoiceName
        private var _germanVoiceName: String = LabibaVAVoice().germanVoiceName
        private var _chineseVoiceName: String = LabibaVAVoice().chineseVoiceName

        fun arabicVoiceName(name: String) = apply { _arabicVoiceName = name }
        fun englishVoiceName(name: String) = apply { _englishVoiceName = name }
        fun russianVoiceName(name: String) = apply { _russianVoiceName = name }
        fun germanVoiceName(name: String) = apply { _germanVoiceName = name }
        fun chineseVoiceName(name: String) = apply { _chineseVoiceName = name }

        fun build() = LabibaVAVoice().apply {
            arabicVoiceName = _arabicVoiceName
            englishVoiceName = _englishVoiceName
            russianVoiceName = _russianVoiceName
            germanVoiceName = _germanVoiceName
            chineseVoiceName = _chineseVoiceName
        }

    }

    fun getVoiceBasedOnLanguage(language: LabibaLanguages): String {
        return when (language) {
            LabibaLanguages.ARABIC -> arabicVoiceName
            LabibaLanguages.RUSSIAN -> russianVoiceName
            LabibaLanguages.GERMANY -> germanVoiceName
            LabibaLanguages.CHINESE -> chineseVoiceName
            else -> englishVoiceName
        }
    }

}