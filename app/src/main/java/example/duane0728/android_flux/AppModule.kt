package example.duane0728.android_flux

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import example.duane0728.android_flux.data.api.GithubService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

  @Provides
  fun okHttp(): OkHttpClient = OkHttpClient.Builder().build()

  @Provides
  fun moshi(): Moshi = Moshi.Builder()
      .add(KotlinJsonAdapterFactory())
      .build()

  @Singleton
  @Provides
  fun retrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit
      = Retrofit.Builder()
      .baseUrl("https://api.github.com/")
      .client(okHttpClient)
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .build()

  @Singleton
  @Provides
  fun githubService(retrofit: Retrofit): GithubService
      = retrofit.create(GithubService::class.java)
}
