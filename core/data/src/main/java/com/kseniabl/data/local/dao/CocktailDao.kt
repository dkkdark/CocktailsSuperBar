package com.kseniabl.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kseniabl.data.local.entity.CocktailModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {
    @Query("SELECT * FROM CocktailModel")
    fun loadCocktails(): Flow<List<CocktailModel>>

    @Query("SELECT * FROM CocktailModel WHERE id = :id")
    suspend fun getCocktailById(id: Int): CocktailModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCocktail(item: CocktailModel)

    @Update
    suspend fun updateCocktail(item: CocktailModel)

    @Query("DELETE FROM CocktailModel WHERE id = :id")
    suspend fun deleteCocktailById(id: Int)
}