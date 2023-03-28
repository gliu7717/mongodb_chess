package com.mongodb.chess.chessPieces

import android.graphics.drawable.Drawable
import com.mongodb.chess.chessData.Board
import com.mongodb.chess.chessData.SquareData

interface ChessPiece {
    val team: String
    val board: Board
    val drawable: Drawable
    var square: SquareData
    val allowed: Array<Array<Int>>?


    data class Move(var move: MutableList<SquareData>, var take: MutableList<SquareData>)
    fun movement(): Move

    fun linearMovement(): Move {
        val move: MutableList<SquareData> = mutableListOf()
        val take: MutableList<SquareData> = mutableListOf()
        val x = square.x
        val y = square.y

        val boardData = board.getData()
        for (coord in allowed!!) {
            var i: Int = coord[0]
            var j: Int = coord[1]
            while (-1<x+i && x+i<board.length && -1<y+j && y+j<board.length) {
                val squareData = boardData[x+i][y+j]
                if (squareData.content == null) {
                    move.add(squareData)
                } else {
                    if (squareData.content!!.team != team) {
                        take.add(squareData)
                    }
                    break
                }
                i += coord[0]
                j += coord[1]
            }
        }
        return Move(move, take)
    }
}