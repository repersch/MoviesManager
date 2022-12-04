package br.edu.ifsp.scl.ads.pdm.moviesmanager.model.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class Filme(
    var id: Int,
    var nome: String,
    var anoLancamento: Int,
    var produtora: String,
    var tempoDeDuracao: Int,
    var assistido: Int = 0,
    var nota: Int?,
    var idGenero: Int
//    var genero: Genero
): Parcelable
