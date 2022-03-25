package me.dannly.home_data.local.converters

import androidx.room.TypeConverter
import org.json.JSONArray
import org.json.JSONObject

class Converters {

    @TypeConverter
    fun fromJsonString(value: String?): List<String>? {
        if (value == null)
            return null
        val list = mutableListOf<String>()
        val jsonArray = JSONObject(value).getJSONArray("values")
        for (i in 0 until jsonArray.length())
            list.add(jsonArray.getString(i))
        return list
    }

    @TypeConverter
    fun toJsonString(value: List<String?>?): String? {
        if (value == null)
            return null
        val jsonObject = JSONObject()
        val jsonArray = JSONArray()
        value.forEach {
            jsonArray.put(it ?: return@forEach)
        }
        jsonObject.put("values", jsonArray)
        return jsonObject.toString()
    }
}