package com.example.unorthodoxassignment.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.disk.DiskCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.unorthodoxassignment.R
import com.example.unorthodoxassignment.data.entity.books.Book
import com.example.unorthodoxassignment.ui.theme.firasansFamily
import com.example.unorthodoxassignment.ui.theme.poppins

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BookItem(
    book: Book,
    bookClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(20.dp),
        onClick = {
            bookClick()
        }
    ) {
        Row(modifier = Modifier.fillMaxSize()) {


            Box(
                modifier = Modifier
                    .width(130.dp)
                    .fillMaxHeight()
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {


                GlideImage(
                    model = null, contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.clip(RoundedCornerShape(5))
                ) {
                    it.load(book.image)


                        .skipMemoryCache(true)
                        .error(R.drawable.errorplaceholder)


                }
            }
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontFamily = poppins,
                                fontSize = 22.sp
                            )
                        ) {
                            append("Title : ")
                        }
                        append(book.title)
                    }, fontSize = 20.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    maxLines = 2
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontFamily = poppins,
                                fontSize = 22.sp
                            )
                        ) {
                            append("Author : ")
                        }
                        append(book.authors)
                    }, fontSize = 20.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    maxLines = 2

                )

            }

        }
    }
}

@Preview
@Composable
private fun BookPreview() {
    BookItem(
        book = Book(
            id = "191204742X",
            title = "The Official Raspberry Pi Handbook 2023",
            subtitle = "",
            authors = "David Crookes, PJ Evans, Rosie Hattersley, Phil King, Nicola King, KG Orphanides, Nik Rawlinson, Mark Vanstone",
            image = "https://www.dbooks.org/img/books/191204742Xs.jpg",
            url = "https://www.dbooks.org/the-official-raspberry-pi-handbook-2023-191204742x/"
        )
    ) {

    }
}