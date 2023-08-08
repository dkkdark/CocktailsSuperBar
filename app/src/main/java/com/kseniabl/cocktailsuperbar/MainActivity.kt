package com.kseniabl.cocktailsuperbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kseniabl.theme.CocktailSuperBarTheme
import com.kseniabl.theme.DidactGothic

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CocktailSuperBarTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun EmptyCocktailsScreen(
    modifier: Modifier = Modifier
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
        Spacer(modifier = Modifier.height(Dp(24F)))
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
        LargeFloatingActionButton(
            onClick = { /*TODO*/ }
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCocktailsScreen(modifier: Modifier = Modifier) {

    Scaffold(
        bottomBar = { RoundedCornerBottomBar() }
    ) {
        MyCocktailsContent(Modifier.padding(it))
    }

    
}

@Composable
fun MyCocktailsContent(modifier: Modifier = Modifier) {
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
            items(5) {
                CocktailElement()
            }
        }
    }
}

@Composable
fun CocktailElement() {
    val matrix = ColorMatrix()
    matrix.setToSaturation(0.7F)

    Card(
        modifier = Modifier
            .size(160.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(38.dp),
    ) {
        Box {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.cocktil1),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.colorMatrix(matrix)
            )
            Text(text = "Cocktail name", fontSize = 18.sp,
                fontFamily = DidactGothic, modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp),
                color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoundedCornerBottomBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize(),
            shape = RoundedCornerShape(topStart = 42.dp, topEnd = 42.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        ) {
            FloatingActionButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { /*TODO*/ }
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    }
}

