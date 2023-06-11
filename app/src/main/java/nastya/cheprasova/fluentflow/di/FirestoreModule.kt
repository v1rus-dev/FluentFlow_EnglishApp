package nastya.cheprasova.fluentflow.di

import org.koin.dsl.module
import nastya.cheprasova.fluentflow.data.firestore.AppFirebaseStorage
import nastya.cheprasova.fluentflow.data.firestore.AppFirestore

val firebaseModule = module {
    single { AppFirestore() }
    single { AppFirebaseStorage() }
}