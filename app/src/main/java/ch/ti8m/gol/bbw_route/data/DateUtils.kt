package ch.ti8m.gol.bbw_route.data

import com.google.gson.internal.bind.util.ISO8601Utils
import java.text.ParsePosition
import java.util.*


object DateUtils {

    fun parse(dateStr: String): Date {
        return ISO8601Utils.parse(dateStr, ParsePosition(0))
    }

    fun format(date: Date): String {
        return ISO8601Utils.format(date)
    }

    fun formatDuration(duration: String): String {
        val time = duration.substring(3)

        val hoursString = time.substring(0, 2)
        val hours = Integer.parseInt(hoursString)
        val minutesString = time.substring(3, 5)
        val minutes = Integer.parseInt(minutesString)

        val sb = StringBuilder()

        if (hours > 0) {
            val hoursTitle: String = "" + hours + "h "
            sb.append(hoursTitle)
        }
        if (minutes > 0) {
            val minutesTitle: String = "" + minutes + "min"
            sb.append(minutesTitle)
        }

        return sb.toString()
    }


}