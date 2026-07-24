package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CropDao {
    @Query("SELECT * FROM diagnosis_history ORDER BY timestamp DESC")
    fun getAllDiagnoses(): Flow<List<DiagnosisEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiagnosis(diagnosis: DiagnosisEntity): Long

    @Query("DELETE FROM diagnosis_history WHERE id = :id")
    suspend fun deleteDiagnosisById(id: Long)

    @Query("SELECT * FROM crop_profiles ORDER BY id DESC")
    fun getAllCropProfiles(): Flow<List<CropProfileEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCropProfile(profile: CropProfileEntity): Long

    @Query("DELETE FROM crop_profiles WHERE id = :id")
    suspend fun deleteCropProfileById(id: Long)

    @Query("SELECT * FROM community_posts ORDER BY timestamp DESC")
    fun getAllCommunityPosts(): Flow<List<CommunityPostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCommunityPost(post: CommunityPostEntity): Long

    @Query("UPDATE community_posts SET likesCount = likesCount + 1 WHERE id = :id")
    suspend fun incrementPostLikes(id: Long)

    @Query("SELECT * FROM soil_tests ORDER BY timestamp DESC")
    fun getAllSoilTests(): Flow<List<SoilTestEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSoilTest(soilTest: SoilTestEntity): Long
}
