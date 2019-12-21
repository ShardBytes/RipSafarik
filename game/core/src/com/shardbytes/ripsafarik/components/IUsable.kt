package com.shardbytes.ripsafarik.components

import com.shardbytes.ripsafarik.entity.Player

interface IUsable {

    val maxUses: Int
    var leftUses: Int

    var cooldown: Float

    fun use(player: Player)

}