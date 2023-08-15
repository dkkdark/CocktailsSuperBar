package com.kseniabl.mycocktails.presentation.bottombar

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class BottomBarWithFabShape(private val fabSize: Float, private val fabPadding: Float): Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = drawBottomBar(size, fabSize, fabPadding)
        )
    }
}