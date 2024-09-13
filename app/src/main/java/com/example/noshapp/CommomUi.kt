package com.example.noshapp

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath
import com.example.noshapp.ui.theme.dimens


@Composable
fun WheelTimePicker(
    selectedHour: Int? = 6,
    onHourSelected: (Int) -> Unit,
    selectedMinute: Int? = 30,
    onMinuteSelected: (Int) -> Unit
) {
    val dimens = MaterialTheme.dimens
    val typography = MaterialTheme.typography

    Row(
        modifier = Modifier
            .height(dimens.large)
            .width(dimens.large)
            .background(MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(dimens.small1))
            .padding(dimens.small2),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (selectedHour != null) {
            WheelPicker(
                range = 1..12,
                selectedValue = selectedHour,
                onValueSelected = onHourSelected,
                modifier = Modifier.weight(1f)
            )
        }

        Text(
            text = ":",
            style = typography.titleLarge.copy(
                fontSize = dimens.medium1.value.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        if (selectedMinute != null) {
            WheelPicker(
                range = 0..59,
                selectedValue = selectedMinute,
                onValueSelected = onMinuteSelected,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun WheelPicker(
    range: IntRange,
    selectedValue: Int,
    onValueSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val dimens = MaterialTheme.dimens
    val typography = MaterialTheme.typography

    val visibleItems = 3
    val itemHeight = dimens.medium3
    val totalHeight = itemHeight * visibleItems
    val listState = rememberLazyListState()

    val initialIndex = range.indexOf(selectedValue)

    LaunchedEffect(initialIndex) {
        listState.scrollToItem(initialIndex)
    }
    LaunchedEffect(remember { derivedStateOf { listState.firstVisibleItemIndex } }) {
        val middleIndex = listState.firstVisibleItemIndex + visibleItems / 2
        if (middleIndex < range.count()) {
            val newValue = range.elementAt(middleIndex)
            if (newValue != selectedValue) {
                onValueSelected(newValue)
            }
        }
    }

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(
            vertical = itemHeight * ((visibleItems - 1) / 2)
        ),
        modifier = modifier
            .height(totalHeight)
            .width(dimens.small2)
            .clip(RoundedCornerShape(dimens.small1))
            .background(MaterialTheme.colorScheme.onPrimary)
    ) {
        itemsIndexed(range.toList()) { _, value ->
            val isSelected = value == selectedValue
            Text(
                text = String.format("%02d", value),
                style = typography.titleLarge.copy(
                    fontSize = if (isSelected) dimens.medium2.value.sp else dimens.medium1.value.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(itemHeight)
                    .wrapContentHeight(Alignment.CenterVertically)
                    .clickable {
                        onValueSelected(value)
                    },
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}



@Composable
fun TimePickerScreen() {
    var selectedHour by remember { mutableStateOf(12) }
    var selectedMinute by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        WheelTimePicker(
            selectedHour = selectedHour,
            onHourSelected = { selectedHour = it },
            selectedMinute = selectedMinute,
            onMinuteSelected = { selectedMinute = it }
        )
    }
}



@Composable
fun BottomScreen(
    selectedHour: Int?,
    onHourSelected: (Int) -> Unit,
    selectedMinute: Int?,
    onMinuteSelected: (Int) -> Unit,
    selectedAmPm: String?,
    onAMSelected: () -> Unit,
    onPMSelected: () -> Unit,
    onCancel: () -> Unit,
    onReschedule: () -> Unit,
    onCookNow: () -> Unit
) {
    val dimens = MaterialTheme.dimens
    val typography = MaterialTheme.typography

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(dimens.small1)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = dimens.small2, end = dimens.small2, top = dimens.small2)
                .wrapContentHeight(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Schedule Cooking Time",
                style = typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start
            )
            Card(
                shape = RoundedCornerShape(50),
                elevation = CardDefaults.cardElevation(dimens.extraSmall),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .padding(top = 1.dp)
                    .size(dimens.medium1)
                    .clickable(onClick = onCancel)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.cancel_icon),
                    contentDescription = "Cancel Icon",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Spacer(modifier = Modifier.width(dimens.small3))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = dimens.medium1, end = dimens.medium2, bottom = dimens.extraSmall),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            WheelTimePicker(
                selectedHour = selectedHour,
                onHourSelected = onHourSelected,
                selectedMinute = selectedMinute,
                onMinuteSelected = onMinuteSelected
            )
            Spacer(modifier = Modifier.width(dimens.medium2))
            Column {
                val isAMSelected = selectedAmPm == "AM"
                Button(
                    onClick = onAMSelected,
                    shape = RoundedCornerShape(dimens.small2),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isAMSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        text = "AM",
                        style = typography.bodyLarge,
                        color = if (isAMSelected) Color.White else MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.height(dimens.extraSmall))
                Button(
                    onClick = onPMSelected,
                    shape = RoundedCornerShape(dimens.small2),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!isAMSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        text = "PM",
                        style = typography.bodyLarge,
                        color = if (!isAMSelected) Color.White else MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
        Spacer(modifier = Modifier.padding(dimens.small2))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = onCancel) {
                Text(
                    text = "Delete",
                    color = Color.Red,
                    style = typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
                )
            }
            Card(
                shape = RoundedCornerShape(dimens.medium1),
                elevation = CardDefaults.cardElevation(dimens.extraSmall),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(width = 2.dp, Color(0xFFFF8C00)),
                modifier = Modifier
                    .weight(0.9f)
                    .padding(end = dimens.extraSmall)
            ) {
                Button(
                    onClick = onReschedule,
                    shape = RoundedCornerShape(dimens.extraSmall),
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text("Reschedule",
                        color = Color(0xFFFF8C00),
                        style = typography.bodySmall
                    )
                }
            }
            Spacer(modifier = Modifier.width(dimens.small3))
            Card(
                shape = RoundedCornerShape(dimens.medium1),
                elevation = CardDefaults.cardElevation(dimens.extraSmall),
                border = BorderStroke(width = 1.dp, color = Color(0xFFFF8C00)),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFF8C00)),
                modifier = Modifier.weight(1f)
            ) {
                Button(
                    onClick = onCookNow,
                    shape = RoundedCornerShape(dimens.extraSmall),
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8C00))
                ) {
                    Text("Cook Now", color = MaterialTheme.colorScheme.onSecondary, style = typography.bodyLarge)
                }
            }
        }
    }
}

@Composable
fun Sidebar(
    items: List<SidebarItem>,
    selectedItemIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    val dimens=MaterialTheme.dimens
    Box(modifier = Modifier) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxHeight()
                .width(dimens.medium4 + 15.dp)
                .background(Color.White)
                .padding(top = dimens.small1, bottom = dimens.small1)
        ) {
            items.forEachIndexed { index, item ->
                Row {
                    SidebarItem(
                        item = item,
                        isSelected = index == selectedItemIndex,
                        onClick = { onItemSelected(index) }
                    )
                }
            }
        }
    }
}

