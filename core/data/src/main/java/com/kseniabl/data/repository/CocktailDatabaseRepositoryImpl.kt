package com.kseniabl.data.repository

import com.kseniabl.data.local.dao.CocktailDao
import com.kseniabl.data.mapCocktail
import com.kseniabl.data.toDomain
import com.kseniabl.data.toEntity
import com.kseniabl.domain.models.Cocktail
import com.kseniabl.domain.repository.CocktailDatabaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

class CocktailDatabaseRepositoryImpl @Inject constructor(
    private val dao: CocktailDao
): CocktailDatabaseRepository {
    override fun getCocktails(): Flow<List<Cocktail>> =
        dao.loadCocktails().mapCocktail()

    override suspend fun getCocktailById(id: Int): Cocktail? {
        return dao.getCocktailById(id)?.toDomain()
    }

    override fun insertOrUpdate(cocktail: Cocktail) {
        CoroutineScope(Dispatchers.IO).launch {
            if (dao.getCocktailById(cocktail.id) == null) {
                dao.insertCocktail(cocktail.toEntity())
            }
            else {
                dao.updateCocktail(cocktail.toEntity())
            }
        }
    }

    override fun insertCocktail(cocktail: Cocktail) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.insertCocktail(cocktail.toEntity())
        }
    }

    override fun updateCocktail(cocktail: Cocktail) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.updateCocktail(cocktail.toEntity())
        }
    }

    override fun deleteCocktail(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteCocktailById(id)
        }
    }

}