package com.course.capstone.littlelemon

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.course.capstone.littlelemon.components.CustomTextField
import com.course.capstone.littlelemon.datastore.StoreValues
import com.course.capstone.littlelemon.model.User
import com.course.capstone.littlelemon.navigation.HomeScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun OnboardingScreen(
    firstName: MutableState<String>,
    lastName: MutableState<String>,
    emailAddress: MutableState<String>,
    user: User,
    navController: NavController,
    dataStore: DataStore<StoreValues>,
    scope: CoroutineScope,
    isLoggedIn: (Boolean) -> Unit,
    userCallback: (User) -> Unit){

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val storeValues = dataStore.data.collectAsState(
        initial = StoreValues()
    ).value


    Column(modifier = Modifier
        .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {

        Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Little Lemon logo",
                modifier = Modifier
                    .width(250.dp)
                    .padding(top = 15.dp),
                contentScale = ContentScale.FillWidth)

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, bottom = 30.dp)
            .height(100.dp)
            .background(color = colorResource(id = R.color.green_Gray_Tint_Dark)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center


        ) {

            Text(text = "Let's get to know you", style = MaterialTheme.typography.h4,
                modifier = Modifier
                    .fillMaxWidth(),
                color = Color.White,
                textAlign = TextAlign.Center)
        }

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start) {
            Text(text = "Personal information", style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(start = 20.dp, top = 30.dp, bottom = 20.dp),
                textAlign = TextAlign.Start)
        }


        //first name
        CustomTextField(
            userInput = firstName,
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text,
            focusDirection = FocusDirection.Down,
            modifier = Modifier
                .fillMaxWidth()
                .height(53.dp)
                .padding(start = 20.dp, end = 20.dp),
            readOnly = false,
            externalLabel = "First Name",
            onValueChange = {
                firstName.value = it
            }
        )

        //last name
        CustomTextField(
            userInput = lastName,
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text,
            focusDirection = FocusDirection.Down,
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .height(53.dp),
            readOnly = false,
            externalLabel = "Last Name"

        ){
            Log.d("TAG", "Onboarding:  firstName.value: ${ firstName} it: ${it}")
            lastName.value = it
            user.lastName = it

        }

        CustomTextField(
            userInput = emailAddress,
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Email,
            focusDirection = FocusDirection.Down,
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .height(53.dp),
            readOnly = false,
            externalLabel = "Email"

        ){
            Log.d("TAG", "Onboarding:  firstName.value: ${ firstName} it: ${it}")
            emailAddress.value = it
            user.emailAddress = it

        }

        //button

        Row(modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center) {

            Button(onClick = {

                if (firstName.value.length <= 1 || lastName.value.length <= 1 || emailAddress.value.length <= 1){
                    Toast.makeText(context, "Registration unsuccessful. Please enter all data.",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(context, "Registration successful.",Toast.LENGTH_LONG).show()
                    userCallback(user)


                    scope.launch {

                        user.insertUser(dataStore = dataStore,user)

                    }

                    navController.navigate(HomeScreen.route)
                }
                Log.d("TAG", "OnboardingScreen: Button clicked")

            },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(bottom = 40.dp),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, colorResource(id =  R.color.orange)),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.yellow))) {

                Text(text = "Register")

            }
        }
    }
    Log.d("TAG", "Onboarding:  User: ${user.toString()}")
    if (storeValues.user.firstName == "" || storeValues.user.lastName == "" || storeValues.user.emailAddress == ""){
        isLoggedIn(false)
    }else{
        isLoggedIn(true)
    }

}



@Preview(showBackground = true)
@Composable
fun OnboardingPreview(){

}
