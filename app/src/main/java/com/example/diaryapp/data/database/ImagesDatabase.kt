package com.example.diaryapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.diaryapp.data.database.entity.ImagesToUpload

@Database(
    entities = [ImagesToUpload::class],
    version = 1,
    exportSchema = false
)
abstract class ImagesDatabase: RoomDatabase() {
    abstract fun imageToUploadDao(): ImagesToUploadDao
}