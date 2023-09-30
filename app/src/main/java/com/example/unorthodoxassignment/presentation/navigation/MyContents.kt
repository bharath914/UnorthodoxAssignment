package com.example.unorthodoxassignment.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.unorthodoxassignment.presentation.BookScreen
import com.example.unorthodoxassignment.presentation.HomeScreen
import com.example.unorthodoxassignment.presentation.signInScreen.PhoneNumLogin
import com.example.unorthodoxassignment.presentation.signInScreen.EmailLoginPage
import com.example.unorthodoxassignment.presentation.signInScreen.SignInScreen
import com.example.unorthodoxassignment.presentation.signInScreen.SignUpScreen

@Composable
fun MainContents(
    navHostController: NavHostController,
    start: String
) {
    NavHost(navController = navHostController, startDestination = start) {
        composable(NavCons.Home) {
            HomeScreen(navHostController = navHostController)
        }
        composable(NavCons.SignIn) {
            SignInScreen(navHostController = navHostController)
        }
        composable(NavCons.login) {
            EmailLoginPage(navHostController = navHostController)
        }
        composable(NavCons.phoneNum) {
            PhoneNumLogin(navHostController)
        }
        composable(NavCons.signUp) {
            SignUpScreen(navHostController = navHostController)
        }

        composable(NavCons.SpecificBook){
            BookScreen()
        }
    }
}





