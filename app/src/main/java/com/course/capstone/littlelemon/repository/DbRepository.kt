package com.course.capstone.littlelemon.repository

import com.course.capstone.littlelemon.db.AppDatabase
import com.course.capstone.littlelemon.db.DbDao
import com.course.capstone.littlelemon.db.MealsDataEntity
import com.course.capstone.littlelemon.network.dto.Meal


class DbRepository(
    private val dbDao: DbDao
) {


    suspend fun getAllMeals() : List<MealsDataEntity>{
        return dbDao.getAllMeals()
    }

    suspend fun insertMeal(meal: MealsDataEntity){
        dbDao.insertMeal(meal)
    }


}