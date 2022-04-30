package br.com.fiap.gamesrecyclerview.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import br.com.fiap.gamesrecyclerview.R
import br.com.fiap.gamesrecyclerview.databinding.ActivityCadastroBinding
import br.com.fiap.gamesrecyclerview.model.Game
import br.com.fiap.gamesrecyclerview.repository.AppDatabase
import br.com.fiap.gamesrecyclerview.repository.GameRepository
import br.com.fiap.gamesrecyclerview.utils.toBitmap
import br.com.fiap.gamesrecyclerview.utils.toByteArray

class CadastroActivity : AppCompatActivity() {

    lateinit var binding: ActivityCadastroBinding
    var gameId = 0
    var imagemBitmap: Bitmap? = null

    var camera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        imagemBitmap = it.data!!.extras!!.get("data") as Bitmap
        binding.imgFoto.setImageBitmap(imagemBitmap)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recuperar o id que está na intent
        gameId = intent.getIntExtra("game_id", 0)

        if (gameId > 0) {
            carregarGame(gameId)
        }

        binding.buttonGaleria.setOnClickListener {
            abrirGaleria()
        }

        binding.buttonCamera.setOnClickListener {
            abrirCamera()
        }

    }

    private fun abrirCamera() {
        val intentCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        camera.launch(intentCamera)
    }

    private fun abrirGaleria() {
        val intentGaleria = Intent(Intent.ACTION_GET_CONTENT)
        intentGaleria.setType("image/*")
        startActivityForResult(intentGaleria, 954)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, imagem: Intent?) {
        super.onActivityResult(requestCode, resultCode, imagem)

        Toast.makeText(this, "Result: $resultCode - Req: $requestCode ", Toast.LENGTH_SHORT).show()

        if (imagem != null && requestCode == 954) {
            val inputStream = contentResolver.openInputStream(imagem.data!!)
            imagemBitmap = BitmapFactory.decodeStream(inputStream)
            binding.imgFoto.setImageBitmap(imagemBitmap)
        }

    }

    private fun carregarGame(gameId: Int) {

        // Se houver gameId, carregar no formulário
        val gameRepository = GameRepository(this)
        val game = gameRepository.buscarGamePeloId(gameId)

        binding.textViewTitulo.text = "Atualizar ${game.titulo}"

        binding.editTitulo.setText(game.titulo)
        binding.editEstudio.setText(game.estudio)
        binding.editValor.setText(game.valorEstimado.toString())
        binding.checkFinalizado.isChecked = game.finalizado

        if (game.foto != null) {
            imagemBitmap = game.foto!!.toBitmap()
            binding.imgFoto.setImageBitmap(imagemBitmap)

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_cadastro, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // Instanciar um objeto game
        val game = Game()

        // Populando o objeto com os dados do formulário
        game.apply {
            titulo = binding.editTitulo.text.toString()
            estudio = binding.editEstudio.text.toString()
            valorEstimado = binding.editValor.text.toString().toDouble()
            finalizado = binding.checkFinalizado.isChecked

            if (imagemBitmap != null) {
                foto = imagemBitmap!!.toByteArray()
            }

        }

        Log.i("xpto", game.toString())

//        game.titulo = binding.editTitulo.text.toString()
//        game.estudio = binding.editEstudio.text.toString()
//        game.valorEstimado = binding.editValor.text.toString().toDouble()
//        game.finalizado = binding.checkFinalizado.isChecked

        if (item.itemId == R.id.menu_salvar) {
            val gameRepository = GameRepository(this)

            if (gameId == 0) {
                gameRepository.salvarGame(game)
                Toast.makeText(this, "Jogo gravado com sucesso!!", Toast.LENGTH_SHORT).show()
                limpar()
            } else {
                game.id = gameId
                gameRepository.atualizarGame(game)
                Toast.makeText(this, "Jogo atualizado com sucesso!!", Toast.LENGTH_SHORT).show()
                finish()
            }

        }

        return true
    }

    private fun limpar() {
        binding.editValor.text.clear()
        binding.editEstudio.text.clear()
        binding.editTitulo.text.clear()
        binding.checkFinalizado.isChecked = false
        binding.editTitulo.requestFocus()
        binding.imgFoto.setImageDrawable(null)
    }


}