package com.example.unorthodoxassignment.presentation.signInScreen

import android.app.Activity
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.example.unorthodoxassignment.presentation.signInClients.GoogleAuthClient
import com.example.unorthodoxassignment.ui.theme.firasansFamily
import com.example.unorthodoxassignment.ui.theme.ptSansFamily
import com.example.unorthodoxassignment.ui.theme.signInPrimaryColor
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SignInScreen(
    navHostController: NavHostController
) {


    val context = LocalContext.current
    val viewmodel = viewModel<SignInViewModel>()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {

        var txtColor = Color.Black
        if (isSystemInDarkTheme()) {
            txtColor = Color.White
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .padding(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Column {


                    Text(
                        text = "Welcome !",
                        fontSize = 48.sp,
                        fontFamily = firasansFamily,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Medium,
                        color = signInPrimaryColor
                    )
                    Text(
                        text = "Sign In to Continue",
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
                    it.load(R.drawable.sign_in_art1)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)

                }
            }
            Spacer(modifier = Modifier.height(50.dp))


            Button(
                onClick = {
                    navHostController.navigate(NavCons.login)
                },
                shape = RoundedCornerShape(15),
                colors = ButtonDefaults.buttonColors(
                    containerColor = signInPrimaryColor
                ),
                modifier = Modifier
                    .padding(start = 40.dp, end = 40.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {


                    Icon(imageVector = Icons.Rounded.Email, contentDescription = "")
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Sign In/Up With Email",
                        fontFamily = ptSansFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp
                    )
                }

            }
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = {
                    navHostController.navigate(NavCons.phoneNum)
                },
                shape = RoundedCornerShape(15),
                colors = ButtonDefaults.buttonColors(
                    containerColor = signInPrimaryColor
                ),
                modifier = Modifier
                    .padding(start = 40.dp, end = 40.dp)
                    .fillMaxWidth()
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {


                    Icon(imageVector = Icons.Rounded.Call, contentDescription = "")
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Sign In/Up With Phone",
                        fontFamily = ptSansFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp
                    )

                }

            }
            Spacer(modifier = Modifier.height(15.dp))
            Row {
                GoogleSignInButton(viewmodel = viewmodel, context = context, navHostController)
            }

        }
    }
}


@Composable
fun GoogleSignInButton(
    viewmodel: SignInViewModel,
    context: Context,
    navHostController: NavHostController
) {
    val googleAuthUiClient by lazy {
        GoogleAuthClient(
            context = context.applicationContext,
            oneTapClient = Identity.getSignInClient(
                context.applicationContext
            )
        )
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),

        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                viewmodel.viewModelScope.launch {
                    val signInResult = googleAuthUiClient.signInWithIntent(
                        intent = result.data ?: return@launch
                    )


                    viewmodel.onSignInResult(signInResult)
                    navHostController.navigate(NavCons.Home)


                }
            }
        }
    )
    Image(
        painter = painterResource(id = R.drawable.google_logo_search_new_svgrepo_com),
        contentDescription = "",
        modifier = Modifier.clickable {

            viewmodel.viewModelScope.launch(Dispatchers.IO) {


                viewmodel.updateSigningInReq(true)
                val intentSender = googleAuthUiClient.signIn()


                launcher.launch(
                    IntentSenderRequest.Builder(
                        intentSender ?: return@launch
                    ).build()
                )


            }
        }

    )
}