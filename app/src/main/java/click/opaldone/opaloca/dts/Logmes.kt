package click.opaldone.opaloca.dts

data class Logmes(
    val txt: String,
    val ts: Long = System.currentTimeMillis()
)
