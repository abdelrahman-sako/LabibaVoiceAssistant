package ai.labiba.labibavoiceassistant.sdkSetupClasses

import ai.labiba.labibavoiceassistant.sdkSetupClasses.builders.LabibaVAUrls
import ai.labiba.labibavoiceassistant.sdkSetupClasses.builders.LabibaVAVoice
import ai.labiba.labibavoiceassistant.enums.LabibaLanguages
import ai.labiba.labibavoiceassistant.interfaces.LabibaVaDataCallbackInterface
import ai.labiba.labibavoiceassistant.other.Constants
import ai.labiba.labibavoiceassistant.ui.dialogs.mainDialog.MainDialog
import android.view.View
import androidx.annotation.Keep
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

@Keep
object LabibaVA {
    const val Arabic = "ARABIC"
    const val English = "ENGLISH"
    const val Russian = "RUSSIAN"
    const val Germany = "GERMANY"
    const val Chinese = "CHINESE"

    var urls: LabibaVAUrls = LabibaVAUrls()
    var voice: LabibaVAVoice = LabibaVAVoice()




    /**
     * Adding new bot id to start conversation on
     * @param id bot-id (contact with business team to get the ids)
     * @param language the chosen id language
     * */
    fun addRecipientId(id: String, language: String) {
        val addedLanguage = LabibaLanguages.fromString(language)
            ?: throw Exception("The passed language name ($language) is not correct or the language is not supported, please use for example (Labiba.Russian) to pass the correct language name")

        if (id.isEmpty()) {
            throw Exception("The passed recipient-Id cant be empty")
        }

        Constants.recipientIds.forEach {
            if (it.second == addedLanguage) {
                Constants.recipientIds.remove(it)
            }
        }

        Constants.recipientIds.add(Pair(id, addedLanguage))
    }


    fun setLabibaVaTheme(labibaVATheme:LabibaVATheme){
        LabibaVAInternal.labibaVaTheme = labibaVATheme
    }

    private fun setStartLanguage(language: String) {
        val startLanguage = LabibaLanguages.fromString(language)
            ?: throw Exception("The passed start language name ($language) is not correct or the language is not supported, please use for example (Labiba.Russian) to pass the correct language name")

        Constants.languageName = startLanguage
        Constants.voiceLanguage = startLanguage.getLanguageCode()
    }

    fun setLabibaVaDataCallback(labibaVADataCallback: LabibaVaDataCallbackInterface) {
        LabibaVAInternal.setLabibaVaDataCallback(labibaVADataCallback)
    }

    fun getDialog():BottomSheetDialogFragment?{
        return LabibaVAInternal.dialog
    }

    fun injectCustomView(customView: View) {
        LabibaVAInternal.injectCustomView(customView)
    }

    fun injectTyping(){
        LabibaVAInternal.injectTyping()
    }

    fun clearChat(){
        LabibaVAInternal.clearChat()
    }

    fun clearTypingAndChoices(){
        LabibaVAInternal.clearTypingAndChoices()
    }

    fun addGifBackground(url:String,elevation:Float = 0f,loop:Boolean = true){
        LabibaVAInternal.addGifBackground(url,elevation,loop)
    }

    fun startConversation(
        supportFragmentManager: FragmentManager,
        language: String = Constants.languageName.value,
        flag: Map<String,Any> = mapOf(),
        fullScreen:Boolean = false
    ) {
        setStartLanguage(language)
        LabibaVAInternal.flags = flag.toMutableMap()
        LabibaVAInternal.fullScreen = fullScreen

        if (!urls.isBaseAdded())
            throw Exception("Urls not added, please use Labiba.urls = LabibaUrls.Builder()... and add server urls (you need at least to add the generalBaseUrl and messagingPath), if you don't have the please contact with the business team.")

        //close previous dialog if exists
        getDialog()?.dismiss()

        val mainDialog = MainDialog()
        mainDialog.show(supportFragmentManager, "mainDialog")
    }

}