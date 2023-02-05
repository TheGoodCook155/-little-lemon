package com.course.capstone.littlelemon.navigation.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.navigation.NavHostController
import com.course.capstone.littlelemon.R
import com.course.capstone.littlelemon.components.CustomTextField
import com.course.capstone.littlelemon.datastore.StoreValues
import com.course.capstone.littlelemon.navigation.OnboardingScreen
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(dataStore: DataStore<StoreValues>, navController: NavHostController){

    val scope = rememberCoroutineScope()
    val user = dataStore.data.collectAsState(
        initial = StoreValues()
    ).value

    val firstName = remember {
        mutableStateOf(user.user.firstName)
    }
    val lastName = remember {
        mutableStateOf(user.user.lastName)
    }
    val emailAddress = remember {
        mutableStateOf(user.user.emailAddress)
    }

    Log.d("TAG", "ProfileScreen: user ${firstName.value}, ${lastName.value}, ${emailAddress.value}")


    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Little Lemon logo",
            modifier = Modifier
                .width(250.dp)
                .padding(top = 15.dp),
            contentScale = ContentScale.FillWidth)

//        Row(modifier = Modifier
//            .fillMaxWidth()
//            .padding(top = 30.dp, bottom = 30.dp)
//            .height(100.dp)
//            .background(color = colorResource(id = R.color.green_Gray_Tint_Dark)),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center
//
//
//        ) {
//
//            Text(text = "Let's get to know you", style = MaterialTheme.typography.h4,
//                modifier = Modifier
//                    .fillMaxWidth(),
//                color = Color.White,
//                textAlign = TextAlign.Center)
//        }

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start) {
            Text(text = "Personal information", style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(start = 20.dp, top = 30.dp, bottom = 40.dp),
                textAlign = TextAlign.Start)
        }


        //first name
        CustomTextField(
            userInput = firstName,
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text,
            focusDirection = FocusDirection.Down,
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 5.dp, bottom = 30.dp)
                .fillMaxWidth(),
            readOnly = true,
            externalLabel = "First Name"
        ){}

        //last name
        CustomTextField(
            userInput = lastName,
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text,
            focusDirection = FocusDirection.Down,
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 5.dp, bottom = 30.dp)
                .fillMaxWidth(),
            readOnly = true,
            externalLabel = "Last Name"

        ){}

        CustomTextField(
            userInput = emailAddress,
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Email,
            focusDirection = FocusDirection.Down,
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 5.dp, bottom = 30.dp)
                .fillMaxWidth(),
            readOnly = true,
            externalLabel = "Email"

        ){}

        //button

        Row(modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center) {

            Button(onClick = {

                scope.launch {
                    user.user.removeUser(dataStore)
                }
                 navController.navigate(OnboardingScreen.route)

            },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(bottom = 40.dp),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, colorResource(id =  R.color.orange)),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.yellow))) {

                Text(text = "Log out")

            }
        }
    }



}



//@Preview(showBackground = true)
//@Composable
//fun ProfileScreenPreview(){
//
//    ProfileScreen()
//
//}