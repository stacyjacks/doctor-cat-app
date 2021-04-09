package kurmakaeva.anastasia.doctorcat

import android.app.Application
import android.content.Context
import com.facebook.stetho.BuildConfig
import com.facebook.stetho.Stetho
import kurmakaeva.anastasia.doctorcat.addfragment.AddCatReminderViewModel
import kurmakaeva.anastasia.doctorcat.listfragment.CatRemindersViewModel
import kurmakaeva.anastasia.doctorcat.room.RemindersDataSource
import kurmakaeva.anastasia.doctorcat.room.CatRemindersRepo
import kurmakaeva.anastasia.doctorcat.room.RoomDB
import kurmakaeva.anastasia.doctorcat.service.CatFactsApiService
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        context = this

        if(BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }

        val networkModule = module {
            single {
                retrofit.create(CatFactsApiService::class.java)
            }
        }

        val repositoryModule = module {
            viewModel { CatRemindersViewModel(get() as RemindersDataSource) }
            viewModel { AddCatReminderViewModel(get() as RemindersDataSource) }
            single { CatRemindersRepo(get()) as RemindersDataSource }
            single { RoomDB.createRemindersDao(this@App) }
        }

        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule, repositoryModule))
        }
    }

    companion object {
        var context: Context? = null
            private set
    }
}