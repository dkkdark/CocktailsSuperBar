package com.kseniabl.mycocktails.presentation

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kseniabl.mycocktails.R
import com.kseniabl.mycocktails.entity.Cocktail
import com.kseniabl.theme.AppBlue
import com.kseniabl.theme.DidactGothic

@Composable
fun DetailScreen(cocktail: Cocktail?) {
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
            painter = painterResource(id = R.drawable.cocktil1),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
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
                        text = cocktail?.name ?: "-", fontSize = 36.sp,
                        fontFamily = DidactGothic
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = cocktail?.description ?: "No description",
                        fontSize = 16.sp,
                        fontFamily = DidactGothic,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    for(el in cocktail?.ingredients ?: emptyList()) {
                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            text = el, fontSize = 16.sp,
                            fontFamily = DidactGothic, textAlign = TextAlign.Center
                        )
                        if (cocktail?.ingredients?.indexOf(el) != cocktail?.ingredients?.size?.minus(1)) {
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
                        text = cocktail?.recipe ?: "-",
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
                // TODO: Edit
            }
        }
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
            1.dp,
            buttonColor
        )
    ) {
        Text(text, fontFamily = DidactGothic, fontSize = 24.sp, color = textColor)
    }
}