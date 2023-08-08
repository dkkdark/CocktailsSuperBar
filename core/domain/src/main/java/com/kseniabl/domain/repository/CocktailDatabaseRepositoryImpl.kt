package com.kseniabl.domain.repository

import android.util.Log
import com.kseniabl.domain.database.dao.CocktailDao
import com.kseniabl.domain.database.entity.CocktailModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class CocktailDatabaseRepositoryImpl @Inject constructor(
    private val dao: CocktailDao
): CocktailDatabaseRepository {
    override fun getCocktails(): Flow<List<CocktailModel>> =
        dao.loadCocktails()


    override fun insertCocktail(cocktail: CocktailModel) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.insertCocktail(cocktail)
            Log.e("qqq", "fdf")
        }
    }

    override fun updateCocktail(cocktail: CocktailModel) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.updateCocktail(cocktail)
        }
    }

    override fun deleteCocktail(cocktail: CocktailModel) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteCocktail(cocktail)
        }
    }

}