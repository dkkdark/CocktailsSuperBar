package com.kseniabl.makecocktail.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.kseniabl.makecocktail.R
import com.kseniabl.theme.AppBlue
import com.kseniabl.theme.DidactGothic
import java.util.Calendar

@Composable
fun CreateCocktailScreen(
    navigateToMyCocktails: () -> Unit,
    viewModel: CreateCocktailViewModel = hiltViewModel()
) {
    val scrollableState = rememberScrollState()

    var showDialog by remember { mutableStateOf(false) }
    val ingredients = remember { mutableStateListOf<String>() }

    var ingredientText by remember {
        mutableStateOf("")
    }

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var recipe by remember { mutableStateOf("") }

    var initLoadingScreen by remember { mutableStateOf(false) }
    var notCorrectDataShow by remember { mutableStateOf(false) }

    if (initLoadingScreen) LoadingScreen()

    LaunchedEffect(true) {
        viewModel.state.collect {
            when (it) {
                is CreateCocktailViewModel.SavingCocktailState.Loading -> {
                    notCorrectDataShow = false
                    initLoadingScreen = true
                }
                is CreateCocktailViewModel.SavingCocktailState.Success -> {
                    initLoadingScreen = false
                    notCorrectDataShow = false
                    navigateToMyCocktails()
                }
                is CreateCocktailViewModel.SavingCocktailState.Error -> {
                    initLoadingScreen = false
                    notCorrectDataShow = false
                    // TODO: show snackbar here
                }
                is CreateCocktailViewModel.SavingCocktailState.NotCorrectDate -> {
                    initLoadingScreen = false
                    notCorrectDataShow = true
                }
                null -> {}
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
            .verticalScroll(scrollableState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(id = R.drawable.placeholder),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(40.dp))
        CocktailTextField(title, "Title", "Add title", 56.dp, notCorrectDataShow) { title = it }
        Spacer(modifier = Modifier.height(24.dp))
        CocktailTextField(description, "Description", "Optional field", 154.dp) { description = it }
        Spacer(modifier = Modifier.height(24.dp))
        IngredientsList(items = ingredients) {
            ingredientText = ""
            showDialog = true
        }
        Spacer(modifier = Modifier.height(24.dp))
        CocktailTextField(recipe, "Recipe", "Optional field", 154.dp) { recipe = it }
        Spacer(modifier = Modifier.height(24.dp))
        CocktailButton(text = "Save", textColor = Color.White, buttonColor = AppBlue) {
            val time = Calendar.getInstance().time.time
            viewModel.checkFields(title, description, recipe, ingredients, time)
        }
        Spacer(modifier = Modifier.height(8.dp))
        CocktailButton(text = "Cancel", textColor = AppBlue, buttonColor = Color.White) {
            navigateToMyCocktails()
        }
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false }) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(top = 24.dp, bottom = 18.dp, start = 12.dp, end = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Add ingredient", fontSize = 36.sp,
                    fontFamily = DidactGothic
                )
                Spacer(modifier = Modifier.height(44.dp))
                CocktailTextField(
                    text = ingredientText,
                    label = "Ingredient",
                    hint = "Add title",
                    height = 56.dp
                ) {
                    ingredientText = it
                }
                Spacer(modifier = Modifier.height(16.dp))
                CocktailButton(text = "Add", textColor = Color.White, buttonColor = AppBlue) {
                    ingredients.add(ingredientText)
                    showDialog = false
                }
                Spacer(modifier = Modifier.height(8.dp))
                CocktailButton(text = "Cancel", textColor = AppBlue, buttonColor = Color.White) {
                    showDialog = false
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailTextField(
    text: String = "",
    label: String = "",
    hint: String = "",
    height: Dp,
    error: Boolean = false,
    onChange: (String) -> Unit
) {
    Column {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .alpha(0.8F),
            value = text,
            onValueChange = {
                onChange(it)
            },
            shape = RoundedCornerShape(24.dp),
            label = { Text(text = label) },
            textStyle = TextStyle(fontFamily = DidactGothic),
            isError = error
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(modifier = Modifier
            .padding(start = 12.dp)
            .alpha(0.6F),
            text = hint, fontFamily = DidactGothic, fontSize = 12.sp,
            color = if (error) Color.Red else MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun CocktailButton(
    text: String,
    textColor: Color,
    buttonColor: Color,
    onButtonClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        onClick = { onButtonClick() },
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        border = if (textColor != Color.White) BorderStroke(1.dp, textColor) else BorderStroke(
            1.dp,
            buttonColor
        )
    ) {
        Text(text, fontFamily = DidactGothic, fontSize = 24.sp, color = textColor)
    }
}

@Composable
fun LoadingScreen() {
    val infiniteTransition = rememberInfiniteTransition(label = "size of image")
    val sizeValue by infiniteTransition.animateFloat(
        initialValue = 48.dp.value,
        targetValue = 56.dp.value,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ), label = ""
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Transparent),
        contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier.size(sizeValue.dp),
            painter = painterResource(id = R.drawable.cocktail),
            contentDescription = null
        )
    }
}

@Composable
fun IngredientsList(items: List<String>, onClick: () -> Unit) {
    LazyRow(horizontalArrangement = Arrangement.Start) {
        items(items) {
            IngredientEl(it)
        }
        item {
            Box(modifier = Modifier
                .size(20.dp)
                .background(AppBlue, shape = CircleShape)
                .clickable { onClick() }) {
                Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
            }
        }
    }
}

@Composable
fun IngredientEl(
    el: String
) {
    Row(
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = 8.dp)
            .background(MaterialTheme.colorScheme.background)
            .border(1.dp, Color.Gray, shape = CircleShape)
    ) {
        Spacer(modifier = Modifier.width(6.dp))
        Text(el, fontFamily = DidactGothic, fontSize = 12.sp)
        Icon(
            modifier = Modifier.size(18.dp),
            imageVector = Icons.Default.Close, contentDescription = null, tint = AppBlue
        )
        Spacer(modifier = Modifier.width(6.dp))
    }
}
