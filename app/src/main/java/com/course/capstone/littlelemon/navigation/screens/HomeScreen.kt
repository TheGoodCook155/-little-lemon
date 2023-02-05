package com.course.capstone.littlelemon.navigation.screens

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.course.capstone.littlelemon.navigation.ProfileScreen

@Composable
fun HomeScreen(navController: NavHostController) {


    Button(onClick = {

        navController.navigate(ProfileScreen.route)


    }) {

        Text(text = "Go to profile")

    }

}