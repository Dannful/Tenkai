package me.dannly.onboarding_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import me.dannly.core.R
import me.dannly.core.domain.preferences.Preferences
import me.dannly.core.util.UiEvent
import me.dannly.core.util.UiText
import me.dannly.onboarding_domain.repository.LoginRepository
import javax.inject.Inject

@OptIn(
    ExperimentalCoroutinesApi::class
)
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val preferences: Preferences
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private suspend fun exitApp() {
        _uiEvent.send(UiEvent.ShowToast(UiText.StringResource(R.string.load_failed)))
        _uiEvent.send(UiEvent.CloseApp)
    }

    fun registerCode(code: String) {
        viewModelScope.launch {
            val accessToken = loginRepository.getAccessToken(code) ?: run {
                exitApp()
                return@launch
            }
            preferences.setAccessToken(accessToken)
            val userId = loginRepository.getUserID().getOrElse {
                exitApp()
                return@launch
            }
            preferences.setUserId(userId)
        }
    }
}