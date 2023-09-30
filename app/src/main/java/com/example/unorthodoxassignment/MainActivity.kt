package com.example.unorthodoxassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.unorthodoxassignment.presentation.navigation.MainContents
import com.example.unorthodoxassignment.presentation.navigation.NavCons
import com.example.unorthodoxassignment.ui.theme.UnorthodoxAssignmentTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            UnorthodoxAssignmentTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val user = Firebase.auth.currentUser
                    val navHostController = rememberNavController()
                    MainContents(
                        navHostController = navHostController,
                        start = if (user != null) NavCons.Home else NavCons.SignIn
                    )
                }
            }
        }
    }
}
