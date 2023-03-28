package com.mongodb.chess.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.util.AttributeSet
import android.view.View
import com.mongodb.chess.chessData.SquareData
import com.mongodb.chess.chessPieces.Pawn
import com.mongodb.chess.chessPieces.Queen

class SquareView(
    context: Context,
    attrs: AttributeSet?
) : View(context, attrs) {

    private var drawable: VectorDrawable? = null
    private lateinit var data: SquareData
    private val margin: Int = 5
    var boardColor: Int = 0

    fun initialize(color: Int, data: SquareData) {
        this.boardColor = color
        this.setBackgroundColor(color)
        this.data = data

        this.isClickable = true
        this.setOnClickListener {
            onSquareClick()
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        if (drawable != null) {
            drawable!!.setBounds(margin, margin, width-margin, width-margin)
            drawable!!.draw(canvas)
        }
    }

    fun setDrawable(drawable: Drawable) {
        this.drawable = drawable as VectorDrawable
        invalidate()
    }

    fun removeDrawable() {
        this.drawable = null
        this.setBackgroundColor(Color.BLUE)
        this.setBackgroundColor(boardColor)
    }

    private fun onSquareClick() {
        val b = this.background as ColorDrawable
        if (boardColor != b.color) {
            val selected = data.board.selected.selectedSquare
            if (selected != null) {
                data.setPiece(selected.content, true)
                selected.setPiece(null, true)
                data.board.selected.unselect()
                selected.view.refreshDrawableState()

                if (data.content is Pawn) {
                    val piece = data.content as Pawn
                    if (piece.firstMove) {piece.firstMove = false}
                    if (data.y == 0 || data.y == data.board.length-1) {
                        data.setPiece(Queen(piece.team, data.board,
                            if (piece.team == "BLACK") {data.board.drawables.BLACK_QUEEN} else {data.board.drawables.WHITE_QUEEN}), true)
                    }
                }
            }
            data.board.nextTurn(data.content?.team)
        } else if (data.content != null) {
            if (data.board.turn == data.content?.team) {
                data.board.selected.newSelected(data)
            }else {
                data.board.selected.unselect()
            }
        } else {
            data.board.selected.unselect()
        }
    }
}