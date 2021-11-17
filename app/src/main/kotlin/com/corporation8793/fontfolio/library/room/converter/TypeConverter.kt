package com.corporation8793.fontfolio.library.room.converter

import android.app.Activity
import androidx.room.TypeConverter
import com.corporation8793.fontfolio.library.room.entity.Font
import com.corporation8793.fontfolio.library.room.entity.FontClassification
import com.corporation8793.fontfolio.library.room.entity.FontLicense
import com.corporation8793.fontfolio.library.room.entity.FontStyleInformation
import com.google.gson.Gson

/**
 * [RoomDatabase]의 [Entity]를 커스텀 타입으로 사용하기 위한 TypeConverter 클래스입니다.
 * @author  두동근
 * @see     [androidx.room.RoomDatabase]
 * @see     [androidx.room.Entity]
 * @see     TypeConverter
 * @see     Gson
 * @see     <a href="https://stackoverflow.com/questions/44582397/android-room-persistent-library-typeconverter-error-of-error-cannot-figure-ou">stackoverflow-questions</a>
 */
class TypeConverter {
    @TypeConverter
    fun fontClassificationToJson(value: FontClassification) = Gson().toJson(value)

    @TypeConverter
    fun fontLicenseobjectToJson(value: FontLicense) = Gson().toJson(value)

    @TypeConverter
    fun fontStyleInformationToJson(value: FontStyleInformation) = Gson().toJson(value)

    @TypeConverter
    fun stringToFontClassification(string: String?): FontClassification {
        return Gson().fromJson(string, FontClassification::class.java)
    }

    @TypeConverter
    fun stringToFontLicense(string: String?): FontLicense {
        return Gson().fromJson(string, FontLicense::class.java)
    }

    @TypeConverter
    fun stringToFontStyleInformation(string: String?): FontStyleInformation {
        return Gson().fromJson(string, FontStyleInformation::class.java)
    }
}