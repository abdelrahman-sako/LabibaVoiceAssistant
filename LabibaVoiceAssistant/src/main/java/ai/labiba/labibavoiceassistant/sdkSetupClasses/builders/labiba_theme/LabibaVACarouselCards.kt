package ai.labiba.labibavoiceassistant.sdkSetupClasses.builders.labiba_theme

class LabibaVACarouselCards {
    internal var titleColor: String = "#ffffff"
    internal var subtitleColor: String = "#ffffff"
    internal var buttonsTextColor: String = "#ffffff"
    internal var backgroundColor: String = "#000000"
    internal var cornersRadius: Float = 5f
    internal var isFullImageCard: Boolean = false

    class Builder {
        private var _titleColor: String = LabibaVACarouselCards().titleColor
        private var _subtitleColor: String = LabibaVACarouselCards().subtitleColor
        private var _buttonsTextColor: String = LabibaVACarouselCards().buttonsTextColor
        private var _backgroundColor: String = LabibaVACarouselCards().backgroundColor
        private var _cornersRadius: Float = LabibaVACarouselCards().cornersRadius
        private var _isFullImageCard: Boolean = LabibaVACarouselCards().isFullImageCard

        fun titleColor(color: String) = apply { this._titleColor = color }

        fun subtitleColor(color: String) = apply { this._subtitleColor = color }

        fun buttonsTextColor(color: String) = apply { this._buttonsTextColor = color }

        fun backgroundColor(color: String) = apply { this._backgroundColor = color }

        fun cornerRadius(radius: Float) = apply { this._cornersRadius = radius }

        fun enableFullImageCard(enable: Boolean) = apply { this._isFullImageCard = enable }

        fun build() = LabibaVACarouselCards().apply {
            titleColor = _titleColor
            subtitleColor = _subtitleColor
            buttonsTextColor = _buttonsTextColor
            backgroundColor = _backgroundColor
            cornersRadius = _cornersRadius
            isFullImageCard = _isFullImageCard
        }
    }

}