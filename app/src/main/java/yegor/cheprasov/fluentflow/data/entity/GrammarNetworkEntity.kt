package yegor.cheprasov.fluentflow.data.entity

data class GrammarNetworkEntity(
    val title: String = "",
    val subtitle: String = "",
    val fileName: String = "",
    val exerciseFile: String = "",
    val level: Int = 0,
    val examples: List<String> = listOf()
)
