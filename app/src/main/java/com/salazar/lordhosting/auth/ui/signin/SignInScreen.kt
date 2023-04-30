package com.salazar.lordhosting.auth.ui.signin

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.salazar.lordhosting.core.ui.components.ErrorMessage
import com.salazar.lordhosting.R
import com.salazar.lordhosting.core.ui.components.ButtonWithLoading

@Preview
@Composable
fun SignInScreenPreview() {
    SignInScreen(
        uiState = SignInUiState(isLoading = false),
        onSignInUIAction = {},
    )
}

@Composable
fun SignInScreen(
    uiState: SignInUiState,
    onSignInUIAction: (SignInUIAction) -> Unit,
) {
    val state = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }
    val image = when (isSystemInDarkTheme()) {
        true -> R.drawable.ldh
        false -> R.drawable.ldh
    }

    val density = LocalDensity.current
    AnimatedVisibility(
        visibleState = state,
        enter = slideInHorizontally(
            initialOffsetX = { with(density) { +400.dp.roundToPx() } }
        ) + fadeIn(
            initialAlpha = 0.3f
        ),
        exit = slideOutHorizontally() + fadeOut()
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(22.dp)
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    painter = rememberAsyncImagePainter(image),
                    contentDescription = null,
                    modifier = Modifier
                        .height(60.dp)
                )
                Spacer(modifier = Modifier.height(30.dp))
                AnimatedVisibility(
                    visible = uiState.withApiKey,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    SignInTextField(
                        value = uiState.apiKey,
                        placeholder = "API Key",
                        onChange = { onSignInUIAction(SignInUIAction.OnApiKeyChange(it)) },
                        enabled = !uiState.isLoading,
                    )
                }
                AnimatedVisibility(
                    visible = !uiState.withApiKey,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Column() {
                        EmailTextField(
                            email = uiState.email,
                            onEmailChanged = {
                                onSignInUIAction(SignInUIAction.OnEmailChange(it))
                            },
                            enabled = !uiState.isLoading,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        PasswordTextField(
                            password = uiState.password,
                            enabled = !uiState.isLoading,
                            onPasswordChanged = {
                                onSignInUIAction(SignInUIAction.OnPasswordChange(it))
                            },
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
                LoginButton(
                    isLoading = uiState.isLoading,
                    signInWithEmailPassword = {
                        onSignInUIAction(SignInUIAction.OnSignInClick)
                    },
                )
                ErrorMessage(
                    errorMessage = uiState.errorMessage,
                    paddingValues = PaddingValues(vertical = 8.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextDivider(dayString = "OR")
                val text =
                    if (uiState.withApiKey) "Sign in with password" else "Sign in with API Key"
                TextButton(onClick = {
                    onSignInUIAction(SignInUIAction.OnWithApiKeyChange(!uiState.withApiKey))
                }) {
                    Text(text = text)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            Footer(
//                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
fun SignInTextField(
    value: String,
    placeholder: String,
    onChange: (String) -> Unit,
    enabled: Boolean,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
) {
    TextField(
        value = value,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium),
        onValueChange = { onChange(it) },
        singleLine = true,
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
        keyboardActions = KeyboardActions(onSearch = {
        }),
        placeholder = {
            Text(text = placeholder)
        },
        enabled = enabled,
    )
}

@Composable
fun PasswordTextField(
    password: String,
    onPasswordChanged: (String) -> Unit,
    enabled: Boolean,
) {
    var passwordVisibility by remember { mutableStateOf(false) }
    TextField(
        value = password,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium),
        onValueChange = { onPasswordChanged(it) },
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
        keyboardActions = KeyboardActions(onSearch = {
        }),
        placeholder = { Text("Password") },
        enabled = enabled,
        trailingIcon = {
            val image = if (passwordVisibility)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            IconButton(onClick = {
                passwordVisibility = !passwordVisibility
            }) {
                Icon(imageVector = image, "")
            }
        }
    )
}

@Composable
fun EmailTextField(
    email: String,
    enabled: Boolean,
    onEmailChanged: (String) -> Unit,
) {
    SignInTextField(
        value = email,
        placeholder = "Username or Email",
        onChange = { onEmailChanged(it) },
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
    )
}

@Composable
fun LoginButton(
    isLoading: Boolean,
    signInWithEmailPassword: () -> Unit,
) {
    ButtonWithLoading(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(id = R.string.login).uppercase(),
        isLoading = isLoading,
        onClick = signInWithEmailPassword,
        shape = MaterialTheme.shapes.medium,
    )
}

@Composable
fun Footer(
    modifier: Modifier = Modifier,
) {
    Column() {
        Divider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                buildAnnotatedString {
                    append("Don't have an account? ")

                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        append("Sign up.")
                    }
                },
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
fun TextDivider(dayString: String) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .height(16.dp)
    ) {
        Line()
        Text(
            text = dayString,
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Line()
    }
}

@Composable
private fun RowScope.Line() {
    Divider(
        modifier = Modifier
            .weight(1f)
            .align(Alignment.CenterVertically),
        thickness = 2.dp,
    )
}