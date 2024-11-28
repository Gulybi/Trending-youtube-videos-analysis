import com.google.gson.Gson
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVRecord
import java.io.File
import java.io.FileReader
import java.io.Reader
import java.net.URL
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


private fun String.toLocalDate(): LocalDate {
    val (year, day, month) = this.split('.').map(String::toInt)

    return LocalDate.of(2000 + year, month, day)
}

class TrendingVideo(data: CSVRecord){
    val video_id: String = data[0]
    val trending_date: LocalDate = data[1].toLocalDate()
    val title: String = data[2]
    val channel_title: String = data[3]
    val category_id: String = data[4]
    val publish_time: LocalDateTime = LocalDateTime.parse(data[5], DateTimeFormatter.ISO_DATE_TIME)
    val tags: String = data[6]
    val views: Long = data[7].toLong()
    val likes: Long = data[8].toLong()
    val dislikes: Long = data[9].toLong()
    val comment_count: Long = data[10].toLong()
    val thumbnail_link: URL = URL(data[11])
    val comments_disabled: Boolean = data[11].toBoolean()
    val ratings_disabled: Boolean = data[12].toBoolean()
    val video_error_or_removed: Boolean = data[13].toBoolean()
    val description: String = data[14]
}

data class Snippet(
    val channelId: String,
    val title: String,
    val assignable: Boolean
)

data class VideoCategory(
    val kind: String,
    val etag: String,
    val id: String,
    val snippet: Snippet
)

data class Category(val kind: String, val etag: String, val items: List<VideoCategory>)

var trendingVideos: MutableList<MutableList<TrendingVideo>> = mutableListOf()

var categoryList : MutableList<Category> = mutableListOf()

private fun loading(){
    println("Loading the data files...")
    val countries: List<String> = listOf("CA", "DE", "FR", "GB" /*"IN", "JP", "KR", "MX", "RU", "US"*/)
    val files: List<Reader> = countries
        .map {country -> "data/${country}videos.csv"}
        .map(::FileReader)
    for(i in countries.indices){
        categoryList.add(Gson().fromJson(File("data/${countries[i]}_category_id.json").readText(), Category::class.java))
        val records: Iterable<CSVRecord> = CSVFormat.EXCEL.parse(files[i]).drop(1)
        val countryTrendingVideos : MutableList<TrendingVideo>  = mutableListOf()
        for(record in records){
            countryTrendingVideos.add(TrendingVideo(record))
        }
        trendingVideos.add(countryTrendingVideos)
    }


    println("Loading is done!")
}


private fun questionMenu(){
    while(true){
        println("What question do you want to be answered?")
        println("""
            1. What are the top 5 categories of trending videos in Germany?
            2. Which videos trended in more than half of the countries?
            3. Are there any videos that trended in almost all countries, but are missing from just one or two of them?
            4. Which videos with more than 100k views have the best and worst like/dislike ratios?
            5. How many channels had just a single highly trending video?
            6. What is the top 5 most disliked video with over 1 million views?
            7. Which video is the most watched video in Great Britain and how many view it has?
        """.trimIndent())
        when(Scanner(System.`in`).nextInt()){
            1 -> question1()
            2 -> question2()
            3 -> question3()
            4 -> question4()
            5 -> question5()
            6 -> question6()
            7 -> question7()
            8 -> question8()
            9 -> question9()
            10 -> question10()
        }
    }
}

private inline fun question1(){
    trendingVideos[1]
        .groupingBy { it.category_id }
        .eachCount()
        .toList()
        .sortedByDescending { (category_id, count) -> count }
        .take(5)
        .forEach{
            (category_id, count) ->
            run {
                categoryList[1].items.forEach {
                    if (it.id == category_id) {
                        println(it.snippet.title)
                    }
                }
            }
        }
}

private inline fun question2(){
    println("Calculating...")

    var videoNames : MutableMap<String, Int> = mutableMapOf()
    var contained : Boolean = false

    trendingVideos.forEach { it ->
        it.map{ it.title }.forEach { title ->
            videoNames[title] = videoNames.getOrDefault(title, 0) + 1
        }
    }

    videoNames.forEach { (name, count) ->
        if(count > 5)
            println(name)
    }

}

private inline fun question3(){

}

private inline fun question4(){

}

private inline fun question5(){

}

private inline fun question6(){

}

private inline fun question7(){

}

private inline fun question8(){

}

private inline fun question9(){

}

private inline fun question10(){

}

fun main(){
    loading()
    questionMenu()
}