package com.example.diaryapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.diaryapp.util.Constant.IMAGE_TO_UPLOAD_TABLE

@Entity(tableName = IMAGE_TO_UPLOAD_TABLE)
data class ImagesToUpload(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val remoteImagePath: String,
    val imageUri: String,
    // 파이어베이스에 이미지를 업로드할때 만약 실패할 경우 이 sessionUri 를 통해 retry 를 할 수 있음
    val sessionUri: String
)
