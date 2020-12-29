package com.jason.rickandmorty.data.DI

import android.content.Context
import android.content.SharedPreferences
import com.jason.rickandmorty.data.database.*
import com.jason.rickandmorty.data.network.RickAndMortyAPI
import com.jason.rickandmorty.ui.MyApplication
import com.jason.rickandmorty.ui.character.CharacterFragment
import com.jason.rickandmorty.ui.character.CharacterRepository
import com.jason.rickandmorty.ui.episode.EpisodeDetailFragment
import com.jason.rickandmorty.ui.episode.EpisodeFragment
import com.jason.rickandmorty.ui.episode.EpisodeRepository
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
interface AppComponent {
    fun inject(characterFragment: CharacterFragment)
    fun inject(episodeFragment: EpisodeFragment)
    fun inject(episodeDetailFragment: EpisodeDetailFragment)
}

@Module
class DataModule{
    @Singleton
    @Provides
    fun sharedPreference(): SharedPreferences{
        return MyApplication.getInstance().getSharedPreferences(file_name, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun characterDao(appDataBase: AppDataBase): CharacterDao {
        return appDataBase.characterDao()
    }
    @Singleton
    @Provides
    fun episodeDao(appDataBase: AppDataBase): EpisodeDao {
        return appDataBase.episodeDao()
    }
    @Singleton
    @Provides
    fun locationDao(appDataBase: AppDataBase): LocationDao {
        return appDataBase.locationDao()
    }

    @Singleton
    @Provides
    fun appDataBase(): AppDataBase {
        return AppDataBase.getInstance()
    }

    @Singleton
    @Provides
    fun retrofitClient(): RickAndMortyAPI{
        return RickAndMortyAPI.invoke()
    }

}