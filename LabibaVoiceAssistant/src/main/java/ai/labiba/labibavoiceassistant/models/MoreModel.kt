package ai.labiba.labibavoiceassistant.models

import androidx.annotation.DrawableRes

data class MoreModel(val id:Int,@DrawableRes val icon:Int,val title:String)


enum class MoreEnum(val id: Int){
    REPORT(1);
}