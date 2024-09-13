package com.example.noshapp

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.noshapp.model.Dish
import com.example.noshapp.ui.theme.Dimens
import com.example.noshapp.ui.theme.ScreenOrientation
import com.example.noshapp.ui.theme.dimens
import com.example.noshapp.uiData.LandingScreenViewmodel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

data class SidebarItem(
    val icon: ImageVector,
    val label: String
)
data class FoodSelectionItems(
    val icon: Painter,
    val text:String
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingPage(
    modifier: Modifier = Modifier,
    landingScreenViewmodel: LandingScreenViewmodel = viewModel()
) {
    val dimens = MaterialTheme.dimens
    var selectedItemIndex by remember { mutableIntStateOf(0) }
    val scaffoldState = rememberBottomSheetScaffoldState(
        SheetState(
            skipPartiallyExpanded = false,
            density = LocalDensity.current, initialValue = SheetValue.PartiallyExpanded,
            skipHiddenState = false
        )
    )
    val dishes by landingScreenViewmodel.dishes.observeAsState(emptyList())
    val selectedDish by landingScreenViewmodel.selectedDish.observeAsState()

    val selectedHour by landingScreenViewmodel.selectedHour.observeAsState(12)
    val selectedMinute by landingScreenViewmodel.selectedMinute.observeAsState(0)
    val selectedAmPm by landingScreenViewmodel.selectedAmPm.observeAsState("AM")
    val scope = rememberCoroutineScope()

    var selectedDishId by remember { mutableStateOf<String?>(null) }


    val sidebarItems = listOf(
        SidebarItem(Icons.Default.Search, "Cook"),
        SidebarItem(Icons.Default.FavoriteBorder, "Favorite"),
        SidebarItem(Icons.Default.Search, "Manual"),
        SidebarItem(icon = ImageVector.vectorResource(id = R.drawable.baseline_devices_24), "Device"),
        SidebarItem(Icons.Default.Person, "Preference"),
        SidebarItem(Icons.Default.Settings, "Setting")
    )

    val foodSelectionItems = listOf(
        FoodSelectionItems(
            painterResource(id = R.mipmap.indian_food_image),
            text = "Indian"
        ),
        FoodSelectionItems(
            painterResource(id = R.mipmap.indian_food_image),
            text = "Continental"
        ),
        FoodSelectionItems(
            painterResource(id = R.mipmap.indian_food_image),
            text = "Italian"
        ),
        FoodSelectionItems(
            painterResource(id = R.mipmap.indian_food_image),
            text = "South Indian"
        ),
        FoodSelectionItems(
            painterResource(id = R.mipmap.indian_food_image),
            text = "Chinese"
        ),
        FoodSelectionItems(
            painterResource(id = R.mipmap.indian_food_image),
            text = "North Indian"
        ),
        FoodSelectionItems(
            painterResource(id = R.mipmap.indian_food_image),
            text = "Non Veg"
        ),
        FoodSelectionItems(
            painterResource(id = R.mipmap.indian_food_image),
            text = "Vegetarian"
        ),
    )
    Box(modifier = Modifier.fillMaxSize()) {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetDragHandle = {},
            sheetContent = {
                BottomScreen(
                    selectedHour = selectedHour,
                    onHourSelected = { hour ->
                        landingScreenViewmodel.setTime(
                            hour,
                            selectedMinute,
                            selectedAmPm
                        )
                    },
                    selectedMinute = selectedMinute,
                    onMinuteSelected = { minute ->
                        landingScreenViewmodel.setTime(
                            selectedHour,
                            minute,
                            selectedAmPm
                        )
                    },
                    onAMSelected = {
                        landingScreenViewmodel.setTime(
                            selectedHour,
                            selectedMinute,
                            "AM"
                        )
                    },
                    onPMSelected = {
                        landingScreenViewmodel.setTime(
                            selectedHour,
                            selectedMinute,
                            "PM"
                        )
                    },

                    onCancel = {
                        scope.launch {
                            scaffoldState.bottomSheetState.hide()
                            selectedDishId = null
                        }
                    },
                    onReschedule = {
                        landingScreenViewmodel.getSelectedDishImageUrl()
                        scope.launch { scaffoldState.bottomSheetState.hide() }
                        selectedDishId = null
                    },
                    onCookNow = {
                        scope.launch { scaffoldState.bottomSheetState.hide() }
                        selectedDishId = null
                    },
                    selectedAmPm =selectedAmPm
                )
            },
            sheetPeekHeight = 0.dp,
            content = {
                if (ScreenOrientation==Configuration.ORIENTATION_PORTRAIT){
                    Spacer(modifier = Modifier.height(dimens.cardHeight))
                }
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.inversePrimary)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentWidth()
                    ) {
                        Sidebar(items = sidebarItems, selectedItemIndex = selectedItemIndex) {
                                index -> selectedItemIndex = index
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(dimens.medium1)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                        ) {
                            SearchBar(
                                modifier = Modifier
                                    .weight(1f)
                                    .align(Alignment.CenterVertically)
                                    .height(IntrinsicSize.Min)
                            )
                            Spacer(modifier = Modifier.width(dimens.small2))
                            DishScheduleTimeShower(
                                modifier = Modifier
                                    .weight(1f)
                                    .align(Alignment.CenterVertically),
                                dishName = landingScreenViewmodel.selectedDish.observeAsState().value?.dishName,
                                time = "Scheduled at ${landingScreenViewmodel.selectedHour.observeAsState().value}:" +
                                        "${landingScreenViewmodel.selectedMinute.observeAsState().value} " +
                                        "${landingScreenViewmodel.selectedAmPm.observeAsState().value}",
                                imageUrl = landingScreenViewmodel.getSelectedDishImageUrl()
                            )
                            Spacer(modifier = Modifier.width(dimens.small2))
                            Icon(
                                imageVector = Icons.Outlined.Notifications,
                                contentDescription = "Notifications",
                                modifier = Modifier
                                    .size(dimens.smallImageSize)
                                    .align(Alignment.CenterVertically)
                                    .clickable { }
                            )
                            Spacer(modifier = Modifier.width(dimens.medium4))
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_power_settings_new_24),
                                contentDescription = "Power",
                                tint = Color.Red,
                                modifier = Modifier
                                    .size(dimens.smallImageSize)
                                    .align(Alignment.CenterVertically)
                                    .clickable { }
                            )
                        }
                        if (ScreenOrientation==Configuration.ORIENTATION_PORTRAIT){
                            Spacer(modifier = Modifier.height(dimens.cardHeight))
                        }
                        Column(
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(top = dimens.medium1)
                        ) {
                            Text(
                                text = "What's on your mind?",
                                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                contentPadding = PaddingValues(vertical = dimens.small1),
                                horizontalArrangement = Arrangement.spacedBy(dimens.small3)
                            ) {
                                items(foodSelectionItems) { item ->
                                    FoodTypeSelectionView(
                                        foodSelectionItems = item,
                                        onItemSelected = {}
                                    )
                                }
                            }
                            if (ScreenOrientation==Configuration.ORIENTATION_PORTRAIT){
                                Spacer(modifier = Modifier.height(dimens.cardHeight))
                            }
                            Text(
                                text = "Recommendations",
                                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                contentPadding = PaddingValues(vertical = MaterialTheme.dimens.small1),
                                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small3)
                            ) {
                                items(dishes) { dish ->
                                    DishCardView(
                                        dish = dish,
                                        isSelected = dish.dishId == selectedDishId,
                                        onItemClicked = {
                                            selectedDishId=dish.dishId
                                            landingScreenViewmodel.selectDish(dish)
                                            scope.launch {
                                                scaffoldState.bottomSheetState.expand()
                                            }
                                        }
                                    )
                                }
                            }
                            if (ScreenOrientation==Configuration.ORIENTATION_PORTRAIT){
                                Spacer(modifier = Modifier.height(dimens.cardHeight))
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(dimens.cardImageSize),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                SuggestionCardViews(
                                    backgroundImage = painterResource(id = R.mipmap.rice_image),
                                    modifier = Modifier.weight(1f)
                                )
                                Spacer(modifier = Modifier.width(dimens.medium3))
                                SuggestionCardViews(
                                    modifier = Modifier.weight(1f) 
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}



@Composable
fun DishCardView(
    dish: Dish,
    onItemClicked: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val dimens = MaterialTheme.dimens
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
        label = "backgroundColor"
    )
    val contentColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else MaterialTheme.colorScheme.onBackground,
        label = "contentColour"
    )
    val iconColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else Color.Black, label = "iconColour"
    )
    val elevation by animateDpAsState(
        targetValue = if (isSelected) 6.dp else 4.dp, label = "elevation"
    )

    Box(modifier = Modifier.wrapContentSize()) {
        Card(
            shape = RoundedCornerShape(dimens.small3),
            modifier = modifier
                .wrapContentWidth()
                .clickable { onItemClicked() },
            colors = CardDefaults.cardColors(containerColor = backgroundColor),
            elevation = CardDefaults.cardElevation(elevation)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.wrapContentSize()
            ) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(dimens.small3)
                ) {
                    Box(modifier = Modifier
                        .clip(RoundedCornerShape(dimens.small3))
                        .width(dimens.large)
                        .height(dimens.large)
                    ) {
                        AsyncImage(
                            model = dish.imageUrl,
                            contentDescription = dish.dishName,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.matchParentSize()
                        )
                    }
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.non_veg_icon_new),
                        contentDescription = "Non-Veg Icon",
                        tint = Color.Red,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(dimens.small1)
                            .size(dimens.small3)
                    )
                    Card(
                        shape = RoundedCornerShape(dimens.small3),
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .wrapContentSize()
                            .offset(y = dimens.small1),
                        elevation = CardDefaults.cardElevation(2.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Star Icon",
                                tint = Color.White,
                                modifier = Modifier
                                    .padding(start = dimens.small1)
                                    .size(dimens.small2)
                            )
                            Spacer(modifier = Modifier.width(dimens.small1))
                            Text(
                                text = "4.2",
                                fontSize = 10.sp,
                                color = Color.White,
                                modifier = Modifier.padding(end = dimens.small1)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(dimens.small3))

                Text(
                    text = dish.dishName,
                    style = MaterialTheme.typography.headlineLarge,
                    color = contentColor,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(dimens.medium1))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = dimens.medium1, bottom = dimens.small1)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.cook_image1),
                        contentDescription = "Cook Image",
                        tint = iconColor,
                        modifier = Modifier.size(dimens.small2)
                    )
                    Spacer(modifier = Modifier.width(dimens.small1))
                    Text(
                        text = "30.min",
                        fontSize = 12.sp,
                        color = contentColor
                    )
                    Spacer(modifier = Modifier.width(dimens.small1))
                    Text(
                        text = "•",
                        fontSize = 12.sp,
                        color = contentColor
                    )
                    Spacer(modifier = Modifier.width(dimens.small1))
                    Text(
                        text = "Medium Prep",
                        fontSize = 12.sp,
                        color = contentColor
                    )
                    Spacer(modifier = Modifier.width(dimens.small1))
                }
            }
        }
    }
}



