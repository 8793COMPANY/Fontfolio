package com.corporation8793.fontfolio.library.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Font_Data.xls 의 2행(row) 단위 중 [Font.fontClassification]열(column)을 [Entity]로 사용하기 위한 데이터 클래스.
 * @author  두동근
 * @see     [androidx.room.Entity]
 * @see     [Font]
 */
@Entity
data class FontClassification(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    @ColumnInfo(name = "Serif") val Serif : Boolean = false,
    @ColumnInfo(name = "Sans_Serif") val Sans_Serif : Boolean = false,
    @ColumnInfo(name = "Slab_Serif") val Slab_Serif : Boolean = false,
    @ColumnInfo(name = "Display") val Display : Boolean = false,
    @ColumnInfo(name = "Hand_Written") val Hand_Written : Boolean = false,
    @ColumnInfo(name = "Calligraphy") val Calligraphy : Boolean = false,
    @ColumnInfo(name = "Script") val Script : Boolean = false,
)
