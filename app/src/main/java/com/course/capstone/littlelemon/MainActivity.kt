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
import kotlinx.coroutines.*


class MainActivity : ComponentActivity() {
    
    val Context.dataStore by dataStore("store.json", AppSerializer)
    
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

//    OutlinedTextField(value = value.value, onValueChange = {
//        value.value = it
//        onValueChange(it)
//    },
//        keyboardOptions = KeyboardOptions(
//            imeAction = ImeAction.Next,
//            keyboardType = KeyboardType.Text
//        ),
//        keyboardActions = KeyboardActions(
//            onNext = {
//
//            },
//            onDone = {
//
//            }
//        ), modifier = Modifier.padding(5.dp),
//        label = {
////            Text(text = if (label != null) label.toString() else "")
//        },
//        shape = RoundedCornerShape(5.dp))

}

