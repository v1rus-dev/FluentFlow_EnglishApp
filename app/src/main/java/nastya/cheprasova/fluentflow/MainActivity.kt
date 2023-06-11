package nastya.cheprasova.fluentflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import nastya.cheprasova.fluentflow.decompose.parent.RealParentScreenComponent
import nastya.cheprasova.fluentflow.ui.compose.ApplicationScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootComponent = RealParentScreenComponent(defaultComponentContext())

        setContent {
            ApplicationScreen(component = rootComponent)
        }
    }
}