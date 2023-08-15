package com.kseniabl.mycocktails.presentation.bottombar

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path

fun drawBottomBar(size: Size, fabSize: Float, fabPadding: Float): Path {
    val height = size.height
    val width = size.width
    return Path().apply {
        reset()
        arcTo(
            Rect(0f, 0f, height*2, height*2),
            180f, 90f, true
        )
        lineTo((width - fabSize - fabPadding * 2) / 2, 0f)
        arcTo(
            Rect(
                (width - fabSize - fabPadding * 2) / 2,
                -(fabSize) / 2 - fabPadding,
                (width - fabSize - fabPadding * 2) / 2 + fabSize + fabPadding * 2,
                (fabSize) / 2 + fabPadding
            ),
            180f, -180f, false
        )
        lineTo(width - height, 0f)
        arcTo(
            Rect(width - height*2, 0f, width, height*2),
            270f, 90f, true
        )
        lineTo(0f, height)
        close()
    }
}