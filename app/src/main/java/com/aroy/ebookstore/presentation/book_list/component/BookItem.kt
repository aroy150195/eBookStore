package com.aroy.ebookstore.presentation.book_list.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aroy.ebookstore.R
import com.aroy.ebookstore.core.components.ImageView
import com.aroy.ebookstore.core.components.RatingBar
import com.aroy.ebookstore.model.BookItems
import kotlin.text.orEmpty

/**
 * Created by Amit Roy on Date : 28/11/25
 */

@Composable
fun BookItem(
    booksItems: BookItems?,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.fillMaxHeight()
                    .padding(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        )
                    ) {
                        ImageView(
                            imageUrl = booksItems?.volumeInfo?.imageLinks?.smallThumbnail.orEmpty(),
                            title = booksItems?.volumeInfo?.title.orEmpty(),
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = booksItems?.volumeInfo?.title ?: "Title not available",
                            maxLines = 1,
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = booksItems?.volumeInfo?.subtitle ?: "Subtitle not available",
                            maxLines = 1,
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 16.sp,
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = booksItems?.volumeInfo?.authors?.getOrNull(0)
                                ?: "Author not available",
                            maxLines = 1,
                            fontFamily = FontFamily.Cursive,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        RatingBar(currentRating = booksItems?.volumeInfo?.averageRating?.toInt() ?: 2)
                    }
                }
            }
        }
    }
}