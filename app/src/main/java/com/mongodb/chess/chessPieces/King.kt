package com.mongodb.chess.chessPieces

import android.graphics.drawable.Drawable
import com.mongodb.chess.chessData.Board
import com.mongodb.chess.chessData.SquareData

class King(
    override val team: String,
    override val board: Board,
    override val drawable: Drawable
) : ChessPiece {
    override lateinit var square: SquareData
    override val allowed: Array<Array<Int>>? = null

    override fun movement(): ChessPiece.Move {
        val move: MutableList<SquareData> = mutableListOf()
        val take: MutableList<SquareData> = mutableListOf()
        val x = square.x
        val y = square.y

        for (i in (x-1) until (x+2)) {
            for (j in (y-1) until (y+2)) {
                if (-1<i && i<board.length && -1<j && j<board.length) {
                    val squareData = board.getData()[i][j]
                    if (squareData.content == null) {
                        move.add(squareData)
                    } else if (squareData.content!!.team != this.team){
                        take.add(squareData)
                    }
                }
            }
        }
        return ChessPiece.Move(move, take)
    }
}