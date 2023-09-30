package com.example.unorthodoxassignment.presentation.signInClients

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit

class PhoneClient(
    val context: Context
) {
    val fireStore = FirebaseFirestore.getInstance()
    val userDetailCollection = fireStore.collection("UserDetails")


    fun sendOtp(
        auth: FirebaseAuth,
        phone: String,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks,
        activity: Activity,
        isSent: () -> Unit
    ) {
        return try {


            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber("+91$phone")
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(callbacks)
                .build()
            auth.setLanguageCode("en")
            PhoneAuthProvider.verifyPhoneNumber(options)
            isSent()


        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()

        }
    }

    suspend fun verifyNumber(
        credential: PhoneAuthCredential,
        auth: FirebaseAuth,
        activity: Activity,
        context: Context,
        message: MutableState<String>,
        phone: String,
        onVerificationComplete: () -> Unit
    ): Boolean {
        val bool = mutableStateOf(false)
        auth.signInWithCredential(credential).addOnCompleteListener(activity) { task ->
            if (task.isSuccessful) {
                message.value = "Verification Successful"
                Toast.makeText(context, "Verification Successful", Toast.LENGTH_SHORT).show()
                bool.value = true
                onVerificationComplete()

            } else {
                if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    Log.d(
                        "Verification",
                        "verifyNumber: ${(task.exception as FirebaseAuthInvalidCredentialsException).message} "
                    )
                }
            }

        }.await()
        return bool.value
    }
}
