package br.edu.ifsp.scl.ads.pdm.moviesmanager.model.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Filme(
    var id: Int,
    var nome: String,
    var anoLancamento: Int,
    var produtora: String,
    var tempoDeDuracao: Int,
    var assistido: Int,
    var nota: Int?,
    var genero: String
//    var genero: Genero
): Parcelable
