package ai.labiba.labibavoiceassistant.sdkSetupClasses.builders.labiba_theme

/**
 * LabibaTextBubbles is a builder class so you need to call Builder and pass the colors
 * If you didn't add any color, the default colors will be added automatically
 * Example:
 *```
 * userTextBubbles = LabibaTextBubbles.Builder().textColor("#ffffff").build()
 * or
 * botTextBubbles = LabibaTextBubbles.Builder().textColor("#ffffff").build()
 *```
 * */
class LabibaVAText {
    internal var textColor: String = "#000000"
    internal var textSize: Int = 34

//    internal var backgroundColor: String = "#ffffff"
//    internal var strokeColor: String = "#00ffffff"
//    internal var cornersRadius: Float = 5f

    /**
     * the Builder contains the below functions
     * textColor()
     * backgroundColor()
     * cornerRadius()
     * */

    class Builder {
        private var _textColor: String = LabibaVAText().textColor
        private var _textSize: Int = LabibaVAText().textSize

//        private var _backgroundColor: String = LabibaVAText().backgroundColor
//        private var _strokeColor: String = LabibaVAText().strokeColor
//        private var _cornersRadius: Float = LabibaVAText().cornersRadius

        fun textColor(color: String) = apply { this._textColor = color }
        fun textSize(size: Int) = apply { this._textSize = size }

//        fun backgroundColor(color: String) = apply { this._backgroundColor = color }
//
//        fun strokeColor(color: String) = apply { this._strokeColor = color }
//
//        fun cornerRadius(radius: Float) = apply { this._cornersRadius = radius }

        fun build() = LabibaVAText().apply {
            textColor = _textColor
            textSize = _textSize
//            backgroundColor = _backgroundColor
//            cornersRadius = _cornersRadius
//            strokeColor = _strokeColor
        }
    }
}
