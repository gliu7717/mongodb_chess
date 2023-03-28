package com.mongodb.chess.chessPieces

import android.graphics.drawable.Drawable
import com.mongodb.chess.chessData.Board
import com.mongodb.chess.chessData.SquareData

class Knight(
    override val team: String,
    override val board: Board,
    override val drawable: Drawable
) : ChessPiece {
    override lateinit var square: SquareData
    override val allowed: Array<Array<Int>> = arrayOf(
        arrayOf(1,2), arrayOf(2,1),
        arrayOf(-1,2), arrayOf(-1,-2),
        arrayOf(-2,1), arrayOf(-2,-1),
        arrayOf(1,-2), arrayOf(2,-1))

    override fun movement(): ChessPiece.Move {
        val move: MutableList<SquareData> = mutableListOf()
        val take: MutableList<SquareData> = mutableListOf()
        val x = square.x
        val y = square.y

        val boardData = board.getData()
        for (coord in allowed) {
            if (-1 < x+coord[0] && x+coord[0] < board.length && -1 < y+coord[1] && y+coord[1] < board.length) {
                val squareData = boardData[x+coord[0]][y+coord[1]]
                if (squareData.content == null) {
                    move.add(squareData)
                } else if (squareData.content!!.team != team) {
                    take.add(squareData)
                }
            }
        }
        return ChessPiece.Move(move, take)
    }
}