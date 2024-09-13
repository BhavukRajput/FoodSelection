package com.example.noshapp.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val extraSmall : Dp = 0.dp,
    val small1 : Dp = 0.dp,
    val small2 : Dp = 0.dp,
    val small3 : Dp = 0.dp,
    val medium1 : Dp = 0.dp,
    val medium2 : Dp = 0.dp,
    val medium3 : Dp = 0.dp,
    val medium4 : Dp = 0.dp,
    val large : Dp = 0.dp, // Added this line
    val cardHeight : Dp = 100.dp,
    val cardImageSize : Dp = 45.dp,
    val smallImageSize : Dp = 0.dp
)

val CompactSmallDimensions = Dimens(
    small1 = 6.dp,
    small2 = 5.dp,
    small3 = 8.dp,
    medium1 = 15.dp,
    medium2 = 26.dp,
    medium3 = 30.dp,
    medium4 = 40.dp,
    large = 100.dp,
    cardHeight = 100.dp,
    cardImageSize = 45.dp,
    smallImageSize = 25.dp
)

val CompactMediumDimensions = Dimens(
    small1 = 8.dp,
    small2 = 6.dp,
    small3 = 10.dp,
    medium1 = 18.dp,
    medium2 = 32.dp,
    medium3 = 40.dp,
    medium4 = 60.dp,
    large = 150.dp,
    cardHeight = 150.dp,
    cardImageSize = 48.dp,
    smallImageSize = 28.dp
)

val CompactDimensions = Dimens(
    small1 = 10.dp,
    small2 = 15.dp,
    small3 = 20.dp,
    medium1 = 30.dp,
    medium2 = 36.dp,
    medium3 = 40.dp,
    medium4 = 60.dp,
    large = 150.dp,
    cardHeight = 120.dp,
    cardImageSize = 50.dp,
    smallImageSize = 28.dp
)

val MediumDimensions = Dimens(
    small1 = 10.dp,
    small2 = 8.dp,
    small3 = 12.dp,
    medium1 = 20.dp,
    medium2 = 34.dp,
    medium3 = 45.dp,
    medium4 = 80.dp,
    large = 200.dp,
    cardHeight = 125.dp,
    cardImageSize = 55.dp,
    smallImageSize = 30.dp
)

val ExpandedDimensions = Dimens(
    small1 = 12.dp,
    small2 = 10.dp,
    small3 = 14.dp,
    medium1 = 22.dp,
    medium2 = 36.dp,
    medium3 = 55.dp,
    medium4 = 80.dp,
    large = 200.dp,
    cardHeight = 140.dp,
    cardImageSize = 60.dp,
    smallImageSize = 35.dp
)
