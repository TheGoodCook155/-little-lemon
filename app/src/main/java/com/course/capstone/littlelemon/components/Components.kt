package com.course.capstone.littlelemon.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomTextField(
    userInput: MutableState<String>,
    imeAction: ImeAction,
    keyboardType: KeyboardType,
    focusDirection: FocusDirection,
    modifier: Modifier,
//    label: String? = null,
    readOnly: Boolean,
    externalLabel: String,
    onValueChange: (String) -> Unit
){
    val localFocusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current


    Column(modifier = Modifier
        .fillMaxWidth()
        .height(70.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top) {

        Text(text = externalLabel,
            fontSize = 11.sp,
            modifier = Modifier.padding(start = 20.dp)
        )

        OutlinedTextField(value = userInput.value.toString(), onValueChange = {
//            userInput.value = it
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
//            label = {
//                Text(text = if (label != null) label.toString() else "")
//            },
            shape = RoundedCornerShape(5.dp))

    }




}