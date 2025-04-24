package com.tfg.prueba.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tfg.prueba.BoundB
import com.tfg.prueba.modifyBB
import com.tfg.prueba.navigation.AppScreens
import com.tfg.prueba.ui.theme.BlueE
import com.tfg.prueba.uploadBB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeScreen(navController: NavController) {
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
            DataBB(
                navController = navController
            )
        }
    )
}

@Composable
fun DataBB(navController: NavController, modifier: Modifier = Modifier){

    val bbState = remember { mutableStateOf<List<BoundB>?>(null) }

    LaunchedEffect(Unit) {
        while (true) {

            val result: List<BoundB>? = withContext(Dispatchers.IO) {
                getBBApi()
            }

            result?.let {
                bbState.value = it
            }
        }
    }

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        bbState.value?.get(position)
        ChangeBodyContent(navController = navController, location = bbState.value?.get(position)?.location,
            x1 = bbState.value?.get(position)?.x_min,
            y1 = bbState.value?.get(position)?.y_min, x2 = bbState.value?.get(position)?.x_max,
            y2 = bbState.value?.get(position)?.y_max)

        Spacer(modifier = Modifier.height(13.dp))

    }

}


@Composable
fun ChangeBodyContent(navController: NavController, location: String?,x1: String?,y1: String?,x2: String?,y2: String?){


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "$location",
            style = TextStyle(fontSize = 25.sp),
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            textAlign = TextAlign.Center
        )

        addInfo(location,x1,y1,x2,y2)

        OutlinedButton(
            onClick = {
                // llamar a la función para que añada los valores
                navController.navigate(route = AppScreens.BoundinBoxes.route)
            },
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray,
                contentColor = Color.Black
            ),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Modificar",
                style = TextStyle(fontSize = 25.sp)
            )
        }
    }
}

@Composable
fun addInfo(location: String?,x1: String?,y1: String?,x2: String?,y2: String?){
    var x_min by remember { mutableStateOf("") }
    var y_min by remember { mutableStateOf("") }
    var x_max by remember { mutableStateOf("") }
    var y_max by remember { mutableStateOf("") }

    Text(text = "Puntos mínimos",
        style = TextStyle(fontSize = 20.sp),
        modifier = Modifier.fillMaxWidth().padding(12.dp),
        textAlign = TextAlign.Center
    )

    OutlinedTextField (
        value = x_min,
        onValueChange = { x_min = it },
        label = {
            Text("Punto mínimo x",
                style = TextStyle(fontSize = 15.sp))
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        singleLine = true,
        textStyle = TextStyle(fontSize = 16.sp)
    )

    OutlinedTextField (
        value = y_min,
        onValueChange = { y_min = it },
        label = {
            Text("Punto mínimo y",
                style = TextStyle(fontSize = 15.sp))
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        singleLine = true,
        textStyle = TextStyle(fontSize = 16.sp)
    )


    Text(text = "Puntos máximos",
        style = TextStyle(fontSize = 20.sp),
        modifier = Modifier.fillMaxWidth().padding(12.dp),
        textAlign = TextAlign.Center
    )

    OutlinedTextField (
        value = x_max,
        onValueChange = { x_max = it },
        label = {
            Text("Punto máximo x",
                style = TextStyle(fontSize = 15.sp))
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        singleLine = true,
        textStyle = TextStyle(fontSize = 16.sp)
    )

    OutlinedTextField (
        value = y_max,
        onValueChange = { y_max = it },
        label = {
            Text("Punto máximo y",
                style = TextStyle(fontSize = 15.sp))
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        singleLine = true,
        textStyle = TextStyle(fontSize = 16.sp)
    )

}
