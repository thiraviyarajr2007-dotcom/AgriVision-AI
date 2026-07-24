package com.example.data

import android.util.Log
import com.example.data.local.CropProfileEntity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirestoreCropRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val cropProfilesCollection = firestore.collection("crop_profiles")

    /**
     * Store or update a crop profile in Firebase Firestore.
     */
    suspend fun saveCropProfileToFirestore(
        profile: CropProfileEntity,
        userId: String = "guest_farmer"
    ): Result<String> {
        return try {
            val docRef = if (profile.id > 0) {
                cropProfilesCollection.document(profile.id.toString())
            } else {
                cropProfilesCollection.document()
            }

            val data = hashMapOf(
                "id" to (if (profile.id > 0) profile.id.toString() else docRef.id),
                "userId" to userId,
                "fieldName" to profile.fieldName,
                "cropType" to profile.cropType,
                "soilType" to profile.soilType,
                "fieldAreaAcres" to profile.fieldAreaAcres,
                "sowingDate" to profile.sowingDate,
                "healthScore" to profile.healthScore,
                "notes" to profile.notes,
                "updatedAt" to System.currentTimeMillis()
            )

            docRef.set(data).await()
            Log.d("FirestoreCropRepo", "Successfully saved crop profile ${docRef.id} to Firestore")
            Result.success(docRef.id)
        } catch (e: Exception) {
            Log.e("FirestoreCropRepo", "Error saving crop profile to Firestore", e)
            Result.failure(e)
        }
    }

    /**
     * Delete a crop profile document from Firestore.
     */
    suspend fun deleteCropProfileFromFirestore(id: String): Result<Unit> {
        return try {
            cropProfilesCollection.document(id).delete().await()
            Log.d("FirestoreCropRepo", "Successfully deleted crop profile $id from Firestore")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("FirestoreCropRepo", "Error deleting crop profile $id from Firestore", e)
            Result.failure(e)
        }
    }

    /**
     * Real-time listener for crop profiles in Firestore.
     */
    fun getCropProfilesFromFirestore(userId: String = "guest_farmer"): Flow<List<CropProfileEntity>> = callbackFlow {
        val listener = cropProfilesCollection
            .whereEqualTo("userId", userId)
            .orderBy("updatedAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("FirestoreCropRepo", "Error listening for crop profiles in Firestore", error)
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val profiles = snapshot.documents.mapNotNull { doc ->
                        val fieldName = doc.getString("fieldName") ?: return@mapNotNull null
                        val cropType = doc.getString("cropType") ?: ""
                        val soilType = doc.getString("soilType") ?: ""
                        val fieldAreaAcres = doc.getDouble("fieldAreaAcres") ?: 1.0
                        val sowingDate = doc.getLong("sowingDate") ?: System.currentTimeMillis()
                        val healthScore = doc.getLong("healthScore")?.toInt() ?: 100
                        val notes = doc.getString("notes") ?: ""
                        val docIdLong = doc.id.toLongOrNull() ?: 0L

                        CropProfileEntity(
                            id = docIdLong,
                            fieldName = fieldName,
                            cropType = cropType,
                            soilType = soilType,
                            fieldAreaAcres = fieldAreaAcres,
                            sowingDate = sowingDate,
                            healthScore = healthScore,
                            notes = notes
                        )
                    }
                    trySend(profiles)
                }
            }

        awaitClose { listener.remove() }
    }
}
