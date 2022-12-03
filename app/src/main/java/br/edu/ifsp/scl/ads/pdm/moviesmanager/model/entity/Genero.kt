package br.edu.ifsp.scl.ads.pdm.moviesmanager.model.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genero(
    var id: Int,
    var nome: String
): Parcelable
