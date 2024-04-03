package ai.labiba.labibavoiceassistant.sdkSetupClasses.builders.labiba_theme

import android.graphics.Color
import android.graphics.drawable.GradientDrawable

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

    internal var cardTopLeftCorner:Float = 14f
    internal var cardTopRightCorner:Float = 14f
    internal var cardBottomLeftCorner:Float = 14f
    internal var cardBottomRightCorner:Float = 14f

    internal var cardBackgroundColor = "#00000000"
    internal var cardBackgroundGradientDrawable:GradientDrawable? = null
    internal var cardStrokeColor = "#00000000"
    internal var cardStrokeWith:Int = 0

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

        private var _cardTopLeftCorner: Float = LabibaVAText().cardTopLeftCorner
        private var _cardTopRightCorner: Float = LabibaVAText().cardTopRightCorner
        private var _cardBottomLeftCorner: Float = LabibaVAText().cardBottomLeftCorner
        private var _cardBottomRightCorner: Float = LabibaVAText().cardBottomRightCorner

        private var _cardBackgroundColor: String = LabibaVAText().cardBackgroundColor
        private var _cardStrokeColor: String = LabibaVAText().cardStrokeColor
        private var _cardStrokeWith: Int = LabibaVAText().cardStrokeWith
        private var _cardBackgroundGradientDrawable: GradientDrawable? = LabibaVAText().cardBackgroundGradientDrawable


        fun cardTopLeftCorner(corner: Float) = apply { this._cardTopLeftCorner = corner }
        fun cardTopRightCorner(corner: Float) = apply { this._cardTopRightCorner = corner }
        fun cardBottomLeftCorner(corner: Float) = apply { this._cardBottomLeftCorner = corner }
        fun cardBottomRightCorner(corner: Float) = apply { this._cardBottomRightCorner = corner }

        fun cardBackgroundColor(color: String) = apply { this._cardBackgroundColor = color }
        fun cardStrokeColor(color: String) = apply { this._cardStrokeColor = color }
        fun cardStrokeWith(width: Int) = apply { this._cardStrokeWith = width }

        fun textColor(color: String) = apply { this._textColor = color }
        fun textSize(size: Int) = apply { this._textSize = size }

        fun scaledDownTextSize(scaledDownSize: Int) = apply { this._scaledDownTextSize = scaledDownSize }

        fun cardBackgroundGradientDrawable(gradientDrawable: GradientDrawable) = apply { this._cardBackgroundGradientDrawable = gradientDrawable }


        fun build() = LabibaVAText().apply {
            textColor = _textColor
            textSize = _textSize
            scaledDownTextSize = _scaledDownTextSize
            cardTopLeftCorner = _cardTopLeftCorner
            cardTopRightCorner = _cardTopRightCorner
            cardBottomLeftCorner = _cardBottomLeftCorner
            cardBottomRightCorner = _cardBottomRightCorner
            cardBackgroundColor = _cardBackgroundColor
            cardStrokeColor = _cardStrokeColor
            cardStrokeWith = _cardStrokeWith
            cardBackgroundGradientDrawable = _cardBackgroundGradientDrawable
        }
    }
}
