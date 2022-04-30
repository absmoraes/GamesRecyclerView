package br.com.fiap.gamesrecyclerview.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

//método para converter bytearray em bitmap
fun ByteArray.toBitmap() = BitmapFactory.decodeByteArray(this, 0, this.size)

//método para converter bitmap em bytearray
fun Bitmap.toByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 0, stream)
    return stream.toByteArray()
}

// Converte double para string com duas casas decimais
fun Double.toReal() = "R$ ${String.format("%.2f", this)}"


