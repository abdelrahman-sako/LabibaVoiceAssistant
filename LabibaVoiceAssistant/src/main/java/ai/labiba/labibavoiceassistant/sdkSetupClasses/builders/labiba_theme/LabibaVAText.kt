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
    internal var scaledDownTextSize:Int = (textSize * 0.5f).toInt()

    /**
     * the Builder contains the below functions
     * textColor()
     * backgroundColor()
     * cornerRadius()
     * */

    class Builder {
        private var _textColor: String = LabibaVAText().textColor
        private var _textSize: Int = LabibaVAText().textSize
        private var _scaledDownTextSize = LabibaVAText().scaledDownTextSize

        fun textColor(color: String) = apply { this._textColor = color }
        fun textSize(size: Int) = apply { this._textSize = size }

        fun scaledDownTextSize(scaledDownSize: Int) = apply { this._scaledDownTextSize = scaledDownSize }


        fun build() = LabibaVAText().apply {
            textColor = _textColor
            textSize = _textSize
            scaledDownTextSize = _scaledDownTextSize
        }
    }
}
