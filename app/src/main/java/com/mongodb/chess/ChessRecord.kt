package com.mongodb.chess

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PrimaryKey


class ChessRecord : RealmObject {

    @PrimaryKey
    var _id: RealmUUID = RealmUUID.random()
    var gameId: Int = 0
    var step: Int =0
    var team: Int = 0
    var positionX = 0
    var positionY= 0
    var piece: String? = null

}