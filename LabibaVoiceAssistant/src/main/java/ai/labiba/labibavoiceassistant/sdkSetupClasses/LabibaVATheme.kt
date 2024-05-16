package ai.labiba.labibavoiceassistant.sdkSetupClasses

import ai.labiba.labibavoiceassistant.sdkSetupClasses.builders.LabibaVAVoice
import ai.labiba.labibavoiceassistant.sdkSetupClasses.builders.labiba_theme.LabibaVACards
import ai.labiba.labibavoiceassistant.sdkSetupClasses.builders.labiba_theme.LabibaVAChoices
import ai.labiba.labibavoiceassistant.sdkSetupClasses.builders.labiba_theme.LabibaVAGeneral
import ai.labiba.labibavoiceassistant.sdkSetupClasses.builders.labiba_theme.LabibaVASuggestions
import ai.labiba.labibavoiceassistant.sdkSetupClasses.builders.labiba_theme.LabibaVAText
import ai.labiba.labibavoiceassistant.sdkSetupClasses.builders.labiba_theme.LabibaVAThemeSettings
import ai.labiba.labibavoiceassistant.sdkSetupClasses.builders.labiba_theme.LabibaVATyping
import ai.labiba.labibavoiceassistant.sdkSetupClasses.builders.labiba_theme.LabibaVaAudio

class LabibaVATheme {

    var themeSettings: LabibaVAThemeSettings = LabibaVAThemeSettings.Builder().build()

    var general: LabibaVAGeneral = LabibaVAGeneral.Builder().build()

    var voice: LabibaVAVoice = LabibaVAVoice.Builder().build()

    var typing: LabibaVATyping = LabibaVATyping.Builder().build()

    var userText: LabibaVAText = LabibaVAText.Builder().build()

    var botText: LabibaVAText = LabibaVAText.Builder().build()

    var cards: LabibaVACards = LabibaVACards()

    var choices: LabibaVAChoices = LabibaVAChoices()

    var audio = LabibaVaAudio()

    var suggestions = LabibaVASuggestions()

}