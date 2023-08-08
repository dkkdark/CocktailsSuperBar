package com.kseniabl.mycocktails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kseniabl.mycocktails.entity.Cocktail
import com.kseniabl.theme.AppBlue
import com.kseniabl.theme.DidactGothic

@Composable
fun EmptyCocktailsScreen(
    modifier: Modifier = Modifier,
    navigateToCreateCocktail: () -> Unit
) {
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
        Text(text = "My cocktails", fontSize = 36.sp,
            fontFamily = DidactGothic)
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Add your first cocktail here", fontSize = 16.sp,
            fontFamily = DidactGothic, modifier = Modifier
                .alpha(0.5F)
                .width(100.dp)
        )
        Spacer(modifier = Modifier.height(Dp(16F)))
        Image(painter = painterResource(id = R.drawable.arrow),
            contentDescription = "arrow", modifier = Modifier
                .size(48.dp)
                .alpha(0.3F))
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier
            .size(64.dp)
            .background(AppBlue, shape = CircleShape)
            .align(Alignment.CenterHorizontally)
            .clickable { navigateToCreateCocktail() },
            contentAlignment = Alignment.Center) {
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
        bottomBar = {
            if (isListEmpty == false) RoundedCornerBottomBar(navigateToCreateCocktail)
        }
    ) {
        if (isListEmpty == true)
            EmptyCocktailsScreen(Modifier.padding(it)) {
                navigateToCreateCocktail()
            }
        if (isListEmpty == false && !cocktails.value.isNullOrEmpty())
            MyCocktailsContent(Modifier.padding(it), cocktails.value!!, onItemClicked)
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
            .padding(top = 24.dp, bottom = 18.dp, start = 12.dp, end = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "My cocktails", fontSize = 36.sp,
            fontFamily = DidactGothic)
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = lazyGridState,
            horizontalArrangement = Arrangement.SpaceAround
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
            Text(text = cocktail.name,
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
fun RoundedCornerBottomBar(navigateToCreateCocktail: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize(),
            shape = RoundedCornerShape(topStart = 42.dp, topEnd = 42.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Box(modifier = Modifier
                .size(56.dp)
                .background(AppBlue, shape = CircleShape)
                .align(Alignment.CenterHorizontally)
                .clickable { navigateToCreateCocktail() },
                contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
            }
        }
    }
}