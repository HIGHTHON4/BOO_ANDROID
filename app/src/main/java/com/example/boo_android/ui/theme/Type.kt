package com.example.boo_android.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.boo_android.R

val Pretendard = FontFamily(
    listOf(
        Font(
            resId = R.font.pretendard_regular,
            weight = FontWeight.Normal
        ),
        Font(
            resId = R.font.pretendard_medium,
            weight = FontWeight.Medium
        ),
        Font(
            resId = R.font.pretendard_semibold,
            weight = FontWeight.SemiBold
        ),
        Font(
            resId = R.font.pretendard_bold,
            weight = FontWeight.Bold
        )
    )
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

object PastPortFontStyle {
    private val defaultTextColor = Black

    private fun createTextStyle(weight: FontWeight, size: Int) = TextStyle(
        fontFamily = Pretendard,
        fontWeight = weight,
        fontSize = size.sp,
        color = defaultTextColor
    )

    val regular12 = createTextStyle(FontWeight.Normal, 12)
    val regular13 = createTextStyle(FontWeight.Normal, 13)
    val regular14 = createTextStyle(FontWeight.Normal, 14)
    val regular16 = createTextStyle(FontWeight.Normal, 16)

    val medium10 = createTextStyle(FontWeight.Medium, 10)
    val medium12 = createTextStyle(FontWeight.Medium, 12)
    val medium14 = createTextStyle(FontWeight.Medium, 14)
    val medium16 = createTextStyle(FontWeight.Medium, 16)
    val medium20 = createTextStyle(FontWeight.Medium, 20)

    val semi12 = createTextStyle(FontWeight.SemiBold, 12)
    val semi14 = createTextStyle(FontWeight.SemiBold, 14)
    val semi16 = createTextStyle(FontWeight.SemiBold, 16)
    val semi20 = createTextStyle(FontWeight.SemiBold, 20)

    val bold15 = createTextStyle(FontWeight.Bold, 15)
    val bold16 = createTextStyle(FontWeight.Bold, 16)
    val bold24 = createTextStyle(FontWeight.Bold, 24)
    val bold36 = createTextStyle(FontWeight.Bold, 36)
}