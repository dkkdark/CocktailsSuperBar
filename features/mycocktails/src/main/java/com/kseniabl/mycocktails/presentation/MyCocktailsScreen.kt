package com.kseniabl.mycocktails.presentation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kseniabl.mycocktails.R
import com.kseniabl.mycocktails.entity.Cocktail
import com.kseniabl.mycocktails.presentation.bottombar.BottomBarWithFabShape
import com.kseniabl.theme.AppBlue
import com.kseniabl.theme.DidactGothic

@Composable
fun EmptyCocktailsScreen(
    modifier: Modifier = Modifier,
    navigateToCreateCocktail: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val sizeValue by infiniteTransition.animateFloat(
        initialValue = 0.dp.value,
        targetValue = 5.dp.value,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ), label = ""
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(280.dp),
            painter = painterResource(id = R.drawable.summer_holidays),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "My cocktails", fontSize = 36.sp,
            fontFamily = DidactGothic
        )
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = "Add your first cocktail here", fontSize = 16.sp,
            fontFamily = DidactGothic, modifier = Modifier
                .alpha(0.5F)
                .width(160.dp), textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.arrow),
            contentDescription = "arrow", modifier = Modifier
                .size(48.dp)
                .alpha(0.3F)
                .graphicsLayer(translationY = sizeValue)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(AppBlue, shape = CircleShape)
                .align(Alignment.CenterHorizontally)
                .clickable { navigateToCreateCocktail() },
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCocktailsScreen(
    navigateToCreateCocktail: () -> Unit,
    viewModel: MyCocktailViewModel = hiltViewModel(),
    onItemClicked: () -> Unit
) {
    val cocktails = viewModel.cocktails.collectAsState()
    val isListEmpty = cocktails.value?.isEmpty()


    Scaffold(
    ) {
        if (isListEmpty == true)
            EmptyCocktailsScreen(Modifier.padding(it)) {
                navigateToCreateCocktail()
            }
        Box {
            if (isListEmpty == false && !cocktails.value.isNullOrEmpty())
                MyCocktailsContent(Modifier.padding(it), cocktails.value!!, onItemClicked)

            if (isListEmpty == false) RoundedCornerBottomBar(navigateToCreateCocktail, this)
        }
    }

}

@Composable
fun MyCocktailsContent(
    modifier: Modifier = Modifier,
    cocktailList: List<Cocktail>,
    onItemClicked: () -> Unit
) {
    val lazyGridState = rememberLazyGridState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 24.dp, start = 12.dp, end = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "My cocktails", fontSize = 36.sp,
            fontFamily = DidactGothic
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = lazyGridState,
            horizontalArrangement = Arrangement.SpaceAround,
            contentPadding = PaddingValues(bottom = 68.dp)
        ) {
            items(cocktailList) {
                CocktailElement(it, onItemClicked)
            }
        }
    }
}

@Composable
fun CocktailElement(
    cocktail: Cocktail,
    onItemClicked: () -> Unit
) {
    val matrix = ColorMatrix()
    matrix.setToSaturation(0.7F)

    Card(
        modifier = Modifier
            .size(160.dp)
            .padding(4.dp)
            .clickable { onItemClicked() },
        shape = RoundedCornerShape(38.dp),
    ) {
        Box {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = cocktail.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.colorMatrix(matrix)
            )
            Text(
                text = cocktail.name,
                fontSize = 18.sp,
                fontFamily = DidactGothic,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun RoundedCornerBottomBar(navigateToCreateCocktail: () -> Unit, boxScope: BoxScope) {
    boxScope.apply {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(92.dp)
                .background(Color.Transparent)
                .align(Alignment.BottomCenter),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .align(Alignment.BottomCenter)
                    .graphicsLayer {
                        shadowElevation = 30.dp.toPx()
                        shape = BottomBarWithFabShape(56.dp.toPx(), 5.dp.toPx())
                        clip = true
                    }
                    .background(MaterialTheme.colorScheme.background)
            )
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(AppBlue, shape = CircleShape)
                    .align(Alignment.TopCenter)
                    .clickable { navigateToCreateCocktail() },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
            }
        }
    }
}