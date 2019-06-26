package com.sunil.kumar.util

import java.nio.charset.Charset

object Utilities {

    fun getSampleJson(fileName: String): String? {

        var data: String? = null

        try {

            val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)

            if (inputStream != null) {

                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()


                data = String(buffer, Charset.defaultCharset())
                return data
            }

        } catch (e: Throwable) {
            e.printStackTrace()
        }

        return data
    }

    fun getRandomProfilePicUrl(): String {
        val randomId = (1..20).random()
        return "$PROFILE_PIC_BASE_URL/$randomId"
    }

    private const val PROFILE_PIC_BASE_URL = "https://api.adorable.io/avatars"
}