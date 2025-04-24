package com.tfg.prueba.screens

import android.annotation.SuppressLint
import android.text.style.BackgroundColorSpan
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tfg.prueba.ApiClient
import com.tfg.prueba.Data
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.tfg.prueba.R
import com.tfg.prueba.ui.theme.BlueE
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Timestamp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SecondScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "MonApp") },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = BlueE),
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.padding(start = 12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Arrow back"
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            SecondBodyContent(
                navController = navController,
                modifier = Modifier.padding(paddingValues)
            )
        }
    )
}

@Composable
fun SecondBodyContent(navController: NavController, modifier: Modifier = Modifier){

    val dataState = remember { mutableStateOf<Data?>(null) }

    LaunchedEffect(Unit) {
        while (true) {

            val result: Data? = withContext(Dispatchers.IO) {
                getDataApi()
            }

            result?.let {
                dataState.value = it
            }
        }
    }



    Column(
        modifier = modifier.padding(16.dp)
    ) {
        MyTexts(dataState.value?.timestamp?: "Loading...",dataState.value?.x ?: "Loading...", dataState.value?.y ?: "Loading...", dataState.value?.label ?: "Loading...")
        Spacer(modifier = Modifier.height(13.dp))
    }

}


suspend fun getDataApi(): Data? {
    val response = ApiClient.apiService.getData();
    if (response == null) {
        return null
    }
    return response
}

@Composable
fun MyTexts(timestamp: String?,x: String?,y: String?,label: String?) {
    Column{
        MyText(data = "Hora de recogida: $timestamp")
        MyText(data = "Punto de la baliza actual:")
        Column (
            modifier = Modifier
                .padding(start = 20.dp)
        ) {
            MyText("Valor x: $x")
            MyText("Valor y: $y")
        }
        MyText(data = "Estancia correspondiente actual: ")
        TextLabel(data = "$label")
    }

}

@Composable
fun MyText(data: String?) {
    Text(
        text = data ?: "Loading...",
        style = TextStyle(fontSize = 20.sp),
        modifier = Modifier.padding(12.dp)
    )
}

@Composable
fun TextLabel(data: String?) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = data ?: "Loading...",
            style = TextStyle(fontSize = 20.sp),
            modifier = Modifier.padding(12.dp)
        )
    }

    val idImg = when (data) {
        "bathroom" -> R.drawable.bathroom
        "bedroom" -> R.drawable.bedroom
        "kitchen" -> R.drawable.kitchen
        "office" -> R.drawable.office
        else -> {null}
    }

    if(idImg != null) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = idImg),
                contentDescription = "imagen estancia"
            )
        }
    }

}

@Preview
@Composable
fun hola(){
    Column{
        MyText(data = "Hora de recogida: hola")
        MyText(data = "Punto de la baliza actual:")
        Column (
            modifier = Modifier
                .padding(start = 20.dp)
        ) {
            MyText("Valor x: 0.0")
            MyText("Valor y: 0.0")
        }
        MyText(data = "Estancia correspondiente actual: ")
        TextLabel(data = "a")

    }
}