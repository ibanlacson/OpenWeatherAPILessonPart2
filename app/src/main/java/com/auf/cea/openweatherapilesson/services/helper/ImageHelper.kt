package com.auf.cea.openweatherapilesson.services.helper

class ImageHelper {
    companion object{
        fun getImageLink(weatherType: String, partOfDay: String): String {
            var urlLink = ""
            if (weatherType == "clear") {
                if (partOfDay == "n") {
                    urlLink = "https://drive.google.com/uc?export=view&id=1Znk1KZ3tA19FDGI8JZaFeClI5A1UCtgk"
                } else {
                    urlLink = "https://drive.google.com/uc?export=view&id=1ABGUyLV080EDsr9tpAdmyjnb7bwoYmrn"
                }
            } else {
                when (weatherType) {
                    ("Thunderstorm") -> { urlLink = "https://drive.google.com/uc?export=view&id=1iYWPG7d1FP320ihpFPDPkuv7UpODBTCo"}
                    ("Drizzle") -> {urlLink = "https://drive.google.com/uc?export=view&id=17O0C2sjDBj-XXlgnvQHwEtlKIsLRw88w"}
                    ("Rain") -> {urlLink = "https://drive.google.com/uc?export=view&id=15f-TiK9mzQ8UPrlco9795F07KwYpsq0X"}
                    ("Snow") -> {urlLink = "https://drive.google.com/uc?export=view&id=1tNmFZ94Osw7Y7gC5owy-Szi6M4y3qLmM"}
                    ("Clouds") -> {urlLink = "https://drive.google.com/uc?export=view&id=1HD4gRv3CTDE_S-7COxMP3zWYcoyhkNpv"}
                }
            }
            return urlLink
        }
    }
}