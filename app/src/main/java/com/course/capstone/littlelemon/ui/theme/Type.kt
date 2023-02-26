package com.course.capstone.littlelemon.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.course.capstone.littlelemon.R

// Set of Material typography styles to start with

val KarlaRegular = FontFamily(
    Font(R.font.karla_regular)
)

val MarkaziRegular = FontFamily(
    Font(R.font.markaziyext_regular)
)


val Typography = Typography(
    body1 = TextStyle(
        fontFamily = KarlaRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),

    h1 = TextStyle(
        fontFamily = KarlaRegular,
        fontWeight = FontWeight.SemiBold,
        fontSize = 40.sp
    ),
    h2 = TextStyle(
        fontFamily = MarkaziRegular,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp
    ),
    h5 = TextStyle(
    fontFamily = KarlaRegular,
    fontWeight = FontWeight.SemiBold,
    fontSize = 12.sp
    ),
    h4 = TextStyle(
        fontFamily = KarlaRegular,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    )


    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

