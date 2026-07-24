package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diagnosis_history")
data class DiagnosisEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val cropName: String,
    val diseaseName: String,
    val isHealthy: Boolean,
    val severity: String,
    val confidence: Int,
    val imageUri: String,
    val resultJson: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "crop_profiles")
data class CropProfileEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val fieldName: String,
    val cropType: String,
    val soilType: String,
    val fieldAreaAcres: Double,
    val sowingDate: Long,
    val healthScore: Int = 90,
    val notes: String = ""
)

@Entity(tableName = "community_posts")
data class CommunityPostEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val authorName: String,
    val authorRegion: String,
    val cropType: String,
    val category: String,
    val title: String,
    val content: String,
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "soil_tests")
data class SoilTestEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val farmName: String,
    val soilType: String,
    val phLevel: Double,
    val nitrogenRating: String,
    val phosphorusRating: String,
    val potassiumRating: String,
    val organicCarbon: Double,
    val timestamp: Long = System.currentTimeMillis()
)

