package com.course.capstone.littlelemon.navigation.screens

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
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
import com.course.capstone.littlelemon.components.CustomTextFieldNotEditable
import com.course.capstone.littlelemon.datastore.StoreValues
import com.course.capstone.littlelemon.model.User
import com.course.capstone.littlelemon.navigation.OnboardingScreen
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(dataStore: DataStore<StoreValues>, navController: NavHostController){

    val scope = rememberCoroutineScope()
    val user = dataStore.data.collectAsState(
        initial = StoreValues()
    ).value

    Log.d("retrievedUser", "ProfileScreen: ${user}: FirstName: ${user.user.firstName}, LastName: ${user.user.lastName}, Email: ${user.user.emailAddress}")

    val firstName = rememberSaveable {
        mutableStateOf("")
    }

    val lastName = rememberSaveable {
        mutableStateOf("")
    }


    val emailAddress = rememberSaveable{
        mutableStateOf("")
    }


    firstName.value = user.user.firstName
    lastName.value = user.user.lastName
    emailAddress.value = user.user.emailAddress


    Log.d("retrievedUser", "ProfileScreen: MutableStates: ${firstName.value}, LastName: ${lastName.value}, Email: ${emailAddress.value}")


    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Little Lemon logo",
            modifier = Modifier
                .width(250.dp)
                .padding(top = 15.dp),
            contentScale = ContentScale.FillWidth)


        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start) {
            Text(text = "Personal information", style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(start = 20.dp, top = 30.dp, bottom = 40.dp),
                textAlign = TextAlign.Start)
        }


        //first name
        CustomTextFieldNotEditable(
            userInput = firstName,
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text,
            focusDirection = FocusDirection.Down,
            modifier = Modifier
                .fillMaxWidth()
                .height(53.dp)
                .padding(start = 20.dp, end = 20.dp),
            externalLabel = "First Name",
        )

        //last name
        CustomTextFieldNotEditable(
            userInput = lastName,
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text,
            focusDirection = FocusDirection.Down,
            modifier = Modifier
                .fillMaxWidth()
                .height(53.dp)
                .padding(start = 20.dp, end = 20.dp),
            externalLabel = "Last Name",
        )

        CustomTextFieldNotEditable(
            userInput = emailAddress,
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text,
            focusDirection = FocusDirection.Down,
            modifier = Modifier
                .fillMaxWidth()
                .height(53.dp)
                .padding(start = 20.dp, end = 20.dp),
            externalLabel = "Email address",
        )

        //button

        Row(modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center) {

            Button(onClick = {

                scope.launch {
                    user.user.removeUser(dataStore)
                }
                 navController.navigate(OnboardingScreen.route){
                        popUpTo(OnboardingScreen.route)
                 }

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