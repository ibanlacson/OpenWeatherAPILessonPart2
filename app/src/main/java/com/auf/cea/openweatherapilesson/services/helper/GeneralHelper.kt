package com.auf.cea.openweatherapilesson.services.helper

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class GeneralHelper {
    companion object {
        fun getDay(timeStamp: Long): String{
            return SimpleDateFormat("EEEE", Locale.ENGLISH).format(timeStamp * 1000)
        }

        fun getTime(timeStamp: Long): String{
            return SimpleDateFormat("hh:mm aa", Locale.ENGLISH).format(timeStamp * 1000)
        }

        fun getDate(timeStamp: Long): String{
            return SimpleDateFormat("MMMM DD", Locale.ENGLISH).format(timeStamp * 1000)
        }

        fun getPop(population: Int): String{
            var popSize:Double = population/1000.0
            val popLength:Int = popSize.toInt().toString().length
            var returnString = ""
            if (popLength <= 3) {
                returnString = popSize.toInt().toString()+"k"
            }
            else if (popLength in 4..6) {
                popSize /= 1000.0
                returnString = popSize.toInt().toString()+"M"
            }
            else if (popLength in 7..9) {
                popSize /= 1000.0
                returnString = popSize.toInt().toString()+"B"
            }

            return returnString
        }
    }
}