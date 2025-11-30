package com.aroy.ebookstore.core.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest

/**
 * Created by Amit Roy on Date : 28/11/25
 */
@Composable
fun ImageView(
    imageUrl: String,
    title: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val imageRequest = ImageRequest.Builder(context)
        .data(imageUrl.replace("http", "https"))
        .memoryCacheKey(imageUrl.replace("http", "https"))
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .size(80, 110)
        .build()

    AsyncImage(
        modifier = modifier
            .height(110.dp)
            .width(80.dp),
        model = imageRequest,
        contentDescription = title,
        contentScale = ContentScale.Crop,
        placeholder = rememberVectorPainter(Icons.Default.Image),
        error = rememberVectorPainter(Icons.Default.BrokenImage),
        fallback = rememberVectorPainter(Icons.Default.BrokenImage)
    )
}