package br.edu.ifsp.scl.ads.pdm.moviesmanager.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.AdapterContextMenuInfo
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import br.edu.ifsp.scl.ads.pdm.moviesmanager.R
import br.edu.ifsp.scl.ads.pdm.moviesmanager.adapter.FilmeAdapter
import br.edu.ifsp.scl.ads.pdm.moviesmanager.controller.FilmeController
import br.edu.ifsp.scl.ads.pdm.moviesmanager.databinding.ActivityMainBinding
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.entity.Constant.FILME_EXTRA
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.entity.Constant.VIEW_FILME
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.entity.Filme

class MainActivity : AppCompatActivity() {

    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val listaDeFilmes: MutableList<Filme> by lazy {
        filmeController.recuperarFilmes()
    }

    private lateinit var filmeAdapter: FilmeAdapter

    private lateinit var carl: ActivityResultLauncher<Intent>

    private val filmeController: FilmeController by lazy {
        FilmeController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        filmeAdapter = FilmeAdapter(this, listaDeFilmes)
        amb.filmesLv.adapter = filmeAdapter

        carl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val filme = result.data?.getParcelableExtra<Filme>(FILME_EXTRA)

                filme?.let { _filme ->
                    val posicao = listaDeFilmes.indexOfFirst { it.id == _filme.id }
                    if (posicao != -1) {
                        listaDeFilmes[posicao] = _filme
                        filmeController.editarFilme(_filme)
                    } else {
                        _filme.id = filmeController.adicionarFilme(_filme)
                        listaDeFilmes.add(_filme)
                    }
                    filmeAdapter.notifyDataSetChanged()
                }
            }
        }

        registerForContextMenu(amb.filmesLv)

        amb.filmesLv.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, posicao, _ ->
                val filme = listaDeFilmes[posicao]
                val filmeIntent = Intent(this@MainActivity, FilmeActivity::class.java)
                filmeIntent.putExtra(FILME_EXTRA, filme)
                filmeIntent.putExtra(VIEW_FILME, true)
                startActivity(filmeIntent)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.addFilmeMi -> {
                carl.launch(Intent(this, FilmeActivity::class.java))
                true
            }

            R.id.ordenarPorNomeMi -> {
                listaDeFilmes.sortBy { it.nome }
                filmeAdapter.notifyDataSetChanged()
                true
            }

            R.id.ordenarPorNotaMi -> {
                listaDeFilmes.sortByDescending { it.nota }
                filmeAdapter.notifyDataSetChanged()
                true
            }

            else -> { false }
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val posicao = (item.menuInfo as AdapterContextMenuInfo).position
        return when(item.itemId) {
            R.id.excluirFilmeMi -> {
                filmeController.removerFilme(listaDeFilmes[posicao].id)
                listaDeFilmes.removeAt(posicao)
                filmeAdapter.notifyDataSetChanged()
                true
            }
            R.id.editarFilmetMi -> {
                val filme = listaDeFilmes[posicao]
                val filmeIntent = Intent(this, FilmeActivity::class.java)
                filmeIntent.putExtra(FILME_EXTRA, filme)
                filmeIntent.putExtra(VIEW_FILME, false)
                carl.launch(filmeIntent)
                true
            }
            else -> { false }
        }
    }

}