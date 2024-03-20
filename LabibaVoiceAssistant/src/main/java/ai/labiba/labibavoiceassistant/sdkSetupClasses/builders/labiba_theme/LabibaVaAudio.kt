package ai.labiba.labibavoiceassistant.sdkSetupClasses.builders.labiba_theme

import ai.labiba.labibavoiceassistant.R
import ai.labiba.labibavoiceassistant.utils.ext.toPx

class LabibaVaAudio {

    internal var playIconDrawable:Any = R.drawable.ic_play
    internal var pauseIconDrawable:Any = R.drawable.ic_pause
    internal var iconColor:String = "#000000"


    internal var buttonBackgroundColor:String = "#f0ede8"
    internal var buttonStrokeWidth:Int= 1
    internal var buttonStrokeColor:String = "#000000"

    internal var durationTextColor:String = "#000000"
    internal var durationTextSize:Float = 10f

    internal var trackColor:String = "#1A000000"
    internal var trackCornerRadius:Int = 8.toPx
    internal var trackThickness:Int = 4.toPx

    internal var indicatorColor:String = "#000000"

    class Builder {

        private var _playIconDrawable: Any = LabibaVaAudio().playIconDrawable
        private var _pauseIconDrawable: Any = LabibaVaAudio().pauseIconDrawable
        private var _iconColor: String = LabibaVaAudio().iconColor

        private var _buttonBackgroundColor: String = LabibaVaAudio().buttonBackgroundColor
        private var _buttonStrokeWidth:Int  = LabibaVaAudio().buttonStrokeWidth
        private var _buttonStrokeColor: String = LabibaVaAudio().buttonStrokeColor

        private var _durationTextColor: String = LabibaVaAudio().durationTextColor
        private var _durationTextSize: Float = LabibaVaAudio().durationTextSize

        private var _trackColor: String = LabibaVaAudio().trackColor
        private var _trackCornerRadius: Int = LabibaVaAudio().trackCornerRadius
        private var _trackThickness: Int = LabibaVaAudio().trackThickness

        private var _indicatorColor: String = LabibaVaAudio().indicatorColor


        fun playIconDrawable(drawable: Any) = apply { this._playIconDrawable = drawable }
        fun pauseIconDrawable(drawable: Any) = apply { this._pauseIconDrawable = drawable }

        fun iconColor(color: String) = apply { this._iconColor = color }

        fun buttonBackgroundColor(color: String) = apply { this._buttonBackgroundColor = color }
        fun buttonStrokeWidth(width: Int) = apply { this._buttonStrokeWidth = width }
        fun buttonStrokeColor(color: String) = apply { this._buttonStrokeColor = color }

        fun durationTextColor(color: String) = apply { this._durationTextColor = color }
        fun durationTextSize(size: Float) = apply { this._durationTextSize = size }

        fun trackColor(color: String) = apply { this._trackColor = color }
        fun trackCornerRadius(radius: Int) = apply { this._trackCornerRadius = radius }
        fun trackThickness(thickness: Int) = apply { this._trackThickness = thickness }

        fun indicatorColor(color: String) = apply { this._indicatorColor = color }


        fun build() = LabibaVaAudio().apply {
            playIconDrawable = _playIconDrawable
            pauseIconDrawable = _pauseIconDrawable
            iconColor = _iconColor
            buttonBackgroundColor = _buttonBackgroundColor
            buttonStrokeWidth = _buttonStrokeWidth
            buttonStrokeColor = _buttonStrokeColor
            durationTextColor = _durationTextColor
            durationTextSize = _durationTextSize
            trackColor = _trackColor
            trackCornerRadius = _trackCornerRadius
            trackThickness = _trackThickness
            indicatorColor = _indicatorColor
        }
    }

}