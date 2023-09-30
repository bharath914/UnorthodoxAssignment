package com.example.unorthodoxassignment.presentation

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.unorthodoxassignment.R
import com.example.unorthodoxassignment.presentation.viewmodel.SpecificBookViewModel
import com.example.unorthodoxassignment.ui.theme.poppins
import com.example.unorthodoxassignment.ui.theme.signInPrimaryColor

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BookScreen(
    specificBookViewModel: SpecificBookViewModel = hiltViewModel()
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        val state = rememberScrollState()

        val context = LocalContext.current
        var url by remember {
            mutableStateOf("")
        }



        Column(
            modifier = Modifier
                .fillMaxSize()
//                .verticalScroll(state = state)
        ) {

            val book = specificBookViewModel.book.value

            if (book.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            if (book.error.isNotBlank()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                    Text(text = book.error)
                }
            }
            book.specificBook?.let { detail ->

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(start = 20.dp, end = 20.dp, top = 20.dp),
                    contentAlignment = Alignment.Center,
                    content = {
                        GlideImage(
                            model = detail.image,
                            contentDescription = "",
                            modifier = Modifier
                                .matchParentSize()
                                .clip(RoundedCornerShape(5)),
                            contentScale = ContentScale.FillBounds
                        ) {
                            it.load(detail.image)
                                .placeholder(R.drawable.placeholder)
                        }


                    })
                Spacer(modifier = Modifier.height(5.dp))
                Column(
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .verticalScroll(state)
                ) {


                    Text(
                        text = detail.title,
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 25.sp,
                        maxLines = 2,
                        fontFamily = poppins,

                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                SpanStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Normal
                                )
                            ) {
                                append("Author : ")
                            }
                            append(detail.authors)
                        },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = poppins,
                        maxLines = 2,

                        )

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        buildAnnotatedString {
                            withStyle(
                                SpanStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Normal
                                )
                            ) {
                                append("Publisher : ")
                            }
                            append(detail.publisher)
                        },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = poppins,
                        maxLines = 2
                    )
                    if (detail.subtitle.isNotBlank()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            buildAnnotatedString {
                                withStyle(
                                    SpanStyle(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontStyle = FontStyle.Normal
                                    )
                                ) {
                                    append("Description : ")
                                }
                                append(detail.subtitle)
                            },
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Light,
                            fontFamily = poppins,
                            maxLines = 10
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        buildAnnotatedString {
                            withStyle(
                                SpanStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Normal
                                )
                            ) {
                                append("Summary : ")
                            }
                            append(detail.description)
                        },
                        fontSize = 18.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Light,

                        maxLines = 10
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        buildAnnotatedString {
                            withStyle(
                                SpanStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Normal
                                )
                            ) {
                                append("Year : ")
                            }
                            append(detail.year)
                        },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light,
                        fontFamily = poppins,
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        buildAnnotatedString {
                            withStyle(
                                SpanStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Normal
                                )
                            ) {
                                append("Total Pages : ")
                            }
                            append(detail.pages)
                        },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light,
                        fontFamily = poppins,
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        OutlinedButton(
                            onClick = {
                                startBrowser(detail.url, context)
                            },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = signInPrimaryColor
                            ), shape = RoundedCornerShape(15)
                        ) {
                            Text(
                                text = " Read Book ", color = Color.Black, fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        OutlinedButton(
                            onClick = {
                                startBrowser(detail.download, context)

                            }, colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = signInPrimaryColor
                            ),
                            shape = RoundedCornerShape(15)
                        ) {
                            Text(
                                text = "Download PDF",
                                color = Color.Black,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }


            }
        }
    }
}

fun startBrowser(url: String, context: Context) {
    val intent = Intent(ACTION_VIEW, Uri.parse(url))
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}