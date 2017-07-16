package example.duane0728.android_flux

import android.app.Application
import android.content.Context
import com.squareup.leakcanary.LeakCanary

class App : Application() {

  companion object {
    fun component(context: Context) = (context.applicationContext as App).appComponent
  }

  private val appComponent: AppComponent by lazy {
    DaggerAppComponent.builder()
        .build()
  }

  override fun onCreate() {
    super.onCreate()
    LeakCanary.install(this)
  }
}