package com.course.capstone.littlelemon.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.course.capstone.littlelemon.network.dto.Meal
import kotlinx.coroutines.flow.Flow

@Dao
public interface DbDao {

    @Query("SELECT * FROM meals")
    fun getAllMeals(): Flow<List<MealsDataEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: MealsDataEntity)



}