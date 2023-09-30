package com.example.unorthodoxassignment.presentation.signInClients

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.example.unorthodoxassignment.R
import com.example.unorthodoxassignment.data.entity.signIn.SignInResult
import com.example.unorthodoxassignment.data.entity.signIn.UserData
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException


class GoogleAuthClient(
    private val context: Context,
    private val oneTapClient: SignInClient,

    ) {
    private val auth = Firebase.auth

    suspend fun signIn(): IntentSender? {

        val result = try {

            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()

        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }


    suspend fun signInWithIntent(intent: Intent): SignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken

        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)


        return try {

            val user = auth.signInWithCredential(googleCredentials).await().user


            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        userName = displayName.toString(),
                        profilePictureUrl = photoUrl.toString(),
                        email = email
                    )
                },
                errorMessage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = "Login Session Cancelled"
            )
        }


    }


    fun getSignedInUser(): UserData? = auth.currentUser?.run {
        UserData(
            userId = uid,
            userName = displayName.toString(),
            profilePictureUrl = photoUrl.toString(),
            email = email
        )
    }


    private fun buildSignInRequest(): BeginSignInRequest {

        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.Builder()
                    .setSupported(true)

                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.webClientId))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }

}