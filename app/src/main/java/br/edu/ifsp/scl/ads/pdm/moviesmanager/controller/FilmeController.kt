package br.edu.ifsp.scl.ads.pdm.moviesmanager.controller

import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.dao.FilmeDao
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.database.FilmeDaoSqlite
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.entity.Filme
import br.edu.ifsp.scl.ads.pdm.moviesmanager.view.MainActivity

class FilmeController(mainActivity: MainActivity) {

    private val filmeDaoImpl: FilmeDao = FilmeDaoSqlite(mainActivity)

    fun adicionarFilme(filme: Filme) = filmeDaoImpl.criaFilme(filme)
    fun selecionarFilme(id: Int) = filmeDaoImpl.retornaFilme(id)
    fun editarFilme(filme: Filme) = filmeDaoImpl.atualizaFilme(filme)
    fun removerFilme(id: Int) = filmeDaoImpl.excluiFilme(id)
}