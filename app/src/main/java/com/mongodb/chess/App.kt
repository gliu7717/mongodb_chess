package com.mongodb.chess

import android.app.Application
import com.mongodb.chess.Repo
import io.realm.kotlin.Realm
import kotlinx.coroutines.runBlocking

class App : Application() {

    private val realmRepo = Repo()

    override fun onCreate() {
        super.onCreate()

        runBlocking {
            realmRepo.doAppSignIn()
        }
    }

}