package api

import data.Category
import data.TrendingVideo

object Question {

    private var trendingVideos: MutableMap<String, MutableList<TrendingVideo>> = mutableMapOf()
    private var categoryList: MutableList<Category> = mutableListOf()


    init{
        loading(trendingVideos, categoryList)
    }

    fun question1(){
        println("Currently working...")
        trendingVideos["DE"]?.let{ videos ->
            videos
                .groupingBy { it.categoryId }
                .eachCount()
                .toList()
                .sortedByDescending { (_, count) -> count }
                .take(5)
                .forEach{
                        (categoryId, _) ->
                    run {
                        categoryList[1].items.forEach {
                            if (it.id == categoryId) {
                                println(it.snippet.title)
                            }
                        }
                    }
                }
        }
    }

    fun question2(){
        println("Currently working...")

        val videoCountById = mutableMapOf<String, Int>()

        trendingVideos.forEach { (_, videos) ->
            videos.forEach{
                videoCountById[it.title] = videoCountById.getOrDefault(it.title, 0) + 1
            }
        }

        videoCountById
            .filter { (_, count) -> count > trendingVideos.keys.size/2 }
            .forEach { (videos, _) -> println(videos) }
    }

    fun question3(){
        val videoCountById = mutableMapOf<String, Int>()

        trendingVideos.forEach { (_, videos) ->
            videos.forEach{
                videoCountById[it.title] = videoCountById.getOrDefault(it.title, 0) + 1
            }
        }

        videoCountById
            .filter { (_, count) -> count == trendingVideos.keys.size - 1 || count == trendingVideos.keys.size - 2 }
            .forEach { (videos, _) -> println(videos) }
    }

    fun question4(){
        trendingVideos.forEach { countries ->
            countries.value
                .filter { it.views > 100_000 }
                .sortedByDescending { it.likes.toDouble()/(it.likes.toDouble() + it.dislikes.toDouble()) * 100.0}
                .take(10)
                .map { it.title }
                .forEach(::println)

        }
    }

    fun question5(){
        var channelsCounter = 0
        trendingVideos.forEach { (_, videos) ->
            channelsCounter = videos
                .groupingBy { it.channelTitle }
                .eachCount()
                .toList()
                .count { (_, count) -> count == 1 }
        }
        println(channelsCounter)
    }

    fun question6(){
        val top5 = mutableListOf<Pair<Long, String>>()
        trendingVideos.forEach{(_, videos) ->

            videos
                .asSequence()
                .map { Triple(it.views, it.dislikes, it.title)}
                .filter{ it.first > 1_000_000 }
                .sortedByDescending { it.second }
                .take(5)
                .map {Pair(it.second, it.third)}
                .forEach{
                    top5.add(it)
                }
        }
        top5
            .sortedByDescending { it.first }
            .map{it.second}
            .distinct()
            .take(5)
            .forEach(::println)
    }

    fun question7(){
        trendingVideos["GB"]?.let{ videos ->
            println(videos.maxOfOrNull { it.views })
            println(videos.filter{ video -> video.views == videos.maxOfOrNull { it.views } }.map{ it.title }.first())
        }
    }

    fun question8(){
        println("Currently working...")

        var maxLength = 0
        trendingVideos.forEach {(_, videos) ->
            val max = videos.map { it.title }.maxOf { it.length }
            if(max > maxLength)
                maxLength = max
        }


        trendingVideos.forEach { (_, videos) ->
            val title = videos.map { it.title }.firstOrNull { title -> title.length == maxLength }
            if(title != null){
                println(title)
            }
        }
    }

    fun question9(){
        val mostWatchedLikedVideos = mutableListOf<Triple<Long, Long, String>>()
        trendingVideos.forEach{ (_, videos) ->
            mostWatchedLikedVideos.add(
                videos
                    .filter { it.categoryId == "2" }
                    .map { Triple(it.views, it.likes, it.title) }
                    .sortedWith(compareByDescending<Triple<Long, Long, String>> {it.first}
                        .thenByDescending { it.second })
                    .first()
            )
        }

        println(
            mostWatchedLikedVideos
                .sortedWith(compareByDescending<Triple<Long, Long, String>> {it.first}
                    .thenByDescending { it.second })
                .first()
                .third
        )
    }

    fun question10(){
        println(
            trendingVideos["RU"]
                ?.map { Pair(it.title, it.publishTime) }
                ?.asSequence()
                ?.filter { video -> video.second == trendingVideos["RU"]?.minOfOrNull { it.publishTime } }
                ?.map { it.first }?.firstOrNull()

        )
    }
}