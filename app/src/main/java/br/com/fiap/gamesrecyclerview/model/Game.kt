package br.com.fiap.gamesrecyclerview.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_game")
class Game {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "game_id")
    var id: Int = 0

    var titulo: String = ""
    var estudio: String = ""

    @ColumnInfo(name = "valor_estimado")
    var valorEstimado: Double = 0.0

    var finalizado: Boolean = true

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var foto: ByteArray? = null

}