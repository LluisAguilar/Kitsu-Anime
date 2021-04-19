package com.applaudo.android.applaudoscodechallenge.data.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.applaudo.android.applaudoscodechallenge.data.db.dao.ArticleDao
import com.applaudo.android.applaudoscodechallenge.data.db.entities.ArticlesFavoriteEntity

@Database(entities = arrayOf(ArticlesFavoriteEntity::class), version = 1, exportSchema = false)
abstract class ApplaudoBD() : RoomDatabase() {

    abstract fun articleDao(): ArticleDao


    companion object {

        @Volatile
        private var mInstance: ApplaudoBD? = null

        fun getDatabaseClient(application: Application): ApplaudoBD {

            if (mInstance == null) {
                synchronized(this) {
                    mInstance = Room
                        .databaseBuilder(application, ApplaudoBD::class.java, "APPLAUDO_DATABASE")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            } else {
                return mInstance!!
            }

            return mInstance!!
        }

    }
}