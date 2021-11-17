package com.corporation8793.fontfolio.library.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Font_Data.xls 의 2행(row) 단위 중 [Font.fontLicense]열(column)을 [Entity]로 사용하기 위한 데이터 클래스.
 * @author  두동근
 * @see     [androidx.room.Entity]
 * @see     [Font]
 */
@Entity
data class FontLicense(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    @ColumnInfo(name = "AL") val AL : Boolean = false,
    @ColumnInfo(name = "PF") val PF : Boolean = false,
    @ColumnInfo(name = "PPF") val PPF : Boolean = false,
    @ColumnInfo(name = "GNU_GPL") val GNU_GPL : Boolean = false,
    @ColumnInfo(name = "OFL") val OFL : Boolean = false,
    @ColumnInfo(name = "FPU") val FPU : Boolean = false,
    @ColumnInfo(name = "FCU") val FCU : Boolean = false,
    @ColumnInfo(name = "Free") val Free : Boolean = false,
    @ColumnInfo(name = "Unknown") val Unknown : Boolean = false
)