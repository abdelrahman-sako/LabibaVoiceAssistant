package ai.labiba.labibavoiceassistant.enums

enum class MediaType(val type:String) {
    IMAGE("image"),
    AUDIO("audio"),
    VIDEO("video");

    companion object {
        private val map = MediaType.values().associateBy(MediaType::type)
        fun fromString(type: String) = map[type]
    }
}