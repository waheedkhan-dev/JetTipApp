package com.codecollapse.jettipapp.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp


val IconButtonSizeModifier = Modifier.size(40.dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoundIconButton(
    modifier: Modifier=Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit,
    tint: Color = Color.Black.copy(alpha = 0.8f),
    backgroundColor: CardColors = CardDefaults.cardColors(),
    elevation: CardElevation = CardDefaults.cardElevation()
) {
    Card(modifier = modifier
        .padding(all = 4.dp)
        .clickable { run { onClick.invoke() } }
        .then(IconButtonSizeModifier),
        border = BorderStroke(1.dp,Color.Gray),
        shape = CircleShape,
        colors = backgroundColor,
        elevation = elevation) {
        Icon(imageVector = imageVector, contentDescription = "Plus or minus icon", tint = tint, modifier = Modifier.padding(8.dp))
    }
}