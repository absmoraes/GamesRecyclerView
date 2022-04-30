package br.com.fiap.gamesrecyclerview.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.fiap.gamesrecyclerview.model.Game

@Database(entities = [Game::class], version = 2)
abstract class AppDatabase() : RoomDatabase() {

    abstract fun gameDao(): GameDAO

    companion object {

        // Migração do banco de dados da versão 1 para 2
        val migration_1_2: Migration = object: Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {

                val sql = "ALTER TABLE tbl_game ADD COLUMN foto BLOB DEFAULT NULL"
                database.execSQL(sql)

            }

        }

        //Singleton - Instância única
        private lateinit var instance: AppDatabase

        fun getDatabase(context: Context): AppDatabase {

            if (!::instance.isInitialized){
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "gameDB")
                    .addMigrations(migration_1_2)
                    .allowMainThreadQueries()
                    .build()
            }

            return instance
        }

    }

}