package com.example.unorthodoxassignment.presentation.signInClients

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.example.unorthodoxassignment.data.entity.signIn.UserData
import com.example.unorthodoxassignment.presentation.navigation.NavCons
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class EmailClient(
    val context: Context
) {

    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    val userDetailsCollection = firestore.collection("UserDetails")


    suspend fun signInWithEmail(email: String, passWord: String): Boolean {


        return try {


            val result = auth.signInWithEmailAndPassword(email, passWord).await()


            if (result.user != null) {


                return true
            }
            false
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            false

        }

    }

    suspend fun singUpWithEmail(
        email: String,
        passWord: String,
        name: String,
        navHostController: NavHostController
    ) {

        try {

            val result = auth.createUserWithEmailAndPassword(email, passWord).addOnSuccessListener {
                navHostController.navigate(NavCons.Home)
            }

                .await()

            if (result.user != null) {

                userDetailsCollection
                    .document(email)
                    .set(
                        hashMapOf(
                            "id" to email,
                            "name" to name,
                            "email" to email,
                            "password" to passWord,
                            "displayUrl" to ""
                        )
                    ).await()


            }


        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()

        }

    }

    suspend fun getDetails(email: String): UserData {

        var userData = UserData()
        userDetailsCollection.document(email).get()
            .addOnSuccessListener { snap ->
                if (snap.exists()) {
                    userData = UserData(
                        userId = snap.getString("id")!!,
                        userName = snap.getString("name")!!,
                        profilePictureUrl = snap.getString("displayUrl")!!,
                        email
                    )
                }

            }
        return userData

    }
}