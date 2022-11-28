package br.edu.ifsp.scl.ads.pdm.moviesmanager.model.dao

import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.entity.Filme

interface FilmeDao {
    fun criaFilme(filme: Filme): Int
    fun retornaFilme(id: Int): Filme?
    fun retornaFilmes(): MutableList<Filme>
    fun atualizaFilme(filme: Filme): Int
    fun excluiFilme(id: Int): Int
}