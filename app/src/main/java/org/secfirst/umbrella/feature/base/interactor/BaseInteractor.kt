package org.secfirst.umbrella.feature.base.interactor

import androidx.appcompat.app.AppCompatDelegate

interface BaseInteractor {

    fun isUserLoggedIn(): Boolean

    fun setLoggedIn(isLogged: Boolean): Boolean

    fun enablePasswordBanner(enableBanner: Boolean): Boolean

    fun setSkipPassword(isSkip: Boolean): Boolean

    fun isSkippPassword(): Boolean

    fun setDefaultLanguage(isoCountry: String) : Boolean

    fun getDefaultLanguage(): String

    suspend fun resetContent(): Boolean
}
