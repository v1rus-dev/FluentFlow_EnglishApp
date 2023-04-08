package yegor.cheprasov.fluentflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import yegor.cheprasov.fluentflow.decompose.parent.RealParentScreenComponent
import yegor.cheprasov.fluentflow.ui.compose.ApplicationScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootComponent = RealParentScreenComponent(defaultComponentContext())

        setContent {
            ApplicationScreen(component = rootComponent)
        }
    }
}