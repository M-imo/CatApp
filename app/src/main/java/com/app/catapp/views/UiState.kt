package com.app.catapp.views


//Koden definerer en dataklasse kalt UiState som brukes til å representere tilstanden til grensesnittet (UI)
data class UiState(
    var loading: Boolean? = null,
    var errorMessage: String? = null,
    var fact: String = "",
    var image: String = "",
)
