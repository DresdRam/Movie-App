package sq.mayv.core.common.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun String.toDateMillis(
    pattern: String = "yyyy-MM-dd",
    locale: Locale = Locale.US
): Long {
    return try {
        val sdf = SimpleDateFormat(pattern, locale)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        sdf.parse(this)?.time ?: 0L
    } catch (e: Exception) {
        0L
    }
}

fun Long.toDateString(
    pattern: String = "yyyy-MM-dd",
    locale: Locale = Locale.US
): String {
    return try {
        val sdf = SimpleDateFormat(pattern, locale)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        sdf.format(Date(this))
    } catch (e: Exception) {
        ""
    }
}