package com.course.capstone.littlelemon.navigation.screens

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.course.capstone.littlelemon.R
import com.course.capstone.littlelemon.components.CustomSearchField
import com.course.capstone.littlelemon.datastore.StoreValues
import com.course.capstone.littlelemon.db.MealsDataEntity
import com.course.capstone.littlelemon.model.User
import com.course.capstone.littlelemon.navigation.HomeScreen
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(
    navController: NavHostController,
    meals: State<List<MealsDataEntity>>,
    dataStore: DataStore<StoreValues>
) {


    val searchString = rememberSaveable{
        mutableStateOf("")
    }

    val categoryCallBack = rememberSaveable {
        mutableStateOf("")
    }

    val isLoggedIn = rememberSaveable {
        mutableStateOf(false)
    }


    val user = dataStore.data.collectAsState(
        initial = StoreValues()
    ).value

    val scope = rememberCoroutineScope()



    if (user.user.firstName == "" || user.user.lastName == "" || user.user.emailAddress == ""){
        isLoggedIn.value = false
    }else{
        isLoggedIn.value = true
    }

    BackHandler {

        if (isLoggedIn.value == true){
            scope.launch {
                user.user.removeUser(dataStore)
            }
        }

    }


    Column(modifier = Modifier
        .fillMaxSize()) {



        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

            //Header section
            HomeHeader(navController = navController)

            //Hero section
            HeroSection(searchString){
                searchString.value = it
            }

            OrderDeliverySection(categoryCallBack){
                categoryCallBack.value = it
                Log.d("categoryCallBack.value", "HomeScreen: ${categoryCallBack.value}")
            }

        }




        ListMealsSection(meals = meals.value.filter { mealsDataEntity ->

            if (!searchString.value.isBlank()){

                mealsDataEntity.title.toLowerCase().contains(searchString.value.toLowerCase())

            } else {

                if (!categoryCallBack.value.isBlank()){

                    mealsDataEntity.category.toLowerCase().equals(categoryCallBack.value)

                }else{
                    mealsDataEntity.title.toLowerCase().contains(searchString.value.toLowerCase())
                }

            }

        })

    }

}

@Composable
fun ListMealsSection(meals: List<MealsDataEntity>) {

    LazyColumn(modifier = Modifier.fillMaxWidth()){

        items(meals){ meal ->

            CreateMealCard(meal)

        }

    }

}

@Composable
fun CreateMealCard(meal: MealsDataEntity) {

    Card(modifier = Modifier
        .padding(1.dp)
        .fillMaxWidth()
        .height(120.dp)) {


            Column(modifier = Modifier
                .wrapContentHeight(),
                verticalArrangement = Arrangement.Top) {

                Text(text = meal.title,
                    modifier = Modifier.padding(top = 15.dp, bottom = 5.dp, start = 15.dp, end = 15.dp),
                    style = TextStyle(Color.Black, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                )

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f)) {

                    Column(modifier = Modifier.weight(2f)) {

                        Text(text = meal.description,
                            modifier = Modifier
                                .padding(top = 5.dp, bottom = 10.dp, start = 15.dp, end = 15.dp)
                                .wrapContentHeight(),
                            style = TextStyle(Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Light),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2
                        )

                        Text(text = "$${meal.price}",
                            modifier = Modifier
                                .padding(bottom = 5.dp, start = 15.dp, end = 15.dp)
                                .wrapContentHeight(),
                            style = TextStyle(Color.Gray, fontSize = 15.sp, fontWeight = FontWeight.Medium),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )

                    }

                    val painter = rememberImagePainter(data = meal.image)
                    Image(
                        painter = painter,
                        contentDescription = "Meal Image",
                        modifier = Modifier
                            .width(90.dp)
                            .height(90.dp)
                            .padding(bottom = 10.dp, end = 10.dp),
                        contentScale = ContentScale.Crop
                    )


                }


                }

            }


}

