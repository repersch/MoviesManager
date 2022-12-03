package br.edu.ifsp.scl.ads.pdm.moviesmanager.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.pdm.moviesmanager.databinding.ActivityGeneroBinding
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.entity.Constant.GENERO_EXTRA
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.entity.Genero

class GeneroActivity: AppCompatActivity() {

    private val agb: ActivityGeneroBinding by lazy {
        ActivityGeneroBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(agb.root)

        agb.salvarBt.setOnClickListener {
            val genero = Genero(
                id = -1,
                nome = agb.nomeGeneroEt.text.toString()
            )
            setResult(RESULT_OK, Intent().putExtra(GENERO_EXTRA, genero))
            finish()
        }
    }
}