package yegor.cheprasov.fluentflow.di

import org.koin.dsl.module
import yegor.cheprasov.fluentflow.data.firestore.AppFirebaseStorage
import yegor.cheprasov.fluentflow.data.firestore.AppFirestore

val firebaseModule = module {
    single { AppFirestore() }
    single { AppFirebaseStorage() }
}