package ai.labiba.labibavoiceassistant.models

import ai.labiba.labibavoiceassistant.enums.LabibaLanguages

internal data class Header(
    val image:Any?=null,
    val title:String?=null,
    val subtitle:String?=null,
    val languages: LabibaLanguages = LabibaLanguages.ARABIC
)
