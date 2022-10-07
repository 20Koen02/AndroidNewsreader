package nl.vanwijngaarden.koen.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import nl.vanwijngaarden.koen.R
import nl.vanwijngaarden.koen.datastore.ApplicationPreferences
import nl.vanwijngaarden.koen.viewmodels.LoginViewModel
import nl.vanwijngaarden.koen.viewmodels.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun LoginScreen(
    drawerState: DrawerState,
    sharedModel: SharedViewModel,
    navController: NavController,
    selectedItem: MutableState<Int>,
    loginModel: LoginViewModel = viewModel()
) {
    val loading by loginModel.loadingState.collectAsState()
    val loginFail by loginModel.loginFailState.collectAsState()
    val registerFail by loginModel.registerFailState.collectAsState()
    val registerUserExists by loginModel.registerUserExistsState.collectAsState()

    val context = LocalContext.current
    val dataStore = ApplicationPreferences(context)

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    FailDialog(
        stringResource(R.string.LoginFailed),
        stringResource(R.string.LoginFailedMessage), loginFail
    ) { loginModel.resetFails() }
    FailDialog(
        stringResource(R.string.RegistrationFailed),
        stringResource(R.string.UserServerError),
        registerFail
    ) { loginModel.resetFails() }
    FailDialog(
        stringResource(R.string.RegistrationFailed),
        stringResource(R.string.UserExists),
        registerUserExists
    ) { loginModel.resetFails() }

    LoginScaffold(drawerState = drawerState, sharedModel = sharedModel, focusManager = focusManager) { p ->
        Column(
            modifier = Modifier
                .consumedWindowInsets(p)
                .padding(p)
        ) {
            LinearProgressIndicator(
                Modifier
                    .fillMaxWidth()
                    .alpha(if (loading) 1F else 0F)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(top = 16.dp)
            ) {
                Row(Modifier.padding(8.dp)) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = stringResource(R.string.Username),
                        modifier = Modifier
                            .padding(start = 8.dp, top = 8.dp, end = 16.dp)
                            .size(26.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text(stringResource(R.string.Username)) },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        maxLines = 1,
                        trailingIcon = {
                            if (username.isNotEmpty()) {
                                IconButton(onClick = { username = "" }) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = stringResource(
                                            R.string.Clear
                                        )
                                    )
                                }
                            }
                        }
                    )
                }

                Row(Modifier.padding(8.dp)) {
                    Icon(
                        imageVector = Icons.Outlined.Password,
                        contentDescription = stringResource(R.string.Password),
                        modifier = Modifier
                            .padding(start = 8.dp, top = 8.dp, end = 16.dp)
                            .size(26.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text(stringResource(R.string.Password)) },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        maxLines = 1,
                        trailingIcon = {
                            if (password.isNotEmpty()) {
                                IconButton(onClick = { password = "" }) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = stringResource(
                                            R.string.Clear
                                        )
                                    )
                                }
                            }
                        }
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.End, modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    FilledTonalButton(
                        onClick = {
                            focusManager.clearFocus()
                            loginModel.register(
                                username,
                                password,
                                navController,
                                dataStore,
                                selectedItem
                            )
                        },
                        modifier = Modifier.padding(end = 8.dp, start = 8.dp),
                        enabled = password.isNotEmpty() && username.isNotEmpty()
                    ) {
                        Text(text = stringResource(R.string.CreateAccount))
                    }
                    Button(
                        onClick = {
                            focusManager.clearFocus()
                            loginModel.login(
                                username,
                                password,
                                navController,
                                dataStore,
                                selectedItem
                            )
                        }, modifier = Modifier.padding(end = 16.dp),
                        enabled = password.isNotEmpty() && username.isNotEmpty()
                    ) {
                        Text(stringResource(R.string.Login))
                    }
                }
            }
        }
    }
}

@Composable
fun FailDialog(title: String, message: String, state: Boolean, resetFails: () -> Unit) {
    if (state) {
        AlertDialog(
            onDismissRequest = { resetFails() },
            title = { Text(title) },
            text = { Text(message) },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.ErrorOutline,
                    contentDescription = stringResource(R.string.Error)
                )
            },
            confirmButton = {
                TextButton(onClick = { resetFails() }) {
                    Text(stringResource(R.string.Dismiss))
                }
            }
        )
    }
}