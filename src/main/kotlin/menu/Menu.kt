package menu

import api.Question
import java.util.*

fun questionMenu(){
    while(true){
        println("""
            What question do you want to be answered?
            1. What are the top 5 categories of trending videos in Germany?
            2. Which videos trended in more than half of the countries?
            3. Are there any videos that trended in almost all countries, but are missing from just one or two of them?
            4. Which videos with more than 100k views have the best and worst like/dislike ratios?
            5. How many channels had just a single highly trending video?
            6. What is the top 5 most disliked video with over 1 million views?
            7. Which video is the most watched video in Great Britain and how many view it has?
            8. Which video has the longest name?
            9. Which video is the most watched and the most liked video in the "Autos and Vehicles" category?
            10. What's the name of the oldest trending video in Russian?
        """.trimIndent())
        when(Scanner(System.`in`).nextInt()){
            1 -> Question.question1()
            2 -> Question.question2()
            3 -> Question.question3()
            4 -> Question.question4()
            5 -> Question.question5()
            6 -> Question.question6()
            7 -> Question.question7()
            8 -> Question.question8()
            9 -> Question.question9()
            10 -> Question.question10()
            else -> return
        }
    }
}