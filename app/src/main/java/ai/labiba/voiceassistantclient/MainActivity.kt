package ai.labiba.voiceassistantclient

import ai.labiba.labibavoiceassistant.interfaces.LabibaVaDataCallbackInterface
import ai.labiba.labibavoiceassistant.sdkSetupClasses.LabibaVA
import ai.labiba.labibavoiceassistant.sdkSetupClasses.LabibaVATheme
import ai.labiba.labibavoiceassistant.sdkSetupClasses.builders.LabibaVAUrls
import ai.labiba.labibavoiceassistant.sdkSetupClasses.builders.labiba_theme.LabibaVAGeneral
import ai.labiba.labibavoiceassistant.sdkSetupClasses.builders.labiba_theme.LabibaVAMoreDialogTheme
import ai.labiba.labibavoiceassistant.sdkSetupClasses.builders.labiba_theme.LabibaVAText
import ai.labiba.labibavoiceassistant.sdkSetupClasses.builders.labiba_theme.LabibaVAThemeSettings
import ai.labiba.labibavoiceassistant.sdkSetupClasses.builders.labiba_theme.ReportButtonPosition
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //add recipient id
        LabibaVA.addRecipientId(BuildConfig.BOT_ID, LabibaVA.English)

        //add urls
        LabibaVA.urls = LabibaVAUrls.Builder().generalBaseUrl(BuildConfig.BASE_URL)
            .voiceBaseUrl(BuildConfig.VOICE_BASE_URL)
            .messagingPath(BuildConfig.MESSAGING_PATH).voicePath(BuildConfig.VOICE_PATH)
            .build()


        val theme = LabibaVATheme()

        //general settings
        theme.themeSettings =
            LabibaVAThemeSettings.Builder().enableAutoListening(true).enableTextToSpeech(false)
                .build()

        theme.general = LabibaVAGeneral.Builder()
            .setWavesColor("#e7e3dc")
            .setStatusBarColor("#9a4a3a")
            .setMicBackgroundColor("#9a4a3a")
            .setMicIconColorColor("#ffffff")
            .setBackgroundColor("#f9f8f6")
            .setTypingIndicatorColor("#FF0000")
            .build()

        //text theme
        theme.botText = LabibaVAText.Builder().textColor("#66000000").build()
        theme.userText = LabibaVAText.Builder().textColor("#101a28").build()
        theme.moreDialog = LabibaVAMoreDialogTheme.Builder().setMoreButtonPosition(ReportButtonPosition.DEFAULT).build()


        //set theme
        LabibaVA.setLabibaVaTheme(theme)


        LabibaVA.setLabibaVaDataCallback(object : LabibaVaDataCallbackInterface {
            override fun onDataRetrieved(map: Map<String, String>) {
                //call back when data is retrieved
            }

        })


        findViewById<Button>(R.id.va_btn).setOnClickListener {
            LabibaVA.startConversation(this.supportFragmentManager, LabibaVA.English)
        }


    }


}