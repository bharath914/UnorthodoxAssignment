package com.example.unorthodoxassignment.data.entity.signIn

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData(
    val userId: String = "",
    val userName: String = "",
    val profilePictureUrl: String? = "",
    val email: String? = ""
)