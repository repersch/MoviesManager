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
import br.edu.ifsp.scl.ads.pdm.moviesmanager.controller.GeneroController
import br.edu.ifsp.scl.ads.pdm.moviesmanager.databinding.ActivityMainBinding
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.Constant.FILME_EXTRA
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.Constant.GENERO_EXTRA
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.Constant.LISTA_DE_GENEROS
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.Constant.VIEW_FILME
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.entity.Filme
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.entity.Genero

class MainActivity : AppCompatActivity() {

    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val generoController: GeneroController by lazy {
        GeneroController(this)
    }

    private val listaDeGeneros: MutableList<Genero> by lazy {
        generoController.recuperarFilmes()
    }

    private val listaDeFilmes: MutableList<Filme> by lazy {
        filmeController.recuperarFilmes()
    }

    private lateinit var filmeAdapter: FilmeAdapter

    private lateinit var filmeArl: ActivityResultLauncher<Intent>
    private lateinit var generoArl: ActivityResultLauncher<Intent>

    private val filmeController: FilmeController by lazy {
        FilmeController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        filmeAdapter = FilmeAdapter(this, listaDeFilmes)
        amb.filmesLv.adapter = filmeAdapter


        filmeArl = registerForActivityResult(
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

        generoArl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val genero = result.data?.getParcelableExtra<Genero>(GENERO_EXTRA)

                genero?.let { _genero ->
                    _genero.id = generoController.adicionarGenero(_genero)
                    listaDeGeneros.add(_genero)
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
                filmeIntent.putParcelableArrayListExtra(LISTA_DE_GENEROS, ArrayList(listaDeGeneros))
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
                val intent = Intent(this, FilmeActivity::class.java)
                intent.putParcelableArrayListExtra(LISTA_DE_GENEROS, ArrayList(listaDeGeneros))
                filmeArl.launch(intent)
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

            R.id.cadastrarGeneroMi -> {
                generoArl.launch(Intent(this, GeneroActivity::class.java))
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
                filmeIntent.putParcelableArrayListExtra(LISTA_DE_GENEROS, ArrayList(listaDeGeneros))
                filmeArl.launch(filmeIntent)
                true
            }
            else -> { false }
        }
    }

}