package mx.edu.utez.musicacompose

import android.app.Application
import mx.edu.utez.musicacompose.data.AppDatabase

class MusicaApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getInstance(this) }
}

