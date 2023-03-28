package com.mongodb.chess.chessPieces

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import com.mongodb.chess.ChessGame
import com.mongodb.chess.R

@SuppressLint("UseCompatLoadingForDrawables")
class PieceDrawable(game: ChessGame) {
    val BLACK_BISHOP = game.resources.getDrawable(R.drawable.ic_black_bishop, game.theme)
    val BLACK_KING = game.resources.getDrawable(R.drawable.ic_black_king, game.theme)
    val BLACK_KNIGHT = game.resources.getDrawable(R.drawable.ic_black_knight, game.theme)
    val BLACK_PAWN = game.resources.getDrawable(R.drawable.ic_black_pawn, game.theme)
    val BLACK_QUEEN = game.resources.getDrawable(R.drawable.ic_black_queen, game.theme)
    val BLACK_ROOK = game.resources.getDrawable(R.drawable.ic_black_rook, game.theme)

    val WHITE_BISHOP = game.resources.getDrawable(R.drawable.ic_white_bishop, game.theme)
    val WHITE_KING = game.resources.getDrawable(R.drawable.ic_white_king, game.theme)
    val WHITE_KNIGHT = game.resources.getDrawable(R.drawable.ic_white_knight, game.theme)
    val WHITE_PAWN = game.resources.getDrawable(R.drawable.ic_white_pawn, game.theme)
    val WHITE_QUEEN = game.resources.getDrawable(R.drawable.ic_white_queen, game.theme)
    val WHITE_ROOK = game.resources.getDrawable(R.drawable.ic_white_rook, game.theme)

    fun getPieceName(chessPiece: ChessPiece):String
    {
        if(chessPiece.drawable == BLACK_BISHOP)
          return "BLACK_BISHOP"
        if(chessPiece.drawable == BLACK_KING)
            return "BLACK_KING"
        if(chessPiece.drawable == BLACK_KNIGHT)
            return "BLACK_KNIGHT"
        if(chessPiece.drawable == BLACK_PAWN)
            return "BLACK_PAWN"
        if(chessPiece.drawable == BLACK_QUEEN)
            return "BLACK_QUEEN"
        if(chessPiece.drawable == BLACK_ROOK)
            return "BLACK_ROOK"

        if(chessPiece.drawable == WHITE_BISHOP)
            return "WHITE_BISHOP"
        if(chessPiece.drawable == WHITE_KING)
            return "WHITE_KING"
        if(chessPiece.drawable == WHITE_KNIGHT)
            return "WHITE_KNIGHT"
        if(chessPiece.drawable == WHITE_PAWN)
            return "WHITE_PAWN"
        if(chessPiece.drawable == WHITE_QUEEN)
            return "WHITE_QUEEN"
        if(chessPiece.drawable == WHITE_ROOK)
            return "WHITE_ROOK"
        return ""
    }
    fun getDrawable(piece: String?):Drawable? {
        if (piece == "WHITE_PAWN")
            return WHITE_PAWN
        if (piece == "BLACK_PAWN")
            return BLACK_PAWN
        return null
    }
}