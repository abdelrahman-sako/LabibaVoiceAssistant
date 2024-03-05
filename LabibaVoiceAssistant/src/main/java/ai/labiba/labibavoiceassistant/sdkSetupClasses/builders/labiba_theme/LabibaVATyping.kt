package ai.labiba.labibavoiceassistant.sdkSetupClasses.builders.labiba_theme

class LabibaVATyping {

    internal var animationDuration: Long = 1000L
    internal var backgroundColor: String = "#00ffffff"
    internal var strokeColor: String = "#000000"
    internal var width: Int = 2
    internal var radius: Float = 100f

    class Builder {
        private var _animationDuration: Long = LabibaVATyping().animationDuration
        private var _backgroundColor: String = LabibaVATyping().backgroundColor
        private var _strokeColor: String = LabibaVATyping().strokeColor
        private var _radius: Float = LabibaVATyping().radius
        private var _width: Int = LabibaVATyping().width

        fun setAnimationDuration(duration: Long) = apply { _animationDuration = duration }

        fun setBackgroundColor(color: String) = apply { _backgroundColor = color }

        fun setStrokeColor(color: String) = apply { _strokeColor = color }

        fun setRadius(radius: Float) = apply { _radius = radius }

        fun setWidth(width: Int) = apply { _width = width }

        fun build() = LabibaVATyping().apply {
            animationDuration = _animationDuration
            backgroundColor = _backgroundColor
            strokeColor = _strokeColor
            radius = _radius
            width = _width
        }
    }

}