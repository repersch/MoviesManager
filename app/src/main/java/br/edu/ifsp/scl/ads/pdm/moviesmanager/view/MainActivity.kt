package br.edu.ifsp.scl.ads.pdm.moviesmanager.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.ifsp.scl.ads.pdm.moviesmanager.R
import br.edu.ifsp.scl.ads.pdm.moviesmanager.databinding.ActivityMainBinding
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.entity.Filme

class MainActivity : AppCompatActivity() {

    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val listaDeFilmes: MutableList<Filme> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
    }
}