package com.example.unorthodoxassignment.presentation.signInScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navHostController: NavHostController
) {


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {


            var placeHolder = "Enter Your Email Id"
            var icon = Icons.Filled.Email

            var name by remember {
                mutableStateOf("")
            }
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


            val context = LocalContext.current


            var signingIn by remember {
                mutableStateOf(false)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .padding(horizontal = 40.dp, vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(Modifier.fillMaxSize()) {
                    Text(
                        text = "Sign Up ! ",
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

            Box(
                modifier = Modifier.size(300.dp)
            ) {
                GlideImage(
                    model = null,
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds
                ) {
                    it.load(R.drawable.sign_in_art_03)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)

                }
            }








            OutlinedTextField(
                value = name, onValueChange = {
                    name = it
                },
                placeholder = {
                    Text(
                        text = "Enter Your Name ! ",
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
                    .fillMaxWidth(),
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                }
            )






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
                    .fillMaxWidth(),
                leadingIcon = {
                    Icon(imageVector = icon, contentDescription = "")

                }
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
                    .fillMaxWidth(),
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Lock, contentDescription = "")
                }
            )


            val emailclient by lazy {
                EmailClient(context)
            }

            Box {

                if (signingIn) {
                    CircularProgressIndicator(
                        color = signInPrimaryColor
                    )
                } else {

                    OutlinedButton(
                        onClick = {

                            signingIn = true






                            signInVm.viewModelScope.launch(IO) {

                                emailclient.singUpWithEmail(text, passWord, name, navHostController)
                            }


                        }, border = BorderStroke(1.dp, signInPrimaryColor)

                    ) {
                        Text(
                            text = "Sign Up", color = signInPrimaryColor, fontFamily = ptSansFamily,
                            fontWeight = FontWeight.Normal, fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}