package br.edu.ifsp.scl.ads.pdm.moviesmanager.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.pdm.moviesmanager.databinding.ActivityFilmeBinding
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.Constant.FILME_EXTRA
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.Constant.LISTA_DE_GENEROS
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.Constant.VIEW_FILME
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.entity.Filme
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.entity.Genero

class FilmeActivity: AppCompatActivity() {

    private val afb: ActivityFilmeBinding by lazy {
        ActivityFilmeBinding.inflate(layoutInflater)
    }

    private val listaDeGeneros: MutableList<Genero> by lazy {
        intent.getParcelableArrayListExtra<Genero>(LISTA_DE_GENEROS)!!
    }

    private lateinit var idGeneroSelecionado: Any

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(afb.root)

        val arrayAdapter = ArrayAdapter<String>(
            this,
            androidx.constraintlayout.widget.R.layout.select_dialog_item_material,
            listaDeGeneros.map { g -> g.nome })
        afb.generoSp.adapter = arrayAdapter

        val filmeRecebido = intent.getParcelableExtra<Filme>(FILME_EXTRA)
        filmeRecebido?.let { _filmeRecebido ->
            with(afb) {
                with(_filmeRecebido) {
                    tituloTv.setText("Filme #${id}")
                    nomeEt.setText(nome)
                    nomeEt.isEnabled = false
                    anoLancamentoEt.setText(anoLancamento.toString())
                    produtoraEt.setText(produtora)
                    tempoDuracaoEt.setText(tempoDeDuracao.toString())
                    generoSp.setSelection(idGenero - 1)
                    if (assistido == 1) {
                        simRb.setChecked(true)
                        notaEt.visibility = View.VISIBLE
                        notaEt.setText(nota.toString())
                    } else naoRb.setChecked(true)
                }
            }
        }

        val viewFilme = intent.getBooleanExtra(VIEW_FILME, false)
        if (viewFilme) {
            afb.nomeEt.isEnabled = false
            afb.anoLancamentoEt.isEnabled = false
            afb.produtoraEt.isEnabled = false
            afb.tempoDuracaoEt.isEnabled = false
            afb.notaEt.isEnabled = false
            afb.generoSp.isEnabled = false
            afb.simRb.isEnabled = false
            afb.naoRb.isEnabled = false
            afb.salvarBt.visibility = View.GONE
        }

        afb.simRb.setOnClickListener { afb.notaEt.visibility = View.VISIBLE }

        afb.naoRb.setOnClickListener { afb.notaEt.visibility = View.GONE }

        afb.generoSp.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val nomeGeneroSelecionado = afb.generoSp.selectedItem
                idGeneroSelecionado = listaDeGeneros.indexOfFirst { it.nome == nomeGeneroSelecionado }
            }
        }

        afb.salvarBt.setOnClickListener {
            val filme = Filme(
                id = filmeRecebido?.id?: -1,
                nome = afb.nomeEt.text.toString(),
                anoLancamento = afb.anoLancamentoEt.text.toString().toInt(),
                produtora = afb.produtoraEt.text.toString(),
                tempoDeDuracao = afb.tempoDuracaoEt.text.toString().toInt(),
                assistido = if (afb.simRb.isChecked) 1 else 0,
                nota = if (afb.notaEt.text.isNotEmpty()) afb.notaEt.text.toString().toInt() else null,
                idGenero = idGeneroSelecionado as Int + 1
            )
            val resultIntent = Intent().putExtra(FILME_EXTRA, filme)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
