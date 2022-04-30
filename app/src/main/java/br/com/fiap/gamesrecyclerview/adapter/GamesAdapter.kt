package br.com.fiap.gamesrecyclerview.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.gamesrecyclerview.R
import br.com.fiap.gamesrecyclerview.model.Game
import br.com.fiap.gamesrecyclerview.repository.GameRepository
import br.com.fiap.gamesrecyclerview.ui.CadastroActivity
import br.com.fiap.gamesrecyclerview.utils.toBitmap
import br.com.fiap.gamesrecyclerview.utils.toReal

class GamesAdapter(var context: Context): RecyclerView.Adapter<GamesAdapter.ViewHolderGames>() {

    private var gamesList: List<Game> = arrayListOf()

    fun updateGamesList(lista: List<Game>) {

        this.gamesList = lista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderGames {

        val view = LayoutInflater
            .from(context).inflate(R.layout.games_layout_rv, parent, false)

        return ViewHolderGames(view)

    }

    override fun onBindViewHolder(holder: ViewHolderGames, position: Int) {

        var game = gamesList[position]

        holder.textViewEstudio.text = game.estudio
        holder.textViewTitulo.text = game.titulo
        holder.textViewValor.text = game.valorEstimado.toReal()
        holder.textViewFinalizado.text = if (game.finalizado) "Finalizado" else "Não Finalizado"

        if (game.foto != null) {
            holder.image.setImageBitmap(game.foto!!.toBitmap())
        }

        if (game.finalizado)
            holder.imageFinalizado.setImageResource(R.drawable.finished_24)
        else
            holder.imageFinalizado.setImageResource(R.drawable.not_finished_24)


        holder.buttonEdit.setOnClickListener {
            Toast.makeText(context, game.id.toString() , Toast.LENGTH_SHORT).show()
            val intent = Intent(context, CadastroActivity::class.java)
            intent.putExtra("game_id", game.id)
            context.startActivity(intent)
        }

        holder.buttonDelete.setOnClickListener {

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Exclusão de jogo!")
            builder.setMessage("Você deseja excluir o jogo " + game.titulo + "?")

            builder.setPositiveButton("Sim"){_, _ ->
                val gameRepository = GameRepository(context)
                gameRepository.excluirGame(game)
                Toast.makeText(context, "Jogo Excluido com sucesso!!", Toast.LENGTH_SHORT).show()

                val listaGames = gameRepository.listarTodosOsGames()

                updateGamesList(listaGames)

                }
            builder.setNegativeButton("Não"){_,_ ->
                Toast.makeText(context,"Exclusão cancelada.",Toast.LENGTH_SHORT).show()
            }

            builder.setNeutralButton("Cancelar"){_,_ ->
                Toast.makeText(context,"Ação cancelada.",Toast.LENGTH_SHORT).show()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

    }

    override fun getItemCount() = gamesList.size

    fun filterStatusGamesList(check1: Boolean, check2: Boolean, searchText: String){

        val gameRepository = GameRepository(context)

        if (check1) {
            this.gamesList = gameRepository.listarTodosOsGamesFinalizados(searchText)
        }

        if (check2){
            this.gamesList += gameRepository.listarTodosOsGamesNaoFinalizados(searchText)
        }

        updateGamesList(this.gamesList)
    }

    class ViewHolderGames(view: View): RecyclerView.ViewHolder(view) {

        var textViewTitulo = view.findViewById<TextView>(R.id.rv_text_view_titulo)
        var textViewEstudio = view.findViewById<TextView>(R.id.rv_text_view_estudio)
        var textViewValor = view.findViewById<TextView>(R.id.rv_text_view_valor_estimado)
        var textViewFinalizado = view.findViewById<TextView>(R.id.rv_text_view_finalizado)
        var imageFinalizado = view.findViewById<ImageView>(R.id.rv_image_view_finished)
        var image = view.findViewById<ImageView>(R.id.rv_image_view)
        var card = view.findViewById<CardView>(R.id.rv_card_games)
        var buttonEdit = view.findViewById<ImageButton>(R.id.rv_image_button_edit)
        var buttonDelete = view.findViewById<ImageButton>(R.id.rv_image_button_delete)

    }
}