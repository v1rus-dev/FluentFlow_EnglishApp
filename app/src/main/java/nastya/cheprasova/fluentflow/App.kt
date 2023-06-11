package nastya.cheprasova.fluentflow

import android.app.Application
import com.google.firebase.FirebaseApp
import nastya.cheprasova.fluentflow.di.KoinInjector

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        KoinInjector.inject(this)
    }

}