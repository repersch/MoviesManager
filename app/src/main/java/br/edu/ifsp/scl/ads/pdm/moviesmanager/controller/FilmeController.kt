package br.edu.ifsp.scl.ads.pdm.moviesmanager.controller

import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.dao.FilmeDao
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.database.FilmeDaoSqlite
import br.edu.ifsp.scl.ads.pdm.moviesmanager.view.MainActivity

class FilmeController(mainActivity: MainActivity) {

    private val filmeDaoImpl: FilmeDao = FilmeDaoSqlite(mainActivity)
}