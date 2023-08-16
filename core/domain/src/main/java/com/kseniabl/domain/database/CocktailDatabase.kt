package com.kseniabl.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kseniabl.domain.database.dao.CocktailDao
import com.kseniabl.domain.database.entity.CocktailModel

@Database(entities = [CocktailModel::class], version = 3)
@TypeConverters(TypeConverter::class)
abstract class CocktailDatabase : RoomDatabase() {

    abstract fun cocktailsDao(): CocktailDao

    companion object {
        const val DATABASE_NAME = "CocktailBarDatabase"
    }
}