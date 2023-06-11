package nastya.cheprasova.fluentflow.data.usecase

import android.content.Context

class LevelUseCase(private val context: Context) {

    companion object {
        private const val SHARED_PREF_NAME = "fluent_flow_pref"

        private const val LEVEL_KEY = "level"
    }

    private val shar = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    fun getCurrentLevelIndex(): Int = shar.getInt(LEVEL_KEY, -1)

    fun getCurrentLevel(): Level = Level.getLevelById(getCurrentLevelIndex())

    fun setLevel(level: Level) {
        shar.edit()
            .putInt(LEVEL_KEY, level.id)
            .apply()
    }
}

enum class Level(val id: Int, val levelName: String) {
    Elementary(0, "Elementary"),
    Middle(1, "Middle"),
    Advanced(2, "Advanced");

    companion object {
        fun getLevelById(id: Int) = values().find { it.id == id} ?: throw IllegalStateException("This level id is not exist in the enum class!")
    }
}