package com.example.unorthodoxassignment.presentation.signInScreen

import android.app.Activity
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.rounded.Lock
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
import androidx.compose.ui.text.input.KeyboardType
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
import com.example.unorthodoxassignment.presentation.signInClients.PhoneClient
import com.example.unorthodoxassignment.ui.theme.firasansFamily
import com.example.unorthodoxassignment.ui.theme.ptSansFamily
import com.example.unorthodoxassignment.ui.theme.signInPrimaryColor
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun PhoneNumLogin(
    navHostController: NavHostController
) {
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {


            val auth = FirebaseAuth.getInstance()
            var placeHolder = "Enter Your Phone Number"
            var icon = Icons.Filled.Phone
            var text by remember {
                mutableStateOf("")
            }
            var buttonText by remember {
                mutableStateOf("Send Otp")
            }
            var txtColor = Color.Black
            if (isSystemInDarkTheme()) {
                txtColor = Color.White
            }


            var otp by remember {
                mutableStateOf("")
            }

            var message = remember {
                mutableStateOf("")
            }


            val signInVm = viewModel<SignInViewModel>()


            val context = LocalContext.current


            var signingIn by remember {
                mutableStateOf(false)
            }


            var verificationId by remember {
                mutableStateOf("")
            }

            var codeSent by remember {
                mutableStateOf(false)
            }
            var step2 by remember {
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = {
                    Icon(imageVector = icon, contentDescription = "")
                }
            )
            AnimatedVisibility(codeSent) {
                OutlinedTextField(value = otp, onValueChange = {
                    if (it.length <= 6) {
                        otp = it
                    }
                }, placeholder = {
                    Text(
                        text = "Enter 6 digit Otp",
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
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    leadingIcon = {
                        Icon(imageVector = Icons.Rounded.Lock, contentDescription = "")
                    })
            }
            Box {

                if (signingIn || step2) {
                    CircularProgressIndicator(
                        color = signInPrimaryColor
                    )
                } else {

                    OutlinedButton(
                        onClick = {


                            val phoneClient by lazy {
                                PhoneClient(context)
                            }
                            signInVm.viewModelScope.launch(Dispatchers.Main) {


                                if (!codeSent) {
                                    signingIn = true
                                    phoneClient.sendOtp(
                                        auth,
                                        phone = text,
                                        callbacks,
                                        context as Activity
                                    ) {
                                        step2 = true
                                        signingIn = false
                                    }


                                } else {
                                    signingIn = true


                                    val credential: PhoneAuthCredential =
                                        PhoneAuthProvider.getCredential(
                                            verificationId, otp
                                        )
                                    phoneClient.verifyNumber(
                                        credential,
                                        auth,
                                        context as Activity,
                                        context,
                                        message,
                                        phone = text
                                    ) {
                                        navHostController.navigate(NavCons.Home)
                                        signingIn = false

                                    }

                                }
                            }
                        }, border = BorderStroke(1.dp, signInPrimaryColor)

                    ) {
                        Text(
                            text = buttonText,
                            color = signInPrimaryColor,
                            fontFamily = ptSansFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp
                        )
                    }
                }
            }
            callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    message.value = "Verification Successful"

                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    message.value = "Failed to Verify User ...${p0.message}"
                    Toast.makeText(context, "Verification Failed", Toast.LENGTH_SHORT).show()
                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)
                    Toast.makeText(context, "Code Sent Please Check Messages", Toast.LENGTH_SHORT)
                        .show()
                    verificationId = p0
                    codeSent = true
                    buttonText = "Verify"
                    step2 = false
                    signingIn = false
                }


            }
        }
    }
}