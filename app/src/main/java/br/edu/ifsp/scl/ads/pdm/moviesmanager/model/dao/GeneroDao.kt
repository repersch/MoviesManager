package br.edu.ifsp.scl.ads.pdm.moviesmanager.model.dao

import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.entity.Genero

interface GeneroDao {
    fun criaGenero(genero: Genero): Int
    fun retornaGenero(id: Int): Genero?
    fun retornaGeneros(): MutableList<Genero>
    fun excluiGenero(id: Int): Int
}