package yegor.cheprasov.fluentflow.data.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.charset.Charset


class RandomWords(private val context: Context) {

    suspend fun getWords(): Words = withContext(Dispatchers.IO) {
        val file = Utils.getJsonFromAssets(context, "words.json")
        val gson = Gson()
        return@withContext Gson().fromJson(file, object : TypeToken<Words>() {})
    }
}

object Utils {
    fun getJsonFromAssets(context: Context, fileName: String): String =
        try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val byteArray = ByteArray(size)
            inputStream.read(byteArray)
            inputStream.close()

            String(byteArray, Charset.forName("UTF-8"))
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
}

data class Words(
    val words: List<String>
)