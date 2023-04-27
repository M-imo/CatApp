package com.app.catapp.model

//Koden definerer en dataklasse kalt Status som brukes til å representere statusinformasjon om et objekt i en nettverksforespørsel.
// Dataklassen har tre egenskaper (sentCount, verified og feedback) som kan inneholde ulike datatyper.
// Annotasjonen SerializedName brukes til å indikere hvordan dataene skal serialiseres og deserialiseres til/fra JSON-format.

import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("sentCount")
    val sentCount: Int,
    @SerializedName("verified")
    val verified: Boolean? = null,
    @SerializedName("feedback")
    val feedback: String? = null,
)