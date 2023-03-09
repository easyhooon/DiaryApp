package com.example.diaryapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diaryapp.data.database.entity.ImagesToUpload

@Dao
interface ImagesToUploadDao {

    @Query("SELECT * FROM images_to_update ORDER By id ASC")
    suspend fun getAllImages(): List<ImagesToUpload>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImageToUpload(imagesToUpload: ImagesToUpload)

    @Query("DELETE FROM images_to_upload WHERE id=:imageId")
    suspend fun cleanupImage(imageId: Int)
}