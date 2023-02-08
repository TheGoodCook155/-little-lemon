package com.course.capstone.littlelemon.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.*
import androidx.datastore.core.DataStore


import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.course.capstone.littlelemon.OnboardingScreen
import com.course.capstone.littlelemon.datastore.StoreValues
import com.course.capstone.littlelemon.model.User
import com.course.capstone.littlelemon.navigation.screens.HomeScreen
import com.course.capstone.littlelemon.navigation.screens.ProfileScreen
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*


interface Navigation {

    val route: String

}



object OnboardingScreen: Navigation{

    override val route: String = "Onboarding_Screen"

}

object HomeScreen: Navigation{

    override val route: String = "Home_Screen"

}

object ProfileScreen: Navigation{

    override val route: String = "Profile_Screen"

}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Navigation(
    navController: NavHostController,
    dataStore: DataStore<StoreValues>,
    client: HttpClient,
){

    val isLoggedIn = remember {
        mutableStateOf(false)
    }

    val firstName = remember {
        mutableStateOf("")
    }

    val lastName = remember {
        mutableStateOf("")
    }

    val emailAddress = remember {
        mutableStateOf("")
    }


    val user by remember {
        mutableStateOf<User>(User("","",""))
    }


    val scope = rememberCoroutineScope()

    val storeValues = dataStore.data.collectAsState(
        initial = StoreValues()
    ).value


    Log.d("TAG", "App: states: firstName: ${firstName.value}, lastName: ${lastName.value}, emailAddress: ${emailAddress.value}")


    if (storeValues.user.firstName == "" || storeValues.user.lastName == "" || storeValues.user.emailAddress == ""){
            isLoggedIn.value = false
    }else{
        isLoggedIn.value = true
    }

    val startScreen = if (isLoggedIn.value == true) HomeScreen.route else OnboardingScreen.route



    NavHost(navController = navController, startDestination = startScreen){

        composable(OnboardingScreen.route){

            OnboardingScreen(firstName,lastName,emailAddress,user, navController,dataStore,scope,{

                isLoggedIn.value = it

            }){
                Log.d("TAG", "App: IT callback: ${it.firstName}, ${it.lastName}, ${it.emailAddress}")
                firstName.value = it.firstName
                lastName.value = it.lastName
                emailAddress.value = it.emailAddress
            }

        }

        composable(HomeScreen.route){
            HomeScreen(navController)
        }

        composable(ProfileScreen.route){
            ProfileScreen(dataStore = dataStore,navController)
        }
    }



}


