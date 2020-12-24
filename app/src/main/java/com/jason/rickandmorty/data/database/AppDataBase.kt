package com.jason.rickandmorty.data.database

import androidx.room.*
import com.jason.rickandmorty.data.model.Character
import com.jason.rickandmorty.data.model.Episode
import com.jason.rickandmorty.data.model.Location
import com.jason.rickandmorty.ui.MyApplication


const val databaseName = "appDatabase"

@Database(entities = [Character::class, Episode::class, Location::class],
          version = 1,
          exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun locationDao(): LocationDao
    abstract fun episodeDao(): EpisodeDao

    companion object {
        @Volatile
        private var instance: AppDataBase? = null

        fun getInstance(): AppDataBase {
            return instance ?: synchronized(this) {
                instance = Room.databaseBuilder(MyApplication.getAppContext(),
                                                AppDataBase::class.java,
                                                databaseName)
                        .fallbackToDestructiveMigration()
                        .build()
                instance!!
            }
        }
    }
}

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(character: Character)

    @Query("SELECT * FROM character")
    fun readAll(): List<Character>

    @Update
    fun update(character: Character)

    @Delete
    fun delete(character: Character)

    @Query("DELETE FROM character")
    fun deleteAll()

    @Query("SELECT * FROM character WHERE id Like :id")
    fun find(id: Int)
}

@Dao
interface EpisodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(episode: Episode)

    @Query("SELECT * FROM episode")
    fun readAll(): List<Episode>

    @Update
    fun update(episode: Episode)

    @Delete
    fun delete(episode: Episode)

    @Query("DELETE FROM episode")
    fun deleteAll()

    @Query("SELECT * FROM episode WHERE id Like :id")
    fun find(id: Int)
}

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(location: Location)

    @Query("SELECT * FROM location")
    fun readAll(): List<Location>

    @Update
    fun update(location: Location)

    @Delete
    fun delete(location: Location)

    @Query("DELETE FROM location")
    fun deleteAll()

    @Query("SELECT * FROM location WHERE id Like :id")
    fun find(id: Int)
}