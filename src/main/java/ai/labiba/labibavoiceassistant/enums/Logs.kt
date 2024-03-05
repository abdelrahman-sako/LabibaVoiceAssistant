package ai.labiba.labibavoiceassistant.enums

internal enum class Logs {
    MESSAGING,
    TEXT_TO_SPEECH,
    SPEECH_TO_TEXT;

    private val names = arrayOf(
        "MESSAGING",
        "TEXT_TO_SPEECH",
        "SPEECH_TO_TEXT"
    )


    fun getTag() = names[ordinal]

}