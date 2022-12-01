package br.edu.ifsp.scl.ads.pdm.moviesmanager.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.pdm.moviesmanager.databinding.ActivityFilmeBinding
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.entity.Constant.FILME_EXTRA
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.entity.Filme

class FilmeActivity: AppCompatActivity() {
    private val afb: ActivityFilmeBinding by lazy {
        ActivityFilmeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(afb.root)

        val filmeRecebido = intent.getParcelableExtra<Filme>(FILME_EXTRA)
        filmeRecebido?.let { _filmeRecebido ->
            with(afb) {
                with(_filmeRecebido) {
                    nomeEt.setText(nome)
                    anoLancamentoEt.setText(anoLancamento)
                    produtoraEt.setText(produtora)
                    tempoDuracaoEt.setText(tempoDeDuracao)
                    if (assistido == 1)  simRb.isSelected else naoRb.isSelected
                    notaEt.setText(nota.toString())
//                    arrumar uma formar de aparecer o genero selecionado
//                    generoSp.setSelection()
                }
            }
        }

        afb.salvarBt.setOnClickListener {
            val filme = Filme(
                id = filmeRecebido?.id?: -1,
                nome = afb.nomeEt.text.toString(),
                anoLancamento = afb.anoLancamentoEt.text.toString().toInt(),
                produtora = afb.produtoraEt.text.toString(),
                tempoDeDuracao = afb.tempoDuracaoEt.text.toString().toInt(),
                assistido = if (afb.simRb.isSelected) 1 else 0,
                nota = afb.notaEt.text.toString().toInt(),
                genero = afb.generoSp.isSelected.toString()
            )
            val resultIntent = Intent().putExtra(FILME_EXTRA, filme)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
