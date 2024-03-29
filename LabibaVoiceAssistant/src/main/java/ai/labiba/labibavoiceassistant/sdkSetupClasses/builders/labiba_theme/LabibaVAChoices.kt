package ai.labiba.labibavoiceassistant.sdkSetupClasses.builders.labiba_theme

import ai.labiba.labibavoiceassistant.utils.ext.toPx

class LabibaVAChoices {

    internal var titleColor: String = "#000000"
    internal var titleSize: Int = 34

    internal var textColor: String = "#000000"
    internal var textSize: Int = 15

    internal var backgroundColor: String = "#ffffff"
    internal var radius: Int = 15

    internal var strokeColor: String = "#000000"
    internal var strokeWidth: Float = 0.5f

    internal var horizontalSpacing = 15.toPx
    internal var verticalSpacing = 15.toPx

    class Builder {
        private var _titleColor: String = LabibaVAChoices().titleColor
        private var _textColor: String = LabibaVAChoices().textColor
        private var _backgroundColor: String = LabibaVAChoices().backgroundColor
        private var _strokeColor: String = LabibaVAChoices().strokeColor
        private var _strokeWidth: Float = LabibaVAChoices().strokeWidth
        private var _titleSize: Int = LabibaVAChoices().titleSize
        private var _textSize: Int = LabibaVAChoices().textSize
        private var _radius: Int = LabibaVAChoices().radius
        private var _horizontalSpacing: Int = LabibaVAChoices().horizontalSpacing
        private var _verticalSpacing: Int = LabibaVAChoices().verticalSpacing

        fun textColor(color: String) = apply { this._textColor = color }
        fun textSize(size: Int) = apply { this._textSize = size }

        fun titleColor(color: String) = apply { this._titleColor = color }

        fun backgroundColor(color: String) = apply { this._backgroundColor = color }

        fun strokeColor(color: String) = apply { this._strokeColor = color }

        fun strokeWidth(width: Float) = apply { this._strokeWidth = width }

        fun titleSize(size: Int) = apply { this._titleSize = size }

        fun radius(radius: Int) = apply { this._radius = radius }

        fun horizontalSpacing(spacing: Int) = apply { this._horizontalSpacing = spacing }
        fun verticalSpacing(spacing: Int) = apply { this._verticalSpacing = spacing }

        fun build() = LabibaVAChoices().apply {
            textColor = _textColor
            textSize = _textSize
            titleColor = _titleColor
            backgroundColor = _backgroundColor
            strokeColor = _strokeColor
            strokeWidth = _strokeWidth
            titleSize = _titleSize
            radius = _radius
            horizontalSpacing = _horizontalSpacing
            verticalSpacing = _verticalSpacing
        }
    }

}