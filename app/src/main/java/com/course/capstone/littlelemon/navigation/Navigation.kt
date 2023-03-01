package com.course.capstone.littlelemon.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.datastore.core.DataStore


import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.course.capstone.littlelemon.OnboardingScreen
import com.course.capstone.littlelemon.datastore.StoreValues
import com.course.capstone.littlelemon.model.User
import com.course.capstone.littlelemon.navigation.screens.HomeScreen
import com.course.capstone.littlelemon.navigation.screens.ProfileScreen
import com.course.capstone.littlelemon.viewmodel.AppViewModel
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
    viewModel: AppViewModel,
){

    val isLoggedIn = rememberSaveable {
        mutableStateOf(false)
    }

    val firstName = rememberSaveable {
        mutableStateOf("")
    }

    val lastName = rememberSaveable {
        mutableStateOf("")
    }

    val emailAddress = rememberSaveable {
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

    val startScreen = if (isLoggedIn.value == false) OnboardingScreen.route else HomeScreen.route


    NavHost(navController = navController, startDestination = startScreen){

        composable(OnboardingScreen.route){

            Log.d("navigation", "Navigation: OnboardingScreen.route: entering Composable")

            OnboardingScreen(firstName,lastName,emailAddress,user, navController,dataStore,scope,{

                isLoggedIn.value = it

            }){
                Log.d("TAG", "App: IT callback: ${it.firstName}, ${it.lastName}, ${it.emailAddress}")
                firstName.value = it.firstName
                lastName.value = it.lastName
                emailAddress.value = it.emailAddress
            }
            Log.d("navigation", "Navigation: OnboardingScreen.route: exit Composable")


        }

        composable(HomeScreen.route){
            Log.d("navigation", "Navigation: HomeScreen.route: entering Composable")
            HomeScreen(navController,viewModel.mealsFromDb.collectAsState(), dataStore)
            Log.d("navigation", "Navigation: ProfileScreen.route: exit Composable")
        }

        composable(ProfileScreen.route){
            Log.d("navigation", "Navigation: ProfileScreen.route: entering Composable")
            ProfileScreen(dataStore = dataStore,navController)
            Log.d("navigation", "Navigation: ProfileScreen.route: exit Composable")
        }
    }



}


