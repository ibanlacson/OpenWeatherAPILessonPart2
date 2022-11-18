package com.auf.cea.openweatherapilesson.services.helper

import java.text.SimpleDateFormat
import java.util.*

class GeneralHelper {
    companion object {
        fun getDay(timeStamp: Long): String{
            return SimpleDateFormat("EEEE", Locale.ENGLISH).format(timeStamp * 1000)
        }

        fun getTime(timeStamp: Long): String{
            return SimpleDateFormat("hh:mm aa", Locale.ENGLISH).format(timeStamp * 1000)
        }
    }
}