package sq.mayv.core.common

sealed interface GenericState<out T> {
    data class Success<out T>(val data: T) : GenericState<T>
    data class Failure(val errorCode: ErrorCode?) : GenericState<Nothing>
}

enum class ErrorCode(val code: Int) {
    UNSPECIFIED(code = -1),
    NOT_FOUND(code = 404),
    BAD_REQUEST(code = 400),
    UNAUTHORIZED(code = 401),
    SERVER_ERROR(code = 500),
    DATABASE_ERROR(code = 1000);

    companion object {
        fun from(code: Int): ErrorCode = entries.firstOrNull { it.code == code } ?: UNSPECIFIED
    }
}

fun ErrorCode.errorMessage(): String {
    return "Error"
}