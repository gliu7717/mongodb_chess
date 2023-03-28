package com.mongodb.chess.chessData

import android.content.Context
import android.util.Log
import com.mongodb.chess.ChessRecord
import com.mongodb.chess.MainViewModel
import com.mongodb.chess.chessPieces.ChessPiece
import com.mongodb.chess.chessPieces.Pawn
import com.mongodb.chess.chessPieces.PieceDrawable
import com.mongodb.chess.views.SquareView

class SquareData(val board: Board, val x: Int, val y: Int, val view: SquareView) {
    var content: ChessPiece? = null

    fun setPiece(piece: ChessPiece?, saveInDb:Boolean = false) {
        content = piece
        if(saveInDb) {

            var pieceName=""
            if (piece != null) {
                pieceName= board.drawables.getPieceName(piece)
            }

            var chessRecord : ChessRecord = ChessRecord()
            chessRecord.gameId = 1
            if(piece?.team == "WHITE")
                chessRecord.team = 0
            else
                chessRecord.team = 1
            chessRecord.piece = pieceName
            chessRecord.positionX = x
            chessRecord.positionY = y

            board.homeVM.saveRecordInfo(chessRecord)

            Log.i(
                "Chess", "setPiece: " + x.toString() + ":" + y.toString()
                        + ", team:" + (piece?.team ?:"")  + ",piece:" + pieceName
            )
        }
        if (piece != null) {
            piece.square = this
            view.setDrawable(piece.drawable)
        } else {
            view.removeDrawable()
        }
    }

    fun setPiece(record: ChessRecord, pieceDrawables: PieceDrawable) {
        var team = "BLACK"
        if(record.team == 0 )
            team = "WHITE"
        var pieceDrawable = pieceDrawables.getDrawable(record.piece)
        if(pieceDrawable == null){
            setPiece( null )
            Log.i("gliutest", "Paint Blank")
        }
        else {
            setPiece(Pawn(team, board, pieceDrawable))
            Log.i("gliutest", "Paint Pawn x" +  x.toString() + " y " + y.toString())
        }
    }


}