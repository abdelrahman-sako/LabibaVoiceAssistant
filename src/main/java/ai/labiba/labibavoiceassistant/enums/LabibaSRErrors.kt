package ai.labiba.labibavoiceassistant.enums

enum class LabibaSRErrors {
    ERROR_NETWORK_TIMEOUT,
    ERROR_NETWORK,
    ERROR_AUDIO,
    ERROR_SERVER,
    ERROR_CLIENT,
    ERROR_SPEECH_TIMEOUT,
    ERROR_NO_MATCH,
    ERROR_RECOGNIZER_BUSY,
    ERROR_INSUFFICIENT_PERMISSIONS,
    ERROR_TOO_MANY_REQUESTS,
    ERROR_SERVER_DISCONNECTED,
    ERROR_LANGUAGE_NOT_SUPPORTED,
    ERROR_LANGUAGE_UNAVAILABLE,
    ERROR_CANNOT_CHECK_SUPPORT;

    private val enMessages = arrayOf(
        "Network operation timed out.",
        "Other network related errors.",
        "Audio recording error.",
        "Server sends error status.",
        "Other client side errors.",
        "No speech input.",
        "No recognition result matched.",
        "RecognitionService busy.",
        "Insufficient permissions.",
        "Too many requests from the same client.",
        "Server has been disconnected.",
        "Requested language is not available to be used with the current recognizer.",
        "Requested language is supported, but not available currently.",
        "The service does not allow to check for support."
    )

    private val arMessages = arrayOf(
        "انتهت مهلة الاتصال. يُرجى إعادة المحاولة لاحقا.",
        "حدث خطأ اتصال بالشبكة. يُرجى إعادة المحاولة لاحقا.",
        "حدث خطأ في تسجيل الصوت.  يُرجى إعادة المحاولة لاحقا.",
        "عذرًا حصل خطأ بالاتصال بالخادم. يُرجى المحاولة لاحقا.",
        "عذرًا، حدث خطأ في الاتصال بالخدمة. يُرجى المحاولة لاحقا.",
        "حدث خطأ في تلقي الأوامر الصوتية. يُرجى المحاولة لاحقا.",
        "يرجى التحقق من اتصالك بالإنترنت.",
        "يرجى التحقق من اتصالك بالإنترنت و إعادة المحاولة",
        "يرجى التحقق من تفعيل خدمات الأوامر الصوتية.",
        "عذرًا، حصل خطأ بسبب كثرة الأوامر الصادر من المستخدم.",
        "تعذر الاتصال بالخادم.",
        "يرجى التحقق من اتصالك بالإنترنت. وإعادة المحاولة.",
        "يرجى التحقق من اتصالك بالإنترنت. وإعادة المحاولة مرة اخرى.",
        "تعذّر الوصول إلى خدمة الأوامر الصوتية. يُرجى المحاولة لاحقا."
    )

    fun getMessage(language: LabibaLanguages): String {
        return if (language === LabibaLanguages.ARABIC) {
            arMessages[ordinal]
        } else {
            enMessages[ordinal]
        }
    }

    fun getEnMessages(): String? {
        return enMessages[ordinal]
    }

    fun getArMessages(): String? {
        return arMessages[ordinal]
    }


}