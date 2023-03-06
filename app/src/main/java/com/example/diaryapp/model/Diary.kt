package com.example.diaryapp.model

import com.example.diaryapp.util.toRealmInstant
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.time.Instant

// Mongo DB model schema(model class)
// not support enum class type
open class Diary : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId.create() // automatically generated
    var ownerId: String = ""
    var mood: String = Mood.Neutral.name
    var title: String = ""
    var description: String = ""
    var images: RealmList<String> = realmListOf()
    var date: RealmInstant = Instant.now().toRealmInstant() // defaultValue
}
