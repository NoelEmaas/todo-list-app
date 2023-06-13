package com.codearmy.todolistapp.presentation.screens.signin

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.codearmy.todolistapp.R
import com.codearmy.todolistapp.ui.theme.*
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun SignInScreen(navController: NavController) {

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MainBackground)) {

        Row(modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .padding(PaddingValues(top = 100.dp)),
            horizontalArrangement = Arrangement.Center){

            AppLogo()
            AppTitle()
        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(top = 50.dp)),
            horizontalAlignment = Alignment.CenterHorizontally) {

            VectorArt()
            AppDescription()
        }
    }

    SignInFacebookButton(navController)
}

@Composable
fun AppDescription() {
    Text(modifier = Modifier
        .width(260.dp),
        textAlign = TextAlign.Center ,
        fontFamily = archivoLight,
        fontSize = 14.sp,
        text = stringResource(R.string.app_description)
    )
}

@Composable
fun AppLogo() {
    Box(modifier = Modifier
        .shadow(10.dp, RoundedCornerShape(20.dp), true)
        .clip(RoundedCornerShape(20.dp))
        .background(Color.White)
        .size(120.dp),
        contentAlignment = Alignment.Center) {

        Image(
            painter = painterResource( id = R.drawable.signin_check_icon),
            contentDescription = "",
            modifier = Modifier.size(90.dp),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun AppTitle() {
    Text(modifier = Modifier.padding(PaddingValues(start = 20.dp, top = 20.dp)),
        text = buildAnnotatedString {
            append("Welcome to ")
            withStyle(style = SpanStyle(
                fontWeight = FontWeight.Bold,
                fontFamily = interBold,
                fontSize = 28.sp
            )
            ) {
                append("\nTodo List")
            }
        },
        fontWeight = FontWeight.Light,
        fontSize = 18.sp,
        fontFamily = interLight,
        color = DarkGray
    )
}

@Composable
fun SignInFacebookButton(navController: NavController) {

    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom) {

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp), contentAlignment = Alignment.Center) {

            Button(modifier = Modifier
                .shadow(12.dp, RoundedCornerShape(10.dp), true)
                .clip(RoundedCornerShape(10.dp))
                .width(300.dp)
                .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = DarkGray
                ),
                onClick = {}) {

                Text(text = "Continue with Facebook", fontFamily = interMedium)
            }

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp));

            FacebookAuthentication(
                onSignInFailed = {
                    Toast.makeText(context, "Sign In Unsuccessful", Toast.LENGTH_SHORT).show()
                },
                onSignedIn = {
                    Toast.makeText(context, "Sign In Successful", Toast.LENGTH_SHORT).show()
                    navController.navigate("home_screen")
                })
        }
    }
}

@Composable
fun FacebookAuthentication(
    onSignInFailed : (Exception) -> Unit,
    onSignedIn : () -> Unit
) {

    val scope = rememberCoroutineScope()

    AndroidView({ context ->
        LoginButton(context).apply {
            val callbackManager = CallbackManager.Factory.create()
            setPermissions("email", "public_profile")
            registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                    TODO("Not yet implemented")
                }

                override fun onError(error: FacebookException) {
                    onSignInFailed(error)
                }
                override fun onSuccess(result: LoginResult) {
                    scope.launch {
                        val token = result.accessToken.token
                        val credential = FacebookAuthProvider.getCredential(token)
                        val authResult = Firebase.auth.signInWithCredential(credential).await()
                        if(authResult.user != null){
                            onSignedIn()
                        } else {
                            onSignInFailed(RuntimeException("Could not sign in with firebase"))
                        }
                    }
                }
            })
        }
    },
        Modifier
            .alpha(0.0f)
            .width(300.dp)
            .height(50.dp))
}

@Composable
fun VectorArt() {
    Image(
        painter = painterResource( id = R.drawable.signin_vector_art),
        contentDescription = "",
        modifier = Modifier
            .width(280.dp)
            .height(230.dp),
    )
}