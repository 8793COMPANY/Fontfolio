package com.corporation8793.fontfolio.library.room.entity.font

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
    @PrimaryKey(autoGenerate = true) var id : Int = 0,
    @ColumnInfo(name = "AL") var AL : Boolean = false,
    @ColumnInfo(name = "PF") var PF : Boolean = false,
    @ColumnInfo(name = "PPF") var PPF : Boolean = false,
    @ColumnInfo(name = "GNU_GPL") var GNU_GPL : Boolean = false,
    @ColumnInfo(name = "OFL") var OFL : Boolean = false,
    @ColumnInfo(name = "FPU") var FPU : Boolean = false,
    @ColumnInfo(name = "FCU") var FCU : Boolean = false,
    @ColumnInfo(name = "Free") var Free : Boolean = false,
    @ColumnInfo(name = "Unknown") var Unknown : Boolean = false
)