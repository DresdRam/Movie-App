package sq.mayv.data.model.movies

enum class Language(val code: String) {

    English(code = "en"),
    Arabic(code = "ar");

    companion object {
        fun from(code: String): Language {
            return entries.find { code == it.code } ?: English
        }
    }
}