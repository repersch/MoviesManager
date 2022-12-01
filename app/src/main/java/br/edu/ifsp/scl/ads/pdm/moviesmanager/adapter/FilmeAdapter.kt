package br.edu.ifsp.scl.ads.pdm.moviesmanager.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.ifsp.scl.ads.pdm.moviesmanager.R
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.entity.Filme

class FilmeAdapter(
    context: Context,
    private val filmeList: MutableList<Filme>
): ArrayAdapter<Filme>(context, R.layout.tile_filme, filmeList) {

    private data class TileFilmeHolder(val nomeTv: TextView, val anoLancamentoTv: TextView)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val filme = filmeList[position]
        var filmeTileView = convertView
        if (filmeTileView ==  null) {
            filmeTileView =
                (context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                    R.layout.tile_filme,
                    parent,
                    false
                )

            val tileFilmeHolder = TileFilmeHolder(
                filmeTileView.findViewById(R.id.nomeTv),
                filmeTileView.findViewById(R.id.anoLancamentoTv)
            )
            filmeTileView.tag = tileFilmeHolder
        }

        with(filmeTileView?.tag as TileFilmeHolder) {
            nomeTv.text = filme.nome
            anoLancamentoTv.text = filme.anoLancamento.toString()
        }
        return filmeTileView
    }
}