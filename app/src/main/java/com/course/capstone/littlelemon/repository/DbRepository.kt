package com.course.capstone.littlelemon.repository

import com.course.capstone.littlelemon.db.AppDatabase
import com.course.capstone.littlelemon.db.DbDao
import com.course.capstone.littlelemon.db.MealsDataEntity
import com.course.capstone.littlelemon.network.dto.Meal
import kotlinx.coroutines.flow.Flow


class DbRepository(
    private val dbDao: DbDao
) {


    suspend fun getAllMeals() : Flow<List<MealsDataEntity>> {
        return dbDao.getAllMeals()
    }

    suspend fun insertMeal(meal: MealsDataEntity){
        dbDao.insertMeal(meal)
    }


}