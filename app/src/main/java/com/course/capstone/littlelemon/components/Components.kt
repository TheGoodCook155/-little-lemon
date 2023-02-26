package com.course.capstone.littlelemon.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.course.capstone.littlelemon.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomTextField(
    userInput: MutableState<String>,
    imeAction: ImeAction,
    keyboardType: KeyboardType,
    focusDirection: FocusDirection,
    modifier: Modifier,
    readOnly: Boolean,
    externalLabel: String,
    onValueChange: (String) -> Unit
){
    val localFocusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current


    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(bottom = 10.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top) {

        Text(text = externalLabel,
            fontSize = 11.sp,
            modifier = Modifier.padding(start = 20.dp, bottom = 10.dp)
        )

        OutlinedTextField(value = userInput.value, onValueChange = {
            onValueChange(it)
        },
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction,
                keyboardType = keyboardType
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    localFocusManager.moveFocus(focusDirection)
                },
                onDone = {
                    localFocusManager.moveFocus(focusDirection)
                    keyboard?.hide()
                }
            ), modifier = modifier,
            readOnly = readOnly,
            enabled = true,
            colors = TextFieldDefaults.textFieldColors(textColor = colorResource(id = R.color.black), backgroundColor = colorResource(
                id = R.color.white)),
            shape = RoundedCornerShape(5.dp))

    }




}



@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomSearchField(userInput: MutableState<String>,
                      imeAction: ImeAction,
                      keyboardType: KeyboardType,
                      focusDirection: FocusDirection,
                      modifier: Modifier,
                      readOnly: Boolean,
                      label: String,
                      onValueChange: (String) -> Unit) {


    val localFocusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current


        OutlinedTextField(value = userInput.value, onValueChange = {
            onValueChange(it)
        },

            keyboardOptions = KeyboardOptions(
                imeAction = imeAction,
                keyboardType = keyboardType
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    localFocusManager.moveFocus(focusDirection)
                },
                onDone = {
                    localFocusManager.moveFocus(focusDirection)
                    localFocusManager.clearFocus(true)
                    keyboard?.hide()
                }
            ), modifier = modifier,
            readOnly = readOnly,
            enabled = true,
            label = {
                Column(verticalArrangement = Arrangement.Center) {
                    Text(label,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        fontSize = 20.sp,
                    )
                }
            },
            shape = RoundedCornerShape(5.dp),
            leadingIcon = {

                Icon(modifier = Modifier.padding(start = 15.dp, end = 35.dp),
                    imageVector = Icons.Default.Search, contentDescription = "Search Icon")

            },
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.textFieldColors(textColor = colorResource(id = R.color.gray_dark), backgroundColor = colorResource(
                id = R.color.white
            ),
                focusedLabelColor = Color.LightGray,
                focusedIndicatorColor = Color.LightGray))


}


@Preview(showBackground = true)
@Composable
fun ComponentsPreview(){

    val str = remember{
        mutableStateOf("")
    }
//
//    CustomSearchField(
//        userInput = str,
//        imeAction = ImeAction.Done,
//        keyboardType = KeyboardType.Text,
//        focusDirection = FocusDirection.Down,
//        modifier = Modifier.padding(5.dp),
//        readOnly = false,
//        label = "Enter search phrase",
//        onValueChange = {
//            str.value = it
//        }
//    )

    CustomTextField(
        userInput = str,
        imeAction = ImeAction.Done,
        keyboardType = KeyboardType.Text,
        focusDirection = FocusDirection.Down,
        modifier = Modifier.height(53.dp),
        readOnly = false,
        externalLabel = "External Label",
        onValueChange = {
            str.value = it
        }
    )

}