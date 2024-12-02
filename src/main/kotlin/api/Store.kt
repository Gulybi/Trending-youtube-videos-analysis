package api

import com.google.gson.Gson
import data.TrendingVideo
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVRecord
import data.Category
import java.io.File
import java.io.FileReader
import java.io.Reader


fun loading(map: MutableMap<String ,MutableList<TrendingVideo>>, categoryList: MutableList<Category>){
    println("Loading the data files...")

    val countries: List<String> = listOf("CA", "DE", "FR", "GB", "RU", "IN", "JP", "KR", "MX", "US")
    val files: List<Reader> = countries
        .map { country -> "data/${country}videos.csv" }
        .map(::FileReader)

    for (i in countries.indices) {
        categoryList.add(
            Gson().fromJson(
                File("data/${countries[i]}_category_id.json").readText(),
                Category::class.java
            )
        )
        val records: Iterable<CSVRecord> = CSVFormat.EXCEL.parse(files[i]).drop(1)
        val countryTrendingVideos: MutableList<TrendingVideo> = mutableListOf()
        for (record in records) {
            countryTrendingVideos.add(TrendingVideo(record))
        }
        map[countries[i]] = countryTrendingVideos
    }
    files.forEach {
        it.close()
    }
    println("Loading is done!")
}