package data

import org.apache.commons.csv.CSVRecord
import java.net.URL
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TrendingVideo(data: CSVRecord){
    val videoId: String = data[0]
    val trendingDate: LocalDate = data[1].toLocalDate()
    val title: String = data[2]
    val channelTitle: String = data[3]
    val categoryId: String = data[4]
    val publishTime: LocalDateTime = LocalDateTime.parse(data[5], DateTimeFormatter.ISO_DATE_TIME)
    val tags: String = data[6]
    val views: Long = data[7].toLong()
    val likes: Long = data[8].toLong()
    val dislikes: Long = data[9].toLong()
    val commentCount: Long = data[10].toLong()
    val thumbnailLink: URL = URL(data[11])
    val commentsDisabled: Boolean = data[11].toBoolean()
    val ratingsDisabled: Boolean = data[12].toBoolean()
    val videoErrorOrRemoved: Boolean = data[13].toBoolean()
    val description: String = data[14]
}

private fun String.toLocalDate(): LocalDate {
    val (year, day, month) = this.split('.').map(String::toInt)

    return LocalDate.of(2000 + year, month, day)
}
