package com.jason.rickandmorty.data.database

import android.content.SharedPreferences
import javax.inject.Inject


const val file_name: String = "PageData"
const val default_page: Int = 1
class PageManager @Inject constructor(val instance: SharedPreferences) {
    fun putPage(key: String, value: Int) {
        with(instance.edit()) {
            putInt(key, value)
            apply()
        }
    }

    fun getPage(key: String, default: Int = default_page): Int {
        return instance.getInt(key, default)
    }

    fun incrementPage(key: String){
        val page = getPage(key) + 1
        putPage(key, page)
    }

/*    companion object{
        private var sharedPreferences: SharedPreferences? = null

        val instance: SharedPreferences
            get() {
                return sharedPreferences ?: synchronized(this){
                    sharedPreferences = MyApplication.getInstance().getSharedPreferences(file_name, Context.MODE_PRIVATE)
                    sharedPreferences!!
                }
            }
    }*/

}