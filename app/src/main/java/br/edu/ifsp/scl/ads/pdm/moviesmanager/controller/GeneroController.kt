package br.edu.ifsp.scl.ads.pdm.moviesmanager.controller

import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.dao.GeneroDao
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.database.FilmesDaoSqlite
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.entity.Genero
import br.edu.ifsp.scl.ads.pdm.moviesmanager.view.MainActivity

class GeneroController(mainActivity: MainActivity) {

    private val generoDaoImpl: GeneroDao = FilmesDaoSqlite(mainActivity)

    fun adicionarGenero(genero: Genero) = generoDaoImpl.criaGenero(genero)
    fun selecionarFilme(id: Int) = generoDaoImpl.retornaGenero(id)
    fun recuperarFilmes() = generoDaoImpl.retornaGeneros()
    fun removerGenero(id: Int) = generoDaoImpl.excluiGenero(id)
}