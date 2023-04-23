package com.example.util.model

import android.annotation.SuppressLint
import com.example.util.toRealmInstant
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
import java.time.Instant

// Mongo DB model schema(model class)
// not support enum class type
open class Diary : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId.invoke() // automatically generated
    var ownerId: String = ""
    var mood: String = Mood.Neutral.name
    var title: String = ""
    var description: String = ""
    var images: RealmList<String> = realmListOf()
    @SuppressLint("NewApi")
    var date: RealmInstant = Instant.now().toRealmInstant() // defaultValue
}
