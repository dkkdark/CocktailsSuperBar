package com.kseniabl.domain.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kseniabl.domain.database.entity.CocktailModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {
    @Query("SELECT * FROM CocktailModel")
    fun loadCocktails(): Flow<List<CocktailModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCocktail(item: CocktailModel)

    @Update
    fun updateCocktail(item: CocktailModel)

    @Delete
    fun deleteCocktail(item: CocktailModel)
}