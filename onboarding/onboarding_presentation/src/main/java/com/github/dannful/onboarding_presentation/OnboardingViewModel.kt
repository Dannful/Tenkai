package com.github.dannful.onboarding_presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.dannful.core_ui.makeErrorToast
import com.github.dannful.onboarding_domain.use_case.OnboardingUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val onboardingUseCases: OnboardingUseCases,
    private val application: Application
) : ViewModel() {

    fun retrieveCodeAndSubmit(url: String, onDone: () -> Unit) {
        viewModelScope.launch {
            val code = onboardingUseCases.getCodeFromUrl(url) ?: run {
                application.makeErrorToast()
                return@launch
            }
            val token = onboardingUseCases.getAccessToken(code).getOrNull() ?: run {
                application.makeErrorToast()
                return@launch
            }
            onboardingUseCases.saveAccessToken(token)
            val userId = onboardingUseCases.getViewerId().getOrNull() ?: run {
                application.makeErrorToast()
                return@launch
            }
            onboardingUseCases.saveUserId(userId)
            onDone()
        }
    }
}