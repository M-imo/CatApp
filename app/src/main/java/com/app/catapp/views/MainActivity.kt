package com.app.catapp.views

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.catapp.R
import com.app.catapp.databinding.ActivityMainBinding
import com.app.catapp.viewModels.CatMainViewModel
import com.bumptech.glide.Glide


//Koden definerer en aktivitet som lar brukeren hente og dele tilfeldige kattedata.
// Det er en knapp for å laste inn data, og en meny for å dele dataene.
// Når dataene lastes inn, vises de på skjermen, og brukeren kan dele dem ved å klikke på "Share" i menyen.

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CatMainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[CatMainViewModel::class.java]
        setContentView(binding.root)
        viewModel.uiState.observe(this) {
            if (it.loading != null) {
                binding.dataLayout.visibility = View.GONE
                binding.errorMessage.visibility = View.GONE
                binding.loadingIndicator.visibility = View.VISIBLE
            } else if (it.errorMessage != null) {
                binding.dataLayout.visibility = View.GONE
                binding.errorMessage.visibility = View.VISIBLE
                binding.errorMessage.text = it.errorMessage
                binding.loadingIndicator.visibility = View.GONE
            } else {
                binding.loadingIndicator.visibility = View.GONE
                binding.errorMessage.visibility = View.GONE
                binding.dataLayout.visibility = View.VISIBLE
                binding.fact.text = it.fact
                Glide
                    .with(this)
                    .load(it.image)
                    .centerCrop()
                    .into(binding.img)
            }
        }

        binding.btn.setOnClickListener {
            viewModel.loadData()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.share_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.share_fact) {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "${binding.fact.text}"
            )
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }
        return super.onOptionsItemSelected(item)
    }
}