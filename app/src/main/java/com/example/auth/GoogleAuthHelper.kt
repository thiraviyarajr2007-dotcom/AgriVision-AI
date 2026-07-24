package com.example.auth

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import com.example.BuildConfig
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class GoogleAuthHelper(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {
    val currentUser: FirebaseUser?
        get() = auth.currentUser

    val isUserSignedIn: Boolean
        get() = auth.currentUser != null

    /**
     * Initiates Google Sign-In using AndroidX Credential Manager and authenticates with Firebase Auth.
     */
    suspend fun signInWithGoogle(context: Context): Result<FirebaseUser> {
        return try {
            val webClientId = try {
                BuildConfig.WEB_CLIENT_ID.ifBlank { "1043502989267-default.apps.googleusercontent.com" }
            } catch (e: Exception) {
                "1043502989267-default.apps.googleusercontent.com"
            }

            Log.d("GoogleAuthHelper", "Initiating Google Sign In with Web Client ID: $webClientId")

            val credentialManager = CredentialManager.create(context)

            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(webClientId)
                .setAutoSelectEnabled(false)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = credentialManager.getCredential(
                request = request,
                context = context
            )

            val credential = result.credential
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            val idToken = googleIdTokenCredential.idToken

            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = auth.signInWithCredential(firebaseCredential).await()

            val user = authResult.user
            if (user != null) {
                Log.d("GoogleAuthHelper", "Firebase Auth successful! User: ${user.displayName} (${user.email})")
                Result.success(user)
            } else {
                Result.failure(Exception("Firebase user was null after Google sign in"))
            }
        } catch (e: GetCredentialCancellationException) {
            Log.d("GoogleAuthHelper", "User cancelled Google Sign-In flow")
            Result.failure(e)
        } catch (e: GetCredentialException) {
            Log.e("GoogleAuthHelper", "Credential Manager error: ${e.message}", e)
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("GoogleAuthHelper", "Failed to sign in with Google: ${e.message}", e)
            Result.failure(e)
        }
    }

    fun signOut() {
        auth.signOut()
    }
}
