package ai.labiba.labibavoiceassistant.sdkSetupClasses.builders.labiba_theme

class LabibaVAGeneral {
    internal var statusBarColor: String? = null
    internal var oldStatusBarColor: String? = null
    internal var backgroundColor: String = "#ffffff"
    internal var backgroundImageUrl: String = ""
    internal var wavesColor: String = "#000000"
    internal var micIconColor: String = "#ffffff"
    internal var micBackgroundColor: String = "#000000"
    internal var typingIndicatorColor:String = "#101a28"

    class Builder {
        private var _statusBarColor: String? = LabibaVAGeneral().statusBarColor
        private var _backgroundColor: String = LabibaVAGeneral().backgroundColor
        private var _backgroundImageUrl: String = LabibaVAGeneral().backgroundImageUrl
        private var _wavesColor: String = LabibaVAGeneral().wavesColor
        private var _micIconColor: String = LabibaVAGeneral().micIconColor
        private var _micBackgroundColor: String = LabibaVAGeneral().micBackgroundColor
        private var _typingIndicatorColor: String = LabibaVAGeneral().typingIndicatorColor


        fun setStatusBarColor(color: String) = apply { _statusBarColor = color }

        fun setBackgroundColor(color: String) = apply { _backgroundColor = color }

        fun setBackgroundImageUrl(url: String) = apply { _backgroundImageUrl = url }

        fun setWavesColor(color: String) = apply { _wavesColor = color }

        fun setMicIconColorColor(color: String) = apply { _micIconColor = color }

        fun setMicBackgroundColor(color: String) = apply { _micBackgroundColor = color }

        fun setTypingIndicatorColor(color: String) = apply { _typingIndicatorColor = color }

        fun build() = LabibaVAGeneral().apply {
            statusBarColor = _statusBarColor
            backgroundColor = _backgroundColor
            backgroundImageUrl = _backgroundImageUrl
            wavesColor = _wavesColor
            micIconColor = _micIconColor
            micBackgroundColor = _micBackgroundColor
            typingIndicatorColor = _typingIndicatorColor
        }
    }
}