package com.example.unorthodoxassignment.presentation.components

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.unorthodoxassignment.presentation.viewmodel.HomeViewModel
import com.example.unorthodoxassignment.ui.theme.signInPrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBox(
    homeViewModel: HomeViewModel
) {
    Row(modifier = Modifier.fillMaxWidth()) {

        val searchText = homeViewModel.searchText.collectAsState()
        val interactionSource = MutableInteractionSource()
        val isfoucues = interactionSource.collectIsFocusedAsState()

        OutlinedTextField(
            value = searchText.value,
            onValueChange = {
                homeViewModel.saveSearchTExt(it)

            },
            leadingIcon = {
                Icon(imageVector = Icons.Outlined.Search, contentDescription = "")
            },
            placeholder = {
                Text(text = "Search books here...")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            keyboardActions = KeyboardActions(onSearch = {
                homeViewModel.getSearchResult(searchText.value)
            }
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = signInPrimaryColor
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            trailingIcon = {
                if (isfoucues.value) {
                    IconButton(onClick = {

                        homeViewModel.clearSearch()


                    }) {


                        Icon(imageVector = Icons.Outlined.Clear, contentDescription = "")
                    }
                }
            },
            interactionSource = interactionSource
        )
    }
}