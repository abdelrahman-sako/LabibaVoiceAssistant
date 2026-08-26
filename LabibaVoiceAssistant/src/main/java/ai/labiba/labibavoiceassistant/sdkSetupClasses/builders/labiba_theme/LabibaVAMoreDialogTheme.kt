package ai.labiba.labibavoiceassistant.sdkSetupClasses.builders.labiba_theme

class LabibaVAMoreDialogTheme {

    internal var reportButtonPosition: ReportButtonPosition = ReportButtonPosition.DEFAULT
    internal var backgroundColor: String = "#ffffff"
    internal var iconsColor: String = "#000000"
    internal var textColor:String = "#000000"

    class Builder {
        private var _reportButtonPosition: ReportButtonPosition = LabibaVAMoreDialogTheme().reportButtonPosition
        private var _backgroundColor: String = LabibaVAMoreDialogTheme().backgroundColor
        private var _iconsColor: String = LabibaVAMoreDialogTheme().iconsColor
        private var _textColor: String = LabibaVAMoreDialogTheme().textColor

        fun setMoreButtonPosition(position: ReportButtonPosition) = apply { _reportButtonPosition = position }
        fun setBackgroundColor(color: String) = apply { _backgroundColor = color }
        fun setIconsColor(color: String) = apply {  _iconsColor= color }
        fun setTextColor(color: String) = apply { _textColor = color }


        fun build() = LabibaVAMoreDialogTheme().apply {
            reportButtonPosition = _reportButtonPosition
            backgroundColor = _backgroundColor
            iconsColor = _iconsColor
            textColor = _textColor

        }
    }




}

enum class ReportButtonPosition{
    DEFAULT,TOP_END;
}