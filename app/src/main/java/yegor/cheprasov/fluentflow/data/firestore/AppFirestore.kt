package yegor.cheprasov.fluentflow.data.firestore

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AppFirestore {
    val db = Firebase.firestore
}