@Composable
fun OrderDeliverySection(categoryCallback: MutableState<String>, callback: (String) -> Unit) {

    val startersColor = rememberSaveable {
        mutableStateOf(R.color.gray_light)
    }

    val mainsColor = rememberSaveable {
        mutableStateOf(R.color.gray_light)
    }

    val dessertsColor = rememberSaveable {
        mutableStateOf(R.color.gray_light)
    }

    val drinksColor = rememberSaveable {
        mutableStateOf(R.color.gray_light)
    }


    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 20.dp, top = 15.dp, bottom = 3.dp, end = 20.dp)) {

        Text(text = stringResource(id = R.string.order_for_delivery),
            modifier = Modifier.padding(start = 5.dp, bottom = 15.dp),
            style = TextStyle(color = Color.Black, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        )

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically) {

            //STARTERS
            Card(modifier = Modifier
                .padding(end = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable {

                    mainsColor.value = R.color.gray_light
                    dessertsColor.value = R.color.gray_light
                    drinksColor.value = R.color.gray_light

                    if (!categoryCallback.value.isBlank()) {
                        categoryCallback.value = ""

                        if (startersColor.value == R.color.yellow) {
                            startersColor.value = R.color.gray_light
                        }

                        callback("")
                        Log.d("callback", "OrderDeliverySection after callback: empty str returned")

                    } else {

                        callback("starters")

                        if (startersColor.value == R.color.gray_light) {
                            startersColor.value = R.color.yellow
                        }

                        Log.d("callback", "OrderDeliverySection after callback: starters returned")

                    }

                },
                backgroundColor = colorResource(id = startersColor.value)) {

                Text(text = "Starters",
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp),
                    color = colorResource(id = R.color.black))

            }

            //MAINS
            Card(modifier = Modifier
                .padding(end = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable {

                    startersColor.value = R.color.gray_light
                    dessertsColor.value = R.color.gray_light
                    drinksColor.value = R.color.gray_light

                    if (!categoryCallback.value.isBlank()) {
                        categoryCallback.value = ""

                        if (mainsColor.value == R.color.yellow) {
                            mainsColor.value = R.color.gray_light
                        }

                        callback("")
                        Log.d("callback", "OrderDeliverySection after callback: empty str returned")

                    } else {

                        callback("mains")

                        if (mainsColor.value == R.color.gray_light) {
                            mainsColor.value = R.color.yellow
                        }

                        Log.d("callback", "OrderDeliverySection after callback: starters returned")

                    }

                },
                backgroundColor = colorResource(id = mainsColor.value)) {

                Text(text = "Mains",
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp),
                    color = colorResource(id = R.color.black))

            }

            //Desserts
            Card(modifier = Modifier
                .padding(end = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable {

                    startersColor.value = R.color.gray_light
                    mainsColor.value = R.color.gray_light
                    drinksColor.value = R.color.gray_light

                    if (!categoryCallback.value.isBlank()) {
                        categoryCallback.value = ""

                        if (dessertsColor.value == R.color.yellow) {
                            dessertsColor.value = R.color.gray_light
                        }

                        callback("")
                        Log.d("callback", "OrderDeliverySection after callback: empty str returned")

                    } else {

                        callback("desserts")

                        if (dessertsColor.value == R.color.gray_light) {
                            dessertsColor.value = R.color.yellow
                        }

                        Log.d("callback", "OrderDeliverySection after callback: starters returned")

                    }

                },
                backgroundColor = colorResource(id = dessertsColor.value)) {

                Text(text = "Desserts",
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp),
                    color = colorResource(id = R.color.black))

            }

            //DRINKS
            Card(modifier = Modifier
                .padding(end = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable {

                    startersColor.value = R.color.gray_light
                    mainsColor.value = R.color.gray_light
                    dessertsColor.value = R.color.gray_light

                    if (!categoryCallback.value.isBlank()) {
                        categoryCallback.value = ""

                        if (drinksColor.value == R.color.yellow) {
                            drinksColor.value = R.color.gray_light
                        }

                        callback("")
                        Log.d("callback", "OrderDeliverySection after callback: empty str returned")

                    } else {

                        callback("drinks")

                        if (drinksColor.value == R.color.gray_light) {
                            drinksColor.value = R.color.yellow
                        }

                        Log.d("callback", "OrderDeliverySection after callback: starters returned")

                    }

                },
                backgroundColor = colorResource(id = drinksColor.value)) {

                Text(text = "Drinks",
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp),
                    color = colorResource(id = R.color.black))
            }

        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, top = 25.dp),
            color = Color.LightGray,
            thickness = 1.dp
        )

    }


}

