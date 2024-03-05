package ai.labiba.labibavoiceassistant.sdkSetupClasses.builders.labiba_theme


class LabibaVAThemeSettings {

    internal var isAutoListening = false
    internal var isTextToSpeechEnabled = true
    internal var autoDetectLanguage = true


    class Builder {
        private var _isAutoListening = LabibaVAThemeSettings().isAutoListening
        private var _isTextToSpeechEnabled = LabibaVAThemeSettings().isTextToSpeechEnabled
        private var _autoDetectLanguage = LabibaVAThemeSettings().isTextToSpeechEnabled


        fun enableAutoListening(enable: Boolean) = apply { this._isAutoListening = enable }
        fun enableTextToSpeech(enable: Boolean) = apply { this._isTextToSpeechEnabled = enable }
        fun enableAutoDetectLanguage(enable: Boolean) = apply { this._autoDetectLanguage = enable }


        fun build() = LabibaVAThemeSettings().apply {
            isTextToSpeechEnabled = _isTextToSpeechEnabled
            isAutoListening = _isAutoListening
            autoDetectLanguage = _autoDetectLanguage

        }
    }

}