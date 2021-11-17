package com.corporation8793.fontfolio.library.room.entity

import android.app.Activity
import androidx.room.*
import com.corporation8793.fontfolio.library.room.converter.TypeConverter

/**
 * Font_Data.xls 의 1행(row) 단위에 대한 열(column)을 [Entity]로 사용하기 위한 데이터 클래스.
 * @author  두동근
 * @see     [androidx.room.Entity]
 */
@Entity(indices = [Index(
    value = ["fontName"],
    unique = true
)])
data class Font(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    @ColumnInfo(name = "fontName") val fontName : String,
    @TypeConverters(TypeConverter::class)
    @ColumnInfo(name = "fontClassification") val fontClassification : FontClassification = FontClassification(),
    @ColumnInfo(name = "fontStyle") val fontStyle : String,
    @TypeConverters(TypeConverter::class)
    @ColumnInfo(name = "fontStyleInformation") val fontStyleInformation : FontStyleInformation = FontStyleInformation(),
    @TypeConverters(TypeConverter::class)
    @ColumnInfo(name = "fontLicense") val fontLicense : FontLicense = FontLicense(),
    @ColumnInfo(name = "fontLicenseDescription") val fontLicenseDescription : String,
    @ColumnInfo(name = "fontCopyrightHolder") val fontCopyrightHolder : String,
    @ColumnInfo(name = "fontDownloadLink") val fontDownloadLink : String
)