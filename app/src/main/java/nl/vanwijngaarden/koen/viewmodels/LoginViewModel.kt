package nl.vanwijngaarden.koen.viewmodels

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.vanwijngaarden.koen.Screen
import nl.vanwijngaarden.koen.api.Network
import nl.vanwijngaarden.koen.datastore.ApplicationPreferences

class LoginViewModel : ViewModel() {
    private val apiClient = Network.apiClient

    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState

    val loginFailState = MutableStateFlow(false)
    val registerFailState = MutableStateFlow(false)
    val registerUserExistsState = MutableStateFlow(false)

    fun resetFails() {
        loginFailState.value = false
        registerFailState.value = false
        registerUserExistsState.value = false
    }

    fun login(
        username: String,
        password: String,
        navController: NavController,
        dataStore: ApplicationPreferences,
        selectedItem: MutableState<Int>
        ) {
        viewModelScope.launch {
            _loadingState.value = true
            loginFailState.value = false

            val response = apiClient.loginUser(username, password)
            if (response.isSuccessful) {
                dataStore.saveToken(response.body.authToken)
                selectedItem.value = 0
                navController.navigate(Screen.HomeScreen.route) {
                    popUpTo(0)
                }
            } else {
                loginFailState.value = true
            }
            _loadingState.value = false
        }
    }

    fun register(
        username: String,
        password: String,
        navController: NavController,
        dataStore: ApplicationPreferences,
        selectedItem: MutableState<Int>
    ) {
        viewModelScope.launch {
            _loadingState.value = true
            registerFailState.value = false
            registerUserExistsState.value = false

            val response = apiClient.registerUser(username.trim(), password.trim())
            if (response.isSuccessful) {
                if (response.body.success) {
                    login(username.trim(), password.trim(), navController, dataStore, selectedItem)
                } else {
                    registerUserExistsState.value = true
                }
            } else {
                registerFailState.value = true
            }
            _loadingState.value = false
        }
    }
}