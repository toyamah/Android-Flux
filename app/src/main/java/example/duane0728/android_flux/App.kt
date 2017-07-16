package example.duane0728.android_flux

import android.app.Application
import com.squareup.leakcanary.LeakCanary

class App : Application() {

  override fun onCreate() {
    super.onCreate()
    LeakCanary.install(this)
  }
}