package yegor.cheprasov.fluentflow

import android.app.Application
import com.google.firebase.FirebaseApp
import yegor.cheprasov.fluentflow.di.KoinInjector

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        KoinInjector.inject(this)
    }

}