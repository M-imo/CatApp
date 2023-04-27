package com.app.catapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.catapp.api.ApiClient
import com.app.catapp.api.cat_facts.CatFactsApi
import com.app.catapp.api.cat_images.CatImagesApi
import com.app.catapp.model.CatFact
import com.app.catapp.model.CatImage
import com.app.catapp.views.UiState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


//Koden definerer en ViewModel-klass kalt CatMainViewModel, som laster ned en tilfeldig kattfakta og bilde fra separate API-er, og oppdaterer grensesnittet i henhold til responsen.
//
//ViewModelen har en MutableLiveData-variabel kalt _uiState, som oppdateres når nye data mottas fra API-ene. En LiveData-variabel kalt uiState brukes til å observere endringer i grensesnittet.
//
//ViewModelen bruker en funksjon kalt loadData() som kaller API-en CatFactsApi for å hente tilfeldige kattfakta. Når responsen mottas, velger koden det første kattfaktumet som er verifisert, og kaller funksjonen getCatImage() med dette faktumet. getCatImage() funksjonen bruker CatImagesApi til å hente et tilfeldig bilde, og oppdaterer deretter _uiState-variabelen med kattfakta-teksten og bilde-URL-en før den sender dataene til grensesnittet via LiveData-variabelen.
//
//Hvis det oppstår en feil under lasting av data, oppdateres _uiState-variabelen med en feilmelding som indikerer årsaken til feilen.
//
//En init-blokk brukes til å starte lasting av data når ViewModelen opprettes.

class CatMainViewModel : ViewModel() {
    private var _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState
    private val facts = ApiClient().client(true)?.create(CatFactsApi::class.java)!!
    private val images = ApiClient().client(false)?.create(CatImagesApi::class.java)!!

    fun loadData() {
        _uiState.value = UiState(loading = true)
        facts.getFacts().enqueue(object : Callback<List<CatFact>> {
            override fun onResponse(call: Call<List<CatFact>>, response: Response<List<CatFact>>) {
                if (response.isSuccessful && response.body() != null) {
                    for (fact in response.body()!!) {
                        if (fact.status.verified != null && fact.status.verified) {
                            getCatImage(fact)
                            break
                        }
                    }
                } else {
                    _uiState.value = UiState(errorMessage = "Unknown Exception")
                }
            }

            override fun onFailure(call: Call<List<CatFact>>, t: Throwable) {
                _uiState.value = UiState(errorMessage = t.localizedMessage ?: "Unknown Exception")
            }
        })
    }

    private fun getCatImage(fact: CatFact) {
        images.getImages().enqueue(object : Callback<List<CatImage>> {
            override fun onResponse(
                call: Call<List<CatImage>>,
                response: Response<List<CatImage>>,
            ) {
                if (response.isSuccessful && response.body() != null) {
                    _uiState.value = UiState(fact = fact.text, image = response.body()!![0].url)
                } else {
                    _uiState.value = UiState(errorMessage = "Unknown Exception")
                }
            }

            override fun onFailure(call: Call<List<CatImage>>, t: Throwable) {
                _uiState.value = UiState(errorMessage = t.localizedMessage ?: "Unknown Exception")
            }
        })
    }

    init {
        loadData()
    }
}