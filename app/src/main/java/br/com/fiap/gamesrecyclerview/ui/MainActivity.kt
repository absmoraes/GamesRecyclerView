package br.com.fiap.gamesrecyclerview.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import br.com.fiap.gamesrecyclerview.R
import br.com.fiap.gamesrecyclerview.adapter.GamesAdapter
import br.com.fiap.gamesrecyclerview.repository.GameRepository
import br.com.fiap.gamesrecyclerview.utils.toReal
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    lateinit var rvGames: RecyclerView
    lateinit var adapterGames: GamesAdapter
    lateinit var buttonAddGame: FloatingActionButton
    lateinit var buttonTittleSearch: ImageButton
    lateinit var editSearchText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvGames = findViewById(R.id.rv_games)
        buttonAddGame = findViewById(R.id.button_add_game)
        buttonTittleSearch = findViewById(R.id.image_button_tittle_search)
        editSearchText =  findViewById<EditText>(R.id.editSearchView)

        buttonAddGame.setOnClickListener {
            val intentAbrirCadastro = Intent(this, CadastroActivity::class.java)
            startActivity(intentAbrirCadastro)
        }

        buttonTittleSearch.setOnClickListener {
            reloadScreen()
        }

        editSearchText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                reloadScreen()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })


    }

    override fun onResume() {
        super.onResume()

        reloadScreen()
    }

    fun reloadScreen() {

        var checkFinalizado =  findViewById<CheckBox>(R.id.checkBoxFinalizado)
        var checkNaoFinalizado =  findViewById<CheckBox>(R.id.checkBoxNaoFinalizado)
        var editSearchText =  findViewById<EditText>(R.id.editSearchView)

        adapterGames = GamesAdapter(this)
        adapterGames.filterStatusGamesList(checkFinalizado.isChecked, checkNaoFinalizado.isChecked,
            editSearchText.text.toString()
        )

        rvGames.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        rvGames.adapter = adapterGames

    }

    fun onCheckboxClicked(view: View) {
        reloadScreen()
        }

}