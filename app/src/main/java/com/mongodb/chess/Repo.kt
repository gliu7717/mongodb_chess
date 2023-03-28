package com.mongodb.chess

import CommonFlow
import android.util.Log
import asCommonFlow
import com.mongodb.chess.chessData.Board
import com.mongodb.chess.chessPieces.PieceDrawable
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.AppConfiguration
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import io.realm.kotlin.notifications.InitialResults
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.notifications.UpdatedResults
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Repo {

    private val schemeClass = setOf(ChessRecord::class)
    var records: List<ChessRecord>? = null
    private val appDataService by lazy {
        val appConfiguration =
            AppConfiguration.Builder("application-1-ijefo").log(LogLevel.ALL).build()
        App.create(appConfiguration)
    }

    private val realm by lazy {
        val user = appDataService.currentUser!!

        val configuration =
            SyncConfiguration.Builder(user = user, schema = schemeClass).name("chess-db")
                .schemaVersion(1)
                .log(LogLevel.ALL)
                .initialSubscriptions { realm ->
                    add(realm.query<ChessRecord>(), name = "chessStream", updateExisting = true)
                }
                .build()

        Realm.open(configuration = configuration)
    }

    suspend fun doAppSignIn() {
        withContext(Dispatchers.Default) {
            appDataService.login(Credentials.anonymous())
            Log.i("chess", "Signed in as anonymous")
        }
    }

    /* private val realm by lazy {
         val configuration = RealmConfiguration.Builder(schema = schemeClass).name("local-db")
             .schemaVersion(1)
             .log(LogLevel.ALL)
             *//*.encryptionKey()*//*
            .build()
        Realm.open(configuration = configuration)
    }*/

    suspend fun saveChessInfo(chessRecord: ChessRecord) {
        realm.write {
            copyToRealm(chessRecord, updatePolicy = UpdatePolicy.ALL)
        }
        Log.i("chess", "Wrote in as anonymous")
    }

    fun getChessRecordsAsFlow(): CommonFlow<List<ChessRecord>> {
        return realm.query(clazz = ChessRecord::class).asFlow().map {
            when (it) {
                is InitialResults -> it.list
                is UpdatedResults -> it.list
            }
        }.asCommonFlow()
    }

    suspend fun getCheckRecordsAsList(): List<ChessRecord> {
        return withContext(Dispatchers.Default) {
            realm.query(clazz = ChessRecord::class).find().map {
                it
            }
        }
    }

    fun watchResult(board: Board, pieceDrawables: PieceDrawable) {
        // flow.collect() is blocking -- run it in a background context
        val job = CoroutineScope(Dispatchers.Default).launch {
            // create a Flow from the Item collection, then add a listener to the Flow
            val itemsFlow = getChessRecordsAsFlow()
            itemsFlow.collect { changes: List<ChessRecord> ->
                run {
                    if(records !=null ){
                        Log.i("gliutest", "old size:" + records!!.size.toString())
                    }
                    Log.i("gliutest", "new  size:" + changes.size.toString())
                    board.replay(records, changes, pieceDrawables)
                    records = changes
                }
            }
        }
    }
}