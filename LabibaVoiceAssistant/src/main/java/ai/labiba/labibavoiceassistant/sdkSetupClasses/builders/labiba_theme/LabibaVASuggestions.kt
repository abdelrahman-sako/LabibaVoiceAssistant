package ai.labiba.labibavoiceassistant.sdkSetupClasses.builders.labiba_theme

import ai.labiba.labibavoiceassistant.utils.ext.toPx

class LabibaVASuggestions {

    internal var textColor: String = "#000000"
    internal var textSize: Int = 15

    internal var backgroundColor: String = "#ffffff"
    internal var radius: Int = 15

    internal var strokeColor: String = "#000000"
    internal var strokeWidth: Float = 0.5f

    internal var horizontalSpacing = 15.toPx
    internal var verticalSpacing = 15.toPx


    class Builder {
        private var _textColor: String = LabibaVASuggestions().textColor
        private var _backgroundColor: String = LabibaVASuggestions().backgroundColor
        private var _strokeColor: String = LabibaVASuggestions().strokeColor
        private var _strokeWidth: Float = LabibaVASuggestions().strokeWidth
        private var _textSize: Int = LabibaVASuggestions().textSize
        private var _radius: Int = LabibaVASuggestions().radius
        private var _horizontalSpacing: Int = LabibaVASuggestions().horizontalSpacing
        private var _verticalSpacing: Int = LabibaVASuggestions().verticalSpacing

        fun textColor(color: String) = apply { this._textColor = color }
        fun textSize(size: Int) = apply { this._textSize = size }

        fun backgroundColor(color: String) = apply { this._backgroundColor = color }

        fun strokeColor(color: String) = apply { this._strokeColor = color }

        fun strokeWidth(width: Float) = apply { this._strokeWidth = width }

        fun radius(radius: Int) = apply { this._radius = radius }

        fun horizontalSpacing(spacing: Int) = apply { this._horizontalSpacing = spacing }

        fun verticalSpacing(spacing: Int) = apply { this._verticalSpacing = spacing }


        fun build() = LabibaVASuggestions().apply {
            textColor = _textColor
            textSize = _textSize
            backgroundColor = _backgroundColor
            strokeColor = _strokeColor
            strokeWidth = _strokeWidth
            radius = _radius
            horizontalSpacing = _horizontalSpacing
            verticalSpacing = _verticalSpacing
        }
    }

}