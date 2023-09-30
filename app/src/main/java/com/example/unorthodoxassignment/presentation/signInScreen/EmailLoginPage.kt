package com.example.unorthodoxassignment.presentation.signInScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.unorthodoxassignment.R
import com.example.unorthodoxassignment.presentation.navigation.NavCons
import com.example.unorthodoxassignment.presentation.viewmodel.SignInViewModel
import com.example.unorthodoxassignment.presentation.signInClients.EmailClient
import com.example.unorthodoxassignment.ui.theme.firasansFamily
import com.example.unorthodoxassignment.ui.theme.ptSansFamily
import com.example.unorthodoxassignment.ui.theme.signInPrimaryColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EmailLoginPage(
    navHostController: NavHostController
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        val scrollstate = rememberScrollState()
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(scrollstate),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {


            val context = LocalContext.current
            val placeHolder = "Enter Your Email Id"
            var icon = Icons.Filled.Email

            var text by remember {
                mutableStateOf("")
            }
            var passWord by remember {
                mutableStateOf("")
            }
            var txtColor = Color.Black
            if (isSystemInDarkTheme()) {
                txtColor = Color.White
            }

            val signInVm = viewModel<SignInViewModel>()


            var signingIn by remember {
                mutableStateOf(false)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .padding(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(Modifier.fillMaxSize()) {
                    Text(
                        text = "Login in ! ",
                        fontFamily = firasansFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 48.sp,
                        color = signInPrimaryColor
                    )
                    Text(
                        text = "Enter Your Credentials",
                        fontFamily = ptSansFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 24.sp,
                        modifier = Modifier.alpha(0.7f),
                        color = txtColor
                    )
                }
            }

            Box(modifier = Modifier.size(350.dp)) {
                GlideImage(
                    model = null,
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds
                ) {
                    it.load(R.drawable.signin_art_2)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)

                }
            }






            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 40.dp),
                horizontalArrangement = Arrangement.Start
            ) {


                Text(
                    text = "Doesn't have an account ? ",
                    fontFamily = ptSansFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                    modifier = Modifier.alpha(0.9f),
                    color = txtColor,

                    )


                Text(
                    text = "Sign Up!",
                    fontFamily = ptSansFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                    modifier = Modifier.clickable {
                        navHostController.navigate(NavCons.signUp)
                    },
                    color = signInPrimaryColor,

                    )
            }
            OutlinedTextField(
                value = text, onValueChange = {
                    text = it
                },
                placeholder = {
                    Text(
                        text = placeHolder, color = signInPrimaryColor, fontFamily = ptSansFamily,
                        fontWeight = FontWeight.Normal, fontSize = 18.sp
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = signInPrimaryColor
                ),
                modifier = Modifier
                    .padding(horizontal = 40.dp, vertical = 20.dp)
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = passWord, onValueChange = {
                    passWord = it
                },
                placeholder = {
                    Text(
                        text = "Enter Your Password",
                        color = signInPrimaryColor,
                        fontFamily = ptSansFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = signInPrimaryColor
                ),
                modifier = Modifier
                    .padding(horizontal = 40.dp, vertical = 20.dp)
                    .fillMaxWidth()
            )
            Box {

                if (signingIn) {
                    CircularProgressIndicator(
                        color = signInPrimaryColor
                    )
                } else {

                    OutlinedButton(
                        onClick = {

                            signingIn = true


                            val emailclient by lazy {
                                EmailClient(context)
                            }
                            signInVm.viewModelScope.launch(Dispatchers.Main) {


                                val success = emailclient.signInWithEmail(text, passWord)
                                if (success) {
                                    navHostController.navigate(NavCons.Home)

                                }
                                signingIn = false
                            }

                        }, border = BorderStroke(1.dp, signInPrimaryColor)

                    ) {
                        Text(
                            text = "Login", color = signInPrimaryColor, fontFamily = ptSansFamily,
                            fontWeight = FontWeight.Normal, fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}