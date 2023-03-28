package com.mongodb.chess.chessData

import android.graphics.Color
import android.widget.LinearLayout
import com.mongodb.chess.ChessGame
import com.mongodb.chess.ChessRecord
import com.mongodb.chess.MainViewModel
import com.mongodb.chess.R
import com.mongodb.chess.chessPieces.Pawn
import com.mongodb.chess.chessPieces.PieceDrawable
import com.mongodb.chess.chessPieces.Rook
import com.mongodb.chess.views.SquareView
import kotlinx.coroutines.runBlocking
import kotlin.math.roundToInt


class Board {
    private val boardData: MutableList<MutableList<SquareData>> = mutableListOf()
    private lateinit var boardLayout: LinearLayout
    lateinit var drawables: PieceDrawable
    var turn: String = "WHITE"
    var length: Int = 8
    var selected = SelectedData()
    lateinit var homeVM : MainViewModel

    constructor(game: ChessGame, l: Int, vm: MainViewModel) {
        boardLayout = game.findViewById(R.id.BoardLayout) as LinearLayout
        drawables = game.pieceDrawables
        length = l
        homeVM = vm

        var white = true
        //val width = boardLayout!!.width / l
        val width: Int = (1000f/l).roundToInt()
        val pair = (l == (l/2f).roundToInt()*2)
        val param = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        for (i in 0 until l) {
            val columnLayout = LinearLayout(game)
            columnLayout.orientation = LinearLayout.VERTICAL
            val rowData: MutableList<SquareData> = mutableListOf()
            for (j in 0 until l) {
                val square = SquareView(game, null)
                val data = SquareData(this, i, j, square)
                square.initialize(if (white) {Color.LTGRAY} else {Color.DKGRAY}, data)

                columnLayout.addView(square, width, width)
                rowData.add(data)

                white = !white
            }
            boardLayout.addView(columnLayout, param)
            boardData.add(rowData.toMutableList())

            rowData.clear()
            if (pair) {white = !white}
        }
    }

    fun getData(): MutableList<MutableList<SquareData>> {
        return boardData
    }

    fun nextTurn(team: String?) {
        if (team != null) {
            turn = if (team == "WHITE") {"BLACK"} else {"WHITE"}
        }

    }
    fun replay(oldRecords:List<ChessRecord>? , newRecords: List<ChessRecord>, pieceDrawables: PieceDrawable )
    {
        val boardData: MutableList<MutableList<SquareData>> = getData()

        if(oldRecords == null)
        {
            for(record in newRecords)
            {
                Thread.sleep(1000)
                boardData[record.positionX][record.positionY].setPiece( record, pieceDrawables)
            }
        }
        else{
            if(newRecords.size > oldRecords.size)
            {
                var record = newRecords.last()
                boardData[record.positionX][record.positionY].setPiece( record, pieceDrawables)
            }
        }
    }


}