@Composable
fun FoodTypeSelectionView(
    foodSelectionItems: FoodSelectionItems,
    onItemSelected:(String)->Unit,
    appDimens: Dimens = MaterialTheme.dimens,
    modifier: Modifier=Modifier
){
    Card(onClick = {},
        elevation = CardDefaults.elevatedCardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier
            .wrapContentSize()
        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
            .wrapContentSize()) {
            Box(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .align(Alignment.CenterVertically)
                    .size(appDimens.smallImageSize)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondary)
            ) {
                Image(
                    painter = foodSelectionItems.icon,
                    contentDescription = "Dish Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )
            }
            Spacer(modifier = Modifier.width(appDimens.small1))
            Column(modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterVertically)
                .padding(appDimens.small1)) {
                Text(text = foodSelectionItems.text,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
            }
            Spacer(modifier = Modifier.width(appDimens.small1))
        }
    }
}



@Composable
fun DishScheduleTimeShower(
    modifier: Modifier = Modifier,
    dishName: String?,
    time: String?,
    imageUrl: String? = null,
    appDimens: Dimens = MaterialTheme.dimens
) {
    val displayDishName = dishName ?: "Italian Spaghetti"
    val displayTime = time ?: "Scheduled at 6:30, P.M."
    Card(
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(50.dp),
        modifier = modifier
            .wrapContentWidth()
            .height(appDimens.medium2 + 15.dp)
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .padding(horizontal = 8.dp) 
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(appDimens.smallImageSize)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondary)
            ) {
                imageUrl?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = "Dish Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } ?: Image(
                    painter = painterResource(id = R.mipmap.indian_food_image),
                    contentDescription = "Dish Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.width(appDimens.small1))
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(4.dp)
            ) {
                Text(
                    text = displayDishName,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = displayTime,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "Search...",
    onSearch: (String) -> Unit = {},
    cornerRadius: Int = 50,
    appDimens: Dimens = MaterialTheme.dimens
) {
    val searchText = remember { mutableStateOf(TextFieldValue("")) }

    OutlinedTextField(
        value = searchText.value,
        onValueChange = {
            searchText.value = it
            onSearch(it.text)
        },
        placeholder = {
            Text(text = hint,
                style = MaterialTheme.typography.bodyMedium)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        singleLine = true,
        shape = RoundedCornerShape(cornerRadius.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(appDimens.medium2 + 15.dp)
            .background(Color.White, RoundedCornerShape(cornerRadius.dp))
    )
}


@Preview(device = Devices.TABLET, showBackground = true, showSystemUi = true)
@Composable
fun LandingPagePreview() {
    LandingPage()
}

@Preview
@Composable
fun DishCardViewPreview(){
    val dish = Dish(
        "RICE",
        "1",
        "",
        true
    )
    DishCardView(onItemClicked = {  }, isSelected = true, dish = dish )

}

