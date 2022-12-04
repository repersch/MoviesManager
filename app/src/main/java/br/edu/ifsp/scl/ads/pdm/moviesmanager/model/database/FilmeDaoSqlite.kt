package br.edu.ifsp.scl.ads.pdm.moviesmanager.model.database

import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.dao.FilmeDao
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.dao.GeneroDao
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.entity.Filme
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.entity.Genero
import java.sql.SQLException


class FilmeDaoSqlite(context: Context): FilmeDao, GeneroDao {

    companion object Constantes {
        // arquivo onde os dados do banco serão armazenados
        private const val FILME_DATABASE_FILE = "filmes"

        // nome da tabela
        private const val FILME_TABLE = "filme"
        private const val GENERO_TABLE = "genero"

        // atributos
        private const val ID_COLUMN = "id"
        private const val NOME_COLUMN = "nome"
        private const val ANO_LANCAMENTO_COLUMN = "anoLancamento"
        private const val PRODUTORA_COLUMN = "produtora"
        private const val TEMPO_DE_DURACAO_COLUMN = "tempoDeDuracao"
        private const val ASSISTIDO_COLUMN = "assistido"
        private const val NOTA_COLUMN = "nota"
        private const val GENERO_COLUMN = "genero"

        // criação da tabela
        private const val CREATE_GENERO_TABLE_STATEMENT =
            "CREATE TABLE IF NOT EXISTS ${GENERO_TABLE} (" +
                    "${ID_COLUMN} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${NOME_COLUMN} TEXT NOT NULL UNIQUE);"

        private const val CREATE_FILME_TABLE_STATEMENT =
            "CREATE TABLE IF NOT EXISTS $FILME_TABLE (" +
                "$ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$NOME_COLUMN TEXT NOT NULL UNIQUE," +
                "$ANO_LANCAMENTO_COLUMN INTEGER NOT NULL," +
                "$PRODUTORA_COLUMN TEXT NOT NULL," +
                "$TEMPO_DE_DURACAO_COLUMN INTEGER NOT NULL," +
                "$ASSISTIDO_COLUMN INTEGER NOT NULL," +
                "$NOTA_COLUMN INTEGER," +
                "$GENERO_COLUMN TEXT NOT NULL," +
                "FOREIGN KEY($GENERO_COLUMN) REFERENCES $GENERO_TABLE($ID_COLUMN));"
    }

    // abre a conexão com o banco
    private val filmeSqliteDatabase: SQLiteDatabase
    init {
        filmeSqliteDatabase = context.openOrCreateDatabase(
            FILME_DATABASE_FILE,
            MODE_PRIVATE,
            null
        )
        try {
            filmeSqliteDatabase.execSQL(CREATE_GENERO_TABLE_STATEMENT)
            filmeSqliteDatabase.execSQL(CREATE_FILME_TABLE_STATEMENT)
        } catch(se: SQLException) {
            Log.e("MovieManager", se.toString())
        }
    }

    private fun Filme.toContentValues(): ContentValues {
        val cv = ContentValues()
        cv.put(NOME_COLUMN, this.nome)
        cv.put(ANO_LANCAMENTO_COLUMN, this.anoLancamento)
        cv.put(PRODUTORA_COLUMN, this.produtora)
        cv.put(TEMPO_DE_DURACAO_COLUMN, this.tempoDeDuracao)
        cv.put(ASSISTIDO_COLUMN, this.assistido)
        cv.put(NOTA_COLUMN, this.nota)
        cv.put(GENERO_COLUMN, this.idGenero)
        return cv
    }

    private fun Genero.toContentValues(): ContentValues {
        val cv = ContentValues()
        cv.put(NOME_COLUMN, this.nome)
        return cv
    }

    private fun Cursor.rowToFilme() = Filme (
        getInt(getColumnIndexOrThrow(ID_COLUMN)),
        getString(getColumnIndexOrThrow(NOME_COLUMN)),
        getInt(getColumnIndexOrThrow(ANO_LANCAMENTO_COLUMN)),
        getString(getColumnIndexOrThrow(PRODUTORA_COLUMN)),
        getInt(getColumnIndexOrThrow(TEMPO_DE_DURACAO_COLUMN)),
        getInt(getColumnIndexOrThrow(ASSISTIDO_COLUMN)),
        getInt(getColumnIndexOrThrow(NOTA_COLUMN)),
        getInt(getColumnIndexOrThrow(GENERO_COLUMN))
    )

    private fun Cursor.rowToGenero() = Genero (
        getInt(getColumnIndexOrThrow(ID_COLUMN)),
        getString(getColumnIndexOrThrow(NOME_COLUMN))
    )

    override fun criaFilme(filme: Filme): Int {
        return filmeSqliteDatabase.insert(
            FILME_TABLE,
            null,
            filme.toContentValues()).toInt()
    }

    override fun retornaFilme(id: Int): Filme? {
        val cursor = filmeSqliteDatabase.rawQuery(
            "SELECT * FROM $FILME_TABLE WHERE $ID_COLUMN = ?",
            arrayOf(id.toString())
        )

        val filme = if (cursor.moveToFirst()) {
            cursor.rowToFilme()
        } else {
            null
        }
        cursor.close()
        return filme
    }

    override fun retornaFilmes(): MutableList<Filme> {
        val filmeList = mutableListOf<Filme>()
        val cursor = filmeSqliteDatabase.rawQuery("SELECT * FROM $FILME_TABLE",null)
        while (cursor.moveToNext()) {
            filmeList.add(cursor.rowToFilme())
        }
        cursor.close()
        return filmeList
    }

    override fun atualizaFilme(filme: Filme): Int {
        return filmeSqliteDatabase.update(
            FILME_TABLE,
            filme.toContentValues(),
            "$ID_COLUMN = ?",
            arrayOf(filme.id.toString()))
    }

    override fun excluiFilme(id: Int): Int {
        return filmeSqliteDatabase.delete(
            FILME_TABLE,
            "$ID_COLUMN = ?",
            arrayOf(id.toString()))
    }

    override fun criaGenero(genero: Genero): Int {
        return filmeSqliteDatabase.insert(
            GENERO_TABLE,
            null,
            genero.toContentValues()).toInt()
    }

    override fun retornaGenero(id: Int): Genero? {
        val cursor = filmeSqliteDatabase.rawQuery(
            "SELECT * FROM ${GENERO_TABLE} WHERE ${ID_COLUMN} = ?",
            arrayOf(id.toString())
        )

        val genero = if (cursor.moveToFirst()) {
            cursor.rowToGenero()
        } else {
            null
        }
        cursor.close()
        return genero
    }

    override fun retornaGeneros(): MutableList<Genero> {
        val generoList = mutableListOf<Genero>()
        val cursor = filmeSqliteDatabase.rawQuery("SELECT * FROM ${GENERO_TABLE}",null)
        while (cursor.moveToNext()) {
            generoList.add(cursor.rowToGenero())
        }
        cursor.close()
        return generoList
    }

    override fun excluiGenero(id: Int): Int {
        return filmeSqliteDatabase.delete(
            GENERO_TABLE,
            "${ID_COLUMN} = ?",
            arrayOf(id.toString()))
    }
}