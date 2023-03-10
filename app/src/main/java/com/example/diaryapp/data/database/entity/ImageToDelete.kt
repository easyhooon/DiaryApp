package com.example.diaryapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.diaryapp.util.Constant

@Entity(tableName = Constant.IMAGE_TO_DELETE_TABLE)
data class ImageToDelete(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val remoteImagePath: String
)
