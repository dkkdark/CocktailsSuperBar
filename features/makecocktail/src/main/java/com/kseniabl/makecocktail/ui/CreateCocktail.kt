package com.kseniabl.makecocktail.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kseniabl.makecocktail.R
import com.kseniabl.theme.AppBlue
import com.kseniabl.theme.DidactGothic

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun CreateCocktailScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(id = R.drawable.placeholder),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(40.dp))
        CocktailTextField("Title", "Add title", 56.dp)
        Spacer(modifier = Modifier.height(24.dp))
        CocktailTextField("Description", "Optional field", 154.dp)
        Spacer(modifier = Modifier.height(24.dp))
        CocktailTextField("Recipe", "Optional field", 154.dp)
        Spacer(modifier = Modifier.height(24.dp))
        CocktailButton(text = "Save", textColor = Color.White, buttonColor = AppBlue) {
        }
        Spacer(modifier = Modifier.height(8.dp))
        CocktailButton(text = "Cancel", textColor = AppBlue, buttonColor = Color.White) {
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailTextField(
    label: String = "",
    hint: String = "",
    height: Dp
) {
    val text by remember { mutableStateOf("") }
    
    Column {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .alpha(0.8F),
            value = text,
            onValueChange = {},
            shape = RoundedCornerShape(24.dp),
            label = { Text(text = label) },
            textStyle = TextStyle(fontFamily = DidactGothic)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(modifier = Modifier.padding(start = 12.dp).alpha(0.6F),
            text = hint, fontFamily = DidactGothic, fontSize = 12.sp)
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
        border = if (textColor != Color.White) BorderStroke(1.dp, textColor) else BorderStroke(1.dp, buttonColor)
    ) {
        Text(text, fontFamily = DidactGothic, fontSize = 24.sp, color = textColor)
    }
}