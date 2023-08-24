package com.kseniabl.mycocktails.presentation

import android.net.Uri
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.kseniabl.domain.models.Cocktail
import com.kseniabl.mycocktails.R
import com.kseniabl.theme.AppBlue
import com.kseniabl.theme.DidactGothic
import java.io.File

@Composable
fun DetailScreen(
    cocktailId: Int?,
    viewModel: DetailViewModel = hiltViewModel(),
    navigateToEditScreen: (Int?) -> Unit,
    navigateToMyCocktails: () -> Unit
) {
    val cocktail by viewModel.cocktail.collectAsState()

    var showDeleteAlertDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(true) {
        viewModel.getCocktail(cocktailId)

        viewModel.cocktailState.collect {
            when(it) {
                is DetailViewModel.CocktailDetailsState.ShowDialog -> {
                    showDeleteAlertDialog = it.show
                }
                is DetailViewModel.CocktailDetailsState.Error -> {
                }
                is DetailViewModel.CocktailDetailsState.Loading -> {
                }
            }
        }
    }

    val context = LocalContext.current
    var imageUri: Uri? = null
    if (cocktail.image.isNotEmpty()) {
        val file = File(context.filesDir, cocktail.image)
        imageUri = Uri.fromFile(file)
    }

    var unrollInfo by remember { mutableStateOf(false) }
    val draggableState = rememberDraggableState {
        unrollInfo = it < 0
    }

    val transition = updateTransition(unrollInfo, label = "")
    val paddingSizeValue by transition.animateDp(
        transitionSpec = { tween(durationMillis = 500) },
        label = "",
    ) { screenState ->
        if (screenState) {
            10.dp
        } else {
            280.dp
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .draggable(
                orientation = Orientation.Vertical,
                state = draggableState
            )
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(330.dp)
                .align(Alignment.TopCenter),
            painter = rememberAsyncImagePainter(imageUri ?: R.drawable.cocktil1),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        DeleteMarker(viewModel)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingSizeValue)
                .align(Alignment.BottomCenter)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize(),
                shape = RoundedCornerShape(topStart = 42.dp, topEnd = 42.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background,
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = cocktail.name, fontSize = 36.sp,
                        fontFamily = DidactGothic
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = cocktail.description,
                        fontSize = 16.sp,
                        fontFamily = DidactGothic,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    for(el in cocktail.ingredients) {
                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            text = el, fontSize = 16.sp,
                            fontFamily = DidactGothic, textAlign = TextAlign.Center
                        )
                        if (cocktail.ingredients.indexOf(el) != cocktail.ingredients.size.minus(1)) {
                            Text(
                                modifier = Modifier.padding(horizontal = 8.dp),
                                text = "-", fontSize = 16.sp,
                                fontFamily = DidactGothic, textAlign = TextAlign.Center
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = "Recipe:", fontSize = 14.sp,
                        fontFamily = DidactGothic, textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = cocktail.recipe,
                        fontSize = 16.sp,
                        fontFamily = DidactGothic,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .wrapContentSize()
                .background(MaterialTheme.colorScheme.background)
                .align(Alignment.BottomCenter)
        ) {
            CocktailButton(text = "Edit", textColor = Color.White, buttonColor = AppBlue) {
                navigateToEditScreen(cocktailId)
            }
        }
    }

    if (showDeleteAlertDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteAlertDialog = false },
            confirmButton = {
                CocktailButton(text = "Yes", textColor = Color.White, buttonColor = AppBlue) {
                    viewModel.deleteCocktail(cocktail)
                    viewModel.showDialog(false)
                    navigateToMyCocktails()
                }
            },
            dismissButton = {
                CocktailButton(text = "Cancel", textColor = AppBlue, buttonColor = MaterialTheme.colorScheme.background) {
                    viewModel.showDialog(false)
                }
            },
            title = { Text("Do you want to delete this cocktail?") }
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
            .height(64.dp)
            .padding(8.dp),
        onClick = { onButtonClick() },
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        border = if (textColor != Color.White) BorderStroke(1.dp, textColor) else BorderStroke(
            0.dp,
            buttonColor
        )
    ) {
        Text(text, fontFamily = DidactGothic, fontSize = 24.sp, color = textColor)
    }
}

@Composable
fun BoxScope.DeleteMarker(
    viewModel: DetailViewModel
) {
    Card(modifier = Modifier
        .size(48.dp)
        .align(Alignment.TopEnd)
        .padding(8.dp)
        .clickable {
            viewModel.showDialog(true)
        },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        )
    ) {
        Icon(
            modifier = Modifier.fillMaxSize().padding(4.dp),
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = Color.Red
        )
    }
}