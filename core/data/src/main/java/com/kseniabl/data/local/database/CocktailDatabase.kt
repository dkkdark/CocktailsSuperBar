package com.kseniabl.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kseniabl.data.local.TypeConverter
import com.kseniabl.data.local.dao.CocktailDao
import com.kseniabl.data.local.entity.CocktailModel

@Database(entities = [CocktailModel::class], version = 5)
@TypeConverters(TypeConverter::class)
abstract class CocktailDatabase : RoomDatabase() {

    abstract fun cocktailsDao(): CocktailDao

    companion object {
        const val DATABASE_NAME = "CocktailBarDatabase"
    }
}