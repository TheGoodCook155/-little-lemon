package com.course.capstone.littlelemon

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.navigation.compose.rememberNavController
import com.course.capstone.littlelemon.datastore.AppSerializer
import com.course.capstone.littlelemon.datastore.StoreValues
import com.course.capstone.littlelemon.navigation.Navigation
import com.course.capstone.littlelemon.ui.theme.LittleLemonTheme
import com.course.capstone.littlelemon.viewmodel.AppViewModel
import io.ktor.client.*
import io.ktor.http.*
import kotlinx.coroutines.*


class MainActivity : ComponentActivity() {

    companion object{
        val Context.dataStore by dataStore("store.json", AppSerializer)
    }


    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LittleLemonTheme {

                val viewModel by viewModels<AppViewModel>()

                Log.d("clientGet", "MainActivity | data: ${viewModel.getMeals()}")
                
                App(dataStore = dataStore, viewModel = viewModel)


            }
        }
    }
}




@Composable
fun App(dataStore: DataStore<StoreValues>, viewModel: AppViewModel) {

    val navController = rememberNavController()

    Navigation(navController = navController,dataStore, viewModel)

}

