package com.corporation8793.fontfolio.library.room.entity.board

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Blob


@Entity
data class Board(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "font_name") val fontName: String?,
    @ColumnInfo(name = "board_name") val boardName: String?,
//    @ColumnInfo(name = "board_image") val boardImage: Bitmap?,
    @ColumnInfo(name = "secret_check") val secretCheck: Boolean?,
    @ColumnInfo(name = "memory_picture", typeAffinity = ColumnInfo.BLOB) val image :ByteArray?
)
