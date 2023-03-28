package com.mongodb.chess

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mongodb.chess.R
import com.mongodb.chess.Repo
import kotlinx.coroutines.runBlocking

const val EXTRA_MESSAGE = "com.ktu.chess.MESSAGE"

class MainActivity : AppCompatActivity() {
    private val realmRepo = Repo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBlocking {
            realmRepo.doAppSignIn()
        }
        setContentView(R.layout.activity_main)
    }

     fun playChest(view: View) {
        val intent = Intent(this, ChessGame::class.java)
         intent.putExtra("isReplay", false)
        startActivity(intent)
    }

    fun replayChess(view: View) {
        val intent = Intent(this, ChessGame::class.java)
        intent.putExtra("isReplay", true)
        startActivity(intent)

    }
}