package com.example.unorthodoxassignment.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.unorthodoxassignment.presentation.components.BookItem
import com.example.unorthodoxassignment.presentation.components.SearchBox
import com.example.unorthodoxassignment.presentation.viewmodel.HomeViewModel
import com.example.unorthodoxassignment.ui.theme.poppins
import com.example.unorthodoxassignment.ui.theme.signInPrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    Scaffold(
        topBar = {
            SearchBox(homeViewModel)
        }
    ) {


        Surface(modifier = Modifier.padding(it)) {

            Column {

                val books = homeViewModel.booksList.collectAsState().value

                if (books.isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        LinearProgressIndicator()
                    }
                }
                if (books.error.isNotBlank()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = books.error)
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp)
                ) {
                    Text(
                        text = "Recently Uploaded Books",
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        color = signInPrimaryColor,
                        fontSize = 20.sp
                    )
                }

                LazyColumn(
                    content = {
                        itemsIndexed(books.response.books) { index, item ->
                            BookItem(book = item) {
                                navHostController.navigate("Book/${item.id}")

                            }
                        }
                    }
                )

            }
        }
    }

}