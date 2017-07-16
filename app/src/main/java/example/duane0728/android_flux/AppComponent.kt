package example.duane0728.android_flux

import dagger.Component
import example.duane0728.android_flux.ui.main.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
  fun inject(mainActivity: MainActivity)
}