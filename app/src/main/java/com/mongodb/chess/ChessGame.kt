package com.mongodb.chess

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.mongodb.chess.chessData.Board
import com.mongodb.chess.chessData.SquareData
import com.mongodb.chess.chessPieces.*
import kotlinx.coroutines.flow.first


class ChessGame : AppCompatActivity() {
    lateinit var board: Board
    lateinit var pieceDrawables: PieceDrawable
    lateinit var sharedPreference:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isReplay = intent.getBooleanExtra("isReplay",false)
        Log.i("chess", "onCreate: isReplay= " + isReplay.toString())
        sharedPreference =  getSharedPreferences("User_data", Context.MODE_PRIVATE)
        setContentView(R.layout.activity_chess_game)
        var  homeVM = MainViewModel(sharedPreference, isReplay)
        pieceDrawables = PieceDrawable(this)
        board = Board(this,8, homeVM)
        initClassicBoard(board)
        if(isReplay)
            replayChess(homeVM)

        // Create the observer which updates the UI.
        val nameObserver = Observer<List<ChessRecord>> { newName ->
            // Update the UI, in this case, a TextView.
            for( chessRecord in newName) {
                Log.i("gliutest", chessRecord.piece.toString())
            }
        }
        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        homeVM.records.observe(this, nameObserver)

    }

    private fun replayChess(vm:MainViewModel)
    {
        vm.watch(board, pieceDrawables)
        //vm.getChessRecordFlow()
       // var list = vm.getChessRecordList()
       // Log.i("chess", "replayChess: " + list.toString())
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initClassicBoard(board: Board) {
        val boardData: MutableList<MutableList<SquareData>> = board.getData()
        boardData[0][0].setPiece( Rook("BLACK", board, pieceDrawables.BLACK_ROOK))
        boardData[1][0].setPiece( Knight("BLACK", board, pieceDrawables.BLACK_KNIGHT))
        boardData[2][0].setPiece( Bishop("BLACK", board, pieceDrawables.BLACK_BISHOP))
        boardData[3][0].setPiece( Queen("BLACK", board, pieceDrawables.BLACK_QUEEN))
        boardData[4][0].setPiece( King("BLACK", board, pieceDrawables.BLACK_KING))
        boardData[5][0].setPiece( Bishop("BLACK", board, pieceDrawables.BLACK_BISHOP))
        boardData[6][0].setPiece( Knight("BLACK", board, pieceDrawables.BLACK_KNIGHT))
        boardData[7][0].setPiece( Rook("BLACK",board, pieceDrawables.BLACK_ROOK))

        for (i in 0 until 8) {
            boardData[i][1].setPiece( Pawn("BLACK",board, pieceDrawables.BLACK_PAWN))
        }

        boardData[0][7].setPiece( Rook("WHITE", board, pieceDrawables.WHITE_ROOK))
        boardData[1][7].setPiece( Knight("WHITE", board, pieceDrawables.WHITE_KNIGHT))
        boardData[2][7].setPiece( Bishop("WHITE", board, pieceDrawables.WHITE_BISHOP))
        boardData[3][7].setPiece( King("WHITE", board, pieceDrawables.WHITE_KING))
        boardData[4][7].setPiece( Queen("WHITE", board, pieceDrawables.WHITE_QUEEN))
        boardData[5][7].setPiece( Bishop("WHITE", board, pieceDrawables.WHITE_BISHOP))
        boardData[6][7].setPiece( Knight("WHITE", board, pieceDrawables.WHITE_KNIGHT))
        boardData[7][7].setPiece( Rook("WHITE",board, pieceDrawables.WHITE_ROOK))

        for (i in 0 until 8) {
            boardData[i][6].setPiece( Pawn("WHITE",board, pieceDrawables.WHITE_PAWN))
        }
    }

}