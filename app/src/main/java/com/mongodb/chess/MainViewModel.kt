package com.mongodb.chess

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import com.mongodb.chess.chessData.Board
import com.mongodb.chess.chessPieces.PieceDrawable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel (sharedPreference : SharedPreferences, bReplay:Boolean): ViewModel() {

    private val realmRepo = Repo()

    private val preference: SharedPreferences = sharedPreference
    private val isReplayMode = bReplay
    private lateinit var chessRecords : List<ChessRecord>

    val records: LiveData<List<ChessRecord>> = liveData {
        Log.i("gliutest", "Triggered LiveData")
        emitSource(realmRepo.getChessRecordsAsFlow().asLiveData(Dispatchers.Main))
    }

    fun getPreferenc(): SharedPreferences
    {
        return preference
    }
    //val users: MutableLiveData<List<User>> = MutableLiveData()


    fun getStepId(): Int
    {
        val defaultValue = 1
        val stepId = preference.getInt("ChessStep", defaultValue)
        with (preference.edit()) {
            putInt("ChessStep", stepId  + 1)
            apply()
        }
        return stepId

    }
     fun saveRecordInfo(chessRecord: ChessRecord) {
         if(isReplayMode)
             return
         chessRecord.step = getStepId()
        viewModelScope.launch {
            realmRepo.saveChessInfo(chessRecord)
        }
    }

     fun getChessRecords() {
        viewModelScope.launch {
            val list = realmRepo.getCheckRecordsAsList()
            withContext(Dispatchers.Main) {
                //  users.value = list
                chessRecords = list
            }
        }
    }

    fun getChessRecordList():List<ChessRecord>
    {
        while(!this::chessRecords.isInitialized)
        {
            Thread.sleep(1_000)
        }
        return chessRecords
    }

    fun getChessRecordFlow() {
        viewModelScope.launch {
            val commflow = realmRepo.getChessRecordsAsFlow()
            var chessRecord = commflow.first().get(0)
            Log.i("gliutest", chessRecord.piece.toString())

            withContext(Dispatchers.Main) {
                //  users.value = list
            }
        }
    }

    fun isReplay(): Boolean {
        return isReplayMode
    }
    fun watch(board: Board, pieceDrawables: PieceDrawable) {
        realmRepo.watchResult(board, pieceDrawables)
        if(realmRepo.records!=null)
            chessRecords = realmRepo.records!!
    }

}