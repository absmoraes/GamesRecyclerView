package br.com.fiap.gamesrecyclerview.repository

import android.content.Context
import br.com.fiap.gamesrecyclerview.model.Game

class GameRepository(context: Context) {

    private val gameDB = AppDatabase.getDatabase(context).gameDao()

    fun salvarGame(game: Game): Long {
        return gameDB.inserir(game)
    }

    fun atualizarGame(game: Game): Int {
        return gameDB.atualizar(game)
    }

    fun excluirGame(game: Game): Int {
        return gameDB.excluir(game)
    }

    fun listarTodosOsGames(): List<Game> {
        return gameDB.listarTodosOsGames()
    }

    fun listarTodosOsGamesFinalizados(id: String): List<Game> {
        val searchParam = "%" + id + "%"
        return gameDB.listarTodosOsGamesFinalizados(searchParam)

    }

    fun listarTodosOsGamesNaoFinalizados(id: String): List<Game> {
        val searchParam = "%" + id + "%"
        return gameDB.listarTodosOsGamesNaoFinalizados(searchParam)
    }

    fun buscarGamePeloId(id: Int) : Game {
        return gameDB.listarJogoPeloId(id)
    }

    fun listarGamesPorSemelhancaNome(id: String): List<Game> {
        return gameDB.listarGamesPorSemelhancaNome(id)
    }

}