@Composable
fun HomeHeader(navController: NavHostController){

        Row(modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start) {

            Column(modifier = Modifier
                .weight(3f)
                .padding(start = 15.dp, end = 5.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {

                Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Little Lemon logo",
                    modifier = Modifier
                        .width(200.dp)
                        .padding(top = 15.dp),
                    contentScale = ContentScale.FillWidth)
            }

            Column(modifier = Modifier
                .weight(1f)
                .padding(start = 15.dp)
                .wrapContentWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painter = painterResource(id = R.drawable.profile), contentDescription = "Profile Image",
                    modifier = Modifier
                        .clickable {
                            navController.navigate(com.course.capstone.littlelemon.navigation.ProfileScreen.route)
                        }
                        .width(50.dp)
                        .padding(top = 15.dp),
                    contentScale = ContentScale.FillWidth)
            }

        }

}

@Composable
fun HeroSection(
    searchString: MutableState<String>, searchStringCallBack: (String) -> Unit
){

    Column(modifier = Modifier
        .fillMaxWidth()
        .height(300.dp)
        .background(colorResource(id = R.color.green_Gray_Tint_Light)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start) {

        Text(modifier = Modifier.padding(top = 5.dp, start = 20.dp),
            text = stringResource(id = R.string.little_lemon),
            color = colorResource(id = R.color.yellow),
            style = MaterialTheme.typography.h2,
            fontSize = 50.sp)


        Row(modifier = Modifier.fillMaxWidth()) {

            Column(modifier = Modifier.weight(1.5f)) {

                Text(modifier = Modifier.padding(top = 5.dp, start = 20.dp),
                    text = stringResource(id = R.string.chicago),
                    color = colorResource(id = R.color.white),
                    style = MaterialTheme.typography.h2,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Medium)

                Text(modifier = Modifier.padding(20.dp),
                    text = stringResource(id = R.string.hero_text),
                    color = colorResource(id = R.color.white),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    softWrap = true)

            }

            Column(modifier = Modifier
                .weight(1f)
                .padding(end = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End) {
                Image(modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)
                    .padding(10.dp)
                    .clip(RoundedCornerShape(15.dp)),
                    painter = painterResource(id = R.drawable.hero_image), contentDescription = "hero image",
                    contentScale = ContentScale.Crop)
            }

        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            CustomSearchField(
                userInput = searchString,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                focusDirection = FocusDirection.Down,
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 5.dp)
                ,
                readOnly = false,
                label = "Enter search phrase",
                onValueChange = {
                    searchStringCallBack(it)
                }
            )

        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview(){

    val testStr = remember {
        mutableStateOf("")
    }

//    HomeScreen()
    HeroSection(
        testStr,
        {

        }
    )
//    OrderDeliverySection()

//    CreateMealCard(meal = MealsDataEntity(
//        1,
//        "Greek Salad",
//        "The famous greek salad of crispy lettuce, peppers, olives and our Chicago sandwitch this is some random text to check overflow drn drn",
//        "12.75",
//        "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/greekSalad.jpg?raw=true",
//        "starters"
//    ))

}
