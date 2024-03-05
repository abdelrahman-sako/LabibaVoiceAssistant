package ai.labiba.labibavoiceassistant.sdkSetupClasses.builders

class LabibaVAUrls {
    internal var generalBaseUrl: String = ""
    internal var voiceBaseUrl: String = ""
    internal var messagingPath: String = ""
    internal var voicePath: String = ""
    internal var loggingPath: String = ""
    internal var uploadPath: String = ""
    internal var ratingPath: String = ""

    internal fun isBaseAdded():Boolean = generalBaseUrl.isNotEmpty() && messagingPath.isNotEmpty()

    class Builder {
        private var _generalBaseUrl: String = LabibaVAUrls().generalBaseUrl
        private var _voiceBaseUrl: String = LabibaVAUrls().voiceBaseUrl
        private var _messagingPath: String = LabibaVAUrls().messagingPath
        private var _voicePath: String = LabibaVAUrls().voicePath
        private var _loggingPath: String = LabibaVAUrls().loggingPath
        private var _uploadPath: String = LabibaVAUrls().uploadPath
        private var _ratingPath: String = LabibaVAUrls().ratingPath

        fun generalBaseUrl(base: String) = apply { _generalBaseUrl = base }

        fun voiceBaseUrl(base: String) = apply { _voiceBaseUrl = base }

        fun messagingPath(path: String) = apply { _messagingPath = path }

        fun voicePath(path: String) = apply { _voicePath = path }

        fun loggingPath(path: String) = apply { _loggingPath = path }

        fun uploadPath(path: String) = apply { _uploadPath = path }

        fun ratingPath(path: String) = apply { _ratingPath = path }

        fun build() = LabibaVAUrls().apply {
            generalBaseUrl = _generalBaseUrl
            voiceBaseUrl = _voiceBaseUrl
            messagingPath = _messagingPath
            voicePath = _voicePath
            loggingPath = _loggingPath
            uploadPath = _uploadPath
            ratingPath = _ratingPath
        }

    }

}