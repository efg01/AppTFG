package com.tfg.prueba.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tfg.prueba.BoundB
import com.tfg.prueba.navigation.AppScreens
import com.tfg.prueba.ui.theme.BlueE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "MonApp")
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = BlueE)
            )
        },
        content = { paddingValues ->
            BodyContent(navController)
        }
    )
}

@Composable
fun BodyContent(navController: NavController){


    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedButton(
            onClick = {
                navController.navigate(route = AppScreens.SecondScreen.route)
            },
            contentPadding = PaddingValues(horizontal = 50.dp, vertical = 50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray,
                contentColor = Color.Black
            ),
            modifier = Modifier.padding(bottom = 20.dp)
        ) {
            Text(text = "Iniciar monitorización",
                style = TextStyle(fontSize = 25.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center
                )
        }
        ElevatedButton(
            onClick = {

                navController.navigate(route = AppScreens.BoundinBoxes.route)
            },
            contentPadding = PaddingValues(horizontal = 45.dp, vertical = 45.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray,
                contentColor = Color.Black
            ),
            modifier = Modifier.padding(bottom = 20.dp)
        ) {
            Text(text = "Modificar/añadir valores",
                style = TextStyle(fontSize = 25.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center
            )
        }
    }
}
