package org.secfirst.umbrella.di.module

import android.app.Application
import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.disposables.CompositeDisposable
import org.secfirst.umbrella.data.VirtualStorage
import org.secfirst.umbrella.data.database.account.AccountDao
import org.secfirst.umbrella.data.database.account.AccountRepo
import org.secfirst.umbrella.data.database.account.AccountRepository
import org.secfirst.umbrella.data.database.checklist.ChecklistDao
import org.secfirst.umbrella.data.database.checklist.ChecklistRepo
import org.secfirst.umbrella.data.database.checklist.ChecklistRepository
import org.secfirst.umbrella.data.database.content.ContentDao
import org.secfirst.umbrella.data.database.content.ContentRepo
import org.secfirst.umbrella.data.database.content.ContentRepository
import org.secfirst.umbrella.data.database.difficulty.DifficultyDao
import org.secfirst.umbrella.data.database.difficulty.DifficultyRepo
import org.secfirst.umbrella.data.database.difficulty.DifficultyRepository
import org.secfirst.umbrella.data.database.form.FormDao
import org.secfirst.umbrella.data.database.form.FormRepo
import org.secfirst.umbrella.data.database.form.FormRepository
import org.secfirst.umbrella.data.database.lesson.LessonDao
import org.secfirst.umbrella.data.database.lesson.LessonRepo
import org.secfirst.umbrella.data.database.lesson.LessonRepository
import org.secfirst.umbrella.data.database.login.LoginDao
import org.secfirst.umbrella.data.database.login.LoginRepo
import org.secfirst.umbrella.data.database.login.LoginRepository
import org.secfirst.umbrella.data.database.reader.ReaderDao
import org.secfirst.umbrella.data.database.reader.ReaderRepo
import org.secfirst.umbrella.data.database.reader.ReaderRepository
import org.secfirst.umbrella.data.database.segment.SegmentDao
import org.secfirst.umbrella.data.database.segment.SegmentRepo
import org.secfirst.umbrella.data.database.segment.SegmentRepository
import org.secfirst.umbrella.data.disk.TentDao
import org.secfirst.umbrella.data.disk.TentLoader
import org.secfirst.umbrella.data.disk.TentRepo
import org.secfirst.umbrella.data.disk.TentRepository
import org.secfirst.umbrella.data.network.ApiHelper
import org.secfirst.umbrella.data.network.NetworkEndPoint.BASE_URL
import org.secfirst.umbrella.data.preferences.AppPreferenceHelper
import org.secfirst.umbrella.data.preferences.AppPreferenceHelper.Companion.PREF_NAME
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
class AppModule {
    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application

    @Provides
    internal fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    @Singleton
    internal fun providePreference(context: Context) = AppPreferenceHelper(context, PREF_NAME)

}

@Module
class TentContentModule {
    internal val tentDao
        get() = object : TentDao {}

    @Provides
    @Singleton
    internal fun provideTentRepo(): TentRepo = TentRepository(tentDao)

    @Provides
    @Singleton
    internal fun provideTentLoader(tentRep: TentRepo): TentLoader = TentLoader(tentRep)
}


@Module
class RepositoryModule {

    internal val rssDao
        get() = object : ReaderDao {}

    internal val contentDao
        get() = object : ContentDao {
            override fun onContentProgress(percentage: Int) {
            }
        }

    internal val formDao
        get() = object : FormDao {}

    internal val lessonDao
        get() = object : LessonDao {}

    internal val segmentDao
        get() = object : SegmentDao {}


    internal val difficultyDao
        get() = object : DifficultyDao {}

    internal val checklistDao
        get() = object : ChecklistDao {}

    internal val accountDao
        get() = object : AccountDao {}


    internal val loginDao
        get() = object : LoginDao {}

    @Provides
    @Singleton
    internal fun provideLessonDao(): LessonRepo = LessonRepository(lessonDao)

    @Provides
    @Singleton
    internal fun provideContentDao(): ContentRepo = ContentRepository(contentDao)

    @Provides
    @Singleton
    internal fun provideFormDao(): FormRepo = FormRepository(formDao)

    @Provides
    @Singleton
    internal fun provideVirtualStorage(application: Application): VirtualStorage =
        VirtualStorage(application)

    @Provides
    @Singleton
    internal fun provideReaderDao(): ReaderRepo = ReaderRepository(rssDao)

    @Provides
    @Singleton
    internal fun provideSegmentDao(): SegmentRepo = SegmentRepository(segmentDao)

    @Provides
    @Singleton
    internal fun provideDifficultyDao(): DifficultyRepo = DifficultyRepository(difficultyDao)

    @Provides
    @Singleton
    internal fun provideChecklistDao(): ChecklistRepo = ChecklistRepository(checklistDao)

    @Provides
    @Singleton
    internal fun provideAccountDao(): AccountRepo = AccountRepository(accountDao)

    @Provides
    @Singleton
    internal fun provideLoginDao(): LoginRepo = LoginRepository(loginDao)
}

@Module
class NetworkModule {

    @Provides
    @Reusable
    internal fun providePostApi(retrofit: Retrofit): ApiHelper {
        return retrofit.create(ApiHelper::class.java)
    }

    @Provides
    @Reusable
    internal fun provideRetrofitInterface(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
}
