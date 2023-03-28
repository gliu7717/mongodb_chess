package com.mongodb.chess.chessPieces

import android.graphics.drawable.Drawable
import com.mongodb.chess.chessData.Board
import com.mongodb.chess.chessData.SquareData

class Queen(
    override val team: String,
    override val board: Board,
    override val drawable: Drawable
) : ChessPiece {
    override lateinit var square: SquareData

    override val allowed: Array<Array<Int>> = arrayOf(
        arrayOf(1,0), arrayOf(0,1),
        arrayOf(-1,0), arrayOf(0,-1),
        arrayOf(1,1), arrayOf(1,-1),
        arrayOf(-1,1), arrayOf(-1,-1))

    override fun movement(): ChessPiece.Move {
        return linearMovement()
    }
}