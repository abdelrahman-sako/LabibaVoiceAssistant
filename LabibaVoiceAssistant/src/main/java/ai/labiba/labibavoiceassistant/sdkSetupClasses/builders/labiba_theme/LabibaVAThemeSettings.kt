package ai.labiba.labibavoiceassistant.sdkSetupClasses.builders.labiba_theme


class LabibaVAThemeSettings {

    internal var isAutoListening = false
    internal var isTextToSpeechEnabled = true
    internal var autoDetectLanguage = true
    internal var useUnsafeOkHttpClient = false


    class Builder {
        private var _isAutoListening = LabibaVAThemeSettings().isAutoListening
        private var _isTextToSpeechEnabled = LabibaVAThemeSettings().isTextToSpeechEnabled
        private var _autoDetectLanguage = LabibaVAThemeSettings().isTextToSpeechEnabled
        private var _useUnsafeOkHttpClient = LabibaVAThemeSettings().useUnsafeOkHttpClient


        fun enableAutoListening(enable: Boolean) = apply { this._isAutoListening = enable }
        fun enableTextToSpeech(enable: Boolean) = apply { this._isTextToSpeechEnabled = enable }
        fun enableAutoDetectLanguage(enable: Boolean) = apply { this._autoDetectLanguage = enable }
        fun useUnsafeOkHttpClient(use: Boolean) = apply { this._useUnsafeOkHttpClient = use }


        fun build() = LabibaVAThemeSettings().apply {
            isTextToSpeechEnabled = _isTextToSpeechEnabled
            isAutoListening = _isAutoListening
            autoDetectLanguage = _autoDetectLanguage
            useUnsafeOkHttpClient = _useUnsafeOkHttpClient
        }
    }

}