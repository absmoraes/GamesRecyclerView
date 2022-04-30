package br.com.fiap.gamesrecyclerview.repository

import androidx.room.*
import br.com.fiap.gamesrecyclerview.model.Game

// SQLiteOpenHelper

@Dao
interface GameDAO {

    // Método para inserir um game
    @Insert
    fun inserir(game: Game) : Long

    // Método para excluir
    @Delete
    fun excluir(game: Game) : Int

    @Update
    fun atualizar(game: Game) : Int

    // Método para listar todos os games
    @Query("SELECT * FROM tbl_game ORDER BY titulo")
    fun listarTodosOsGames() : List<Game>

    // Listar todos os games finalizados
    @Query("SELECT * FROM tbl_game WHERE finalizado = 1 AND titulo LIKE :id")
    fun listarTodosOsGamesFinalizados(id: String) : List<Game>

    // Listar todos os games não finalizados
    @Query("SELECT * FROM tbl_game WHERE finalizado = 0 AND titulo LIKE :id")
    fun listarTodosOsGamesNaoFinalizados(id: String) : List<Game>

    // Exibir um jogo pelo código
    @Query("SELECT * FROM tbl_game WHERE game_id = :id")
    fun listarJogoPeloId(id: Int) : Game

    // Pesquisar por nome do jogo
    @Query("SELECT * FROM tbl_game WHERE titulo LIKE :id")
    fun listarGamesPorSemelhancaNome(id: String) : List<Game>

}