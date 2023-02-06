package com.course.capstone.littlelemon

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.navigation.compose.rememberNavController
import com.course.capstone.littlelemon.datastore.AppSerializer
import com.course.capstone.littlelemon.datastore.StoreValues
import com.course.capstone.littlelemon.navigation.Navigation
import com.course.capstone.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.*
import io.ktor.client.engine.android.*

class MainActivity : ComponentActivity() {


    val client = HttpClient(Android)

    val Context.dataStore by dataStore("store.json", AppSerializer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LittleLemonTheme {

//                val value = remember{
//                    mutableStateOf("")
//                }
//
//                App(value){
//                    value.value = it
//                    Log.d("TAG", "onCreate: value: ${value.value}, it: ${it}")
//                }

                App(dataStore,client)


            }
        }
    }
}

@Composable
fun App(dataStore: DataStore<StoreValues>, client: HttpClient) {

    val navController = rememberNavController()

    Navigation(navController = navController,dataStore, client)

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