@Composable
fun SidebarItem(
    item: SidebarItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val dimens=MaterialTheme.dimens
    val typography=MaterialTheme.typography
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(vertical = dimens.small1)
            .clickable(onClick = onClick)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Column(modifier = Modifier.padding(start = dimens.small1)) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.label,
                    modifier = Modifier.size(dimens.medium2),
                    tint = if (isSelected) Color(0xFFFF8C00) else MaterialTheme.colorScheme.primary
                )
            }
            Column(modifier = Modifier.padding(start = dimens.small1)) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = item.label,
                style = typography.labelSmall,
                color = if (isSelected) Color(0xFFFF8C00) else MaterialTheme.colorScheme.primary
            )
        }
    }

        if (isSelected) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .offset(x = 30.dp)
                    .drawWithCache {
                        val roundedPolygon = RoundedPolygon(
                            numVertices = 3,
                            radius = size.minDimension / 2,
                            centerX = size.width / 2,
                            centerY = size.height / 2,
                            rounding = CornerRounding(
                                size.minDimension / 5f,
                                smoothing = 0.95f
                            )
                        )
                        val roundedPolygonPath = roundedPolygon
                            .toPath()
                            .asComposePath()
                        onDrawBehind {
                            drawPath(roundedPolygonPath, color = Color.White)
                        }
                    }
                    .size(60.dp)
            ){
                Icon(painter = painterResource(id = R.drawable.baseline_circle_24),
                    contentDescription = "Indicator Dot",
                    tint = Color(0xFFFF8C00),

                    modifier = Modifier
                        .size(10.dp)
                        .align(Alignment.Center))
            }
        }
    }
}


@Composable
fun SidebarScreen() {
    var selectedItemIndex by remember { mutableStateOf(0) }

    val sidebarItems = listOf(
        SidebarItem(Icons.Default.Search, "Cook"),
        SidebarItem(Icons.Default.FavoriteBorder, "Favorite"),
        SidebarItem(Icons.Default.Search, "Manual"),
        SidebarItem(Icons.Default.Search, "Device"),
        SidebarItem(Icons.Default.Person, "Preference"),
        SidebarItem(Icons.Default.Settings, "Setting")
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Blue)) {
        Sidebar(
            items = sidebarItems,
            selectedItemIndex = selectedItemIndex,
            onItemSelected = { index -> selectedItemIndex = index }
        )
    }
}


@Composable
fun SuggestionCardViews(
    backgroundImage: Painter? = null,
    modifier: Modifier = Modifier
) {
    val dimens = MaterialTheme.dimens
    val typography = MaterialTheme.typography

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFF8C00)
        ),
        modifier = modifier
            .wrapContentWidth()
            .height(dimens.cardImageSize)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            backgroundImage?.let {
                Image(
                    painter = it,
                    contentDescription = "Background Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(dimens.medium4)
                        .clip(CircleShape)
                )
            }
            Text(
                text = if (backgroundImage != null) "Explore All Dishes" else "Confused what to cook?",
                style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White,
                modifier = Modifier
                    .align(if (backgroundImage != null) Alignment.CenterStart else Alignment.Center)
                    .then(
                        if (backgroundImage != null) {
                            Modifier.padding(start = dimens.small3)
                        } else {
                            Modifier.padding(horizontal = dimens.small3)
                        }
                    )
            )
        }
    }
}


@Preview
@Composable
fun SuggestionCardViewsPreview2(){
    SuggestionCardViews()
}


@Preview(device = Devices.TABLET, showBackground = true, showSystemUi = true)
@Composable
fun SideBarScreenPreview() {
    SidebarScreen()
}

@Preview
@Composable
fun BottomScreenPreview() {
    BottomScreen(
        selectedHour = 6,
        onHourSelected = {it},
        selectedMinute =30 ,
        onMinuteSelected = {it},
        selectedAmPm = "AM",
        onAMSelected = {  },
        onPMSelected = {  },
        onCancel = { },
        onReschedule = { }) {

    }
}



@Preview
@Composable
fun TimePickerScreenPreview() {
    TimePickerScreen()
}

