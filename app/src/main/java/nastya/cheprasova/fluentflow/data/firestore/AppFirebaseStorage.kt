package nastya.cheprasova.fluentflow.data.firestore

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class AppFirebaseStorage {
    val storage = Firebase.storage

    val grammarReference = storage.getReference("grammars")
    val wordsReference = storage.getReference("words")
}