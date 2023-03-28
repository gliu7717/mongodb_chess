package com.mongodb.chess.chessData

import android.graphics.Color

class SelectedData {
    var selectedSquare: SquareData? = null
    var moveList: MutableList<SquareData>? = null
    var takeList: MutableList<SquareData>? = null

    fun newSelected(squareData: SquareData) {
        clearColoring()

        this.selectedSquare = squareData
        val (move, take) = squareData.content!!.movement()
        this.moveList = move
        this.takeList = take

        for (square in move) {
            square.view.setBackgroundColor(Color.BLUE)
        }
        for (square in take) {
            square.view.setBackgroundColor(Color.RED)
        }
    }
    fun unselect(){
        if (selectedSquare != null) {
            clearColoring()
            moveList = null
            takeList = null
            selectedSquare = null
        }
    }

    fun clearColoring() {
        if (moveList != null) {
            for (square in moveList!!) {
                square.view.setBackgroundColor(square.view.boardColor)
            }
        }
        if (takeList != null) {
            for (square in takeList!!) {
                square.view.setBackgroundColor(square.view.boardColor)
            }
        }
    }
}