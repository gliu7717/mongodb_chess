package com.mongodb.chess.chessPieces

import android.graphics.drawable.Drawable
import com.mongodb.chess.chessData.Board
import com.mongodb.chess.chessData.SquareData

class Pawn(
    override val team: String,
    override val board: Board,
    override val drawable: Drawable
) : ChessPiece {
    override lateinit var square: SquareData
    var firstMove: Boolean = true
    override val allowed: Array<Array<Int>>? = null

    override fun movement(): ChessPiece.Move {
        val move: MutableList<SquareData> = mutableListOf()
        val take: MutableList<SquareData> = mutableListOf()
        val x = square.x
        val y = square.y

        val boardData = board.getData()
        if (team == "BLACK") {
            if (boardData[x][y+1].content == null) {
                move.add(boardData[x][y+1])
                if (firstMove && boardData[x][y+2].content == null) {
                    move.add(boardData[x][y+2])
                    firstMove = false
                }
            }

            if (x > 0) {
                val squareData = boardData[x-1][y+1]
                if (squareData.content != null && squareData.content!!.team != team) {
                    take.add(squareData)
                }
            }

            if (x < board.length-1) {
                val squareData = boardData[x+1][y+1]
                if (squareData.content != null && squareData.content!!.team != team) {
                    take.add(squareData)
                }
            }
        } else {
            if (boardData[x][y-1].content == null) {
                move.add(boardData[x][y-1])
                if (firstMove && boardData[x][y-2].content == null) {
                    move.add(boardData[x][y-2])
                }
            }

            if (x > 0) {
                val squareData = boardData[x-1][y-1]
                if (squareData.content != null && squareData.content!!.team != team) {
                    take.add(squareData)
                }
            }

            if (x < board.length-1) {
                val squareData = boardData[x+1][y-1]
                if (squareData.content != null && squareData.content!!.team != team) {
                    take.add(squareData)
                }
            }
        }
        return ChessPiece.Move(move, take)
    }
}