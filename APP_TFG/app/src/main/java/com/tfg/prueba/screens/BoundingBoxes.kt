package com.tfg.prueba.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tfg.prueba.ApiClient
import kotlinx.coroutines.Dispatchers
import androidx.navigation.NavController
import com.tfg.prueba.BoundB
import com.tfg.prueba.R
import com.tfg.prueba.deleteBB
import com.tfg.prueba.navigation.AppScreens
import com.tfg.prueba.ui.theme.BlueE
import com.tfg.prueba.uploadBB
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.IOException

var position = 0
var bbState: List<BoundB> = arrayListOf()

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BoundingBox(navController: NavController) {
    llamaBoxes()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "MonApp") },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = BlueE),
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate(route = AppScreens.FirstScreen.route) },
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
            BBBodyContent(
                navController = navController,
                modifier = Modifier.padding(paddingValues)
            )
        }
    )
}


@Composable
fun llamaBoxes() {
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            val result: List<BoundB>? = withContext(Dispatchers.IO) {
                getBBApi()
            }
            result?.let {
                bbState = result
            }
        }
    }
}


@Composable
fun BBBodyContent(navController: NavController, modifier: Modifier = Modifier){

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        ElevatedButton(
            onClick = { navController.navigate(route = AppScreens.AddBB.route)},
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray,
                contentColor = Color.Black
            ),
            modifier = Modifier
                .padding(bottom = 20.dp)
                .fillMaxWidth()
        ) {
            Text("Añadir caja envolvente", style = TextStyle(fontSize = 20.sp))
        }

        LazyColumn {
            items(bbState.size){
                bbState[it]
                MyTextsBB(navController, bbState[it].location ?: "Loading...",
                    bbState[it].x_min ?: "Loading...",
                    bbState[it].y_min ?: "Loading...",
                    bbState[it].x_max ?: "Loading...",
                    bbState[it].y_max ?: "Loading...",
                    it)
                Spacer(modifier = Modifier.height(13.dp))
            }
        }
    }

}


suspend fun getBBApi(): List<BoundB>? {

    try {
        val response = ApiClient.apiService.getBB();
        return response
    }catch (e: IOException){
        e.printStackTrace()
    }catch (e: retrofit2.HttpException){
        e.printStackTrace()
    }catch (e: Exception){
        e.printStackTrace()
    }

    return null
}

@Composable
fun MyTextsBB(navController: NavController, label: String?,x1: String?,y1: String?,x2: String?,y2: String?, i: Int) {
    Column{
        TextLabelBB(navController, data = "Puntos de la estancia ", label= label, i)
        Column (
            modifier = Modifier
                .padding(start = 20.dp)
        ) {
        }
        MyTextBB(data = "Puntos mínimos: ", x= x1, y= y1)
        MyTextBB(data = "Puntos máximos: ", x= x2, y= y2)

    }

}

@Composable
fun MyTextBB(data: String, x: String?, y: String?) {
    Row {
        Text(
            text = data,
            style = TextStyle(fontSize = 20.sp),
            modifier = Modifier.padding(12.dp)
        )
        Text(text = "($x , $y)",
            style = TextStyle(fontSize = 20.sp),
            modifier = Modifier.padding(12.dp)
        )
    }

}

@Composable
fun TextLabelBB(navController: NavController, data: String, label: String?, i: Int) {

    if(label == null){
        Text(
            text = "No hay datos",
            style = TextStyle(fontSize = 16.sp),
            modifier = Modifier.padding(12.dp)
        )
    }else {

        Surface(
            color = BlueE,
            modifier = Modifier.padding(vertical = 2.dp, horizontal = 4.dp)
        ) {
            Row(modifier = Modifier.padding(24.dp)) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(text = " $label ", style = TextStyle(fontSize = 25.sp))

                    val idImg = when (label) {
                        "bathroom" -> R.drawable.bathroom
                        "bedroom" -> R.drawable.bedroom
                        "kitchen" -> R.drawable.kitchen
                        "office" -> R.drawable.office
                        else -> {
                            null
                        }
                    }

                    if (idImg != null) {
/*
                        Image(
                            painter = painterResource(id = idImg),
                            contentDescription = "imagen estancia",
                            Modifier.size(43.dp)
                        )*/

                    }
                }
                Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                    ElevatedButton(
                        onClick = { navController.navigate(route = AppScreens.ChangeScreen.route); position=i },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        modifier = Modifier.padding(vertical = 2.dp)
                    ) {
                        Text("Modificar", style = TextStyle(fontSize = 20.sp))
                    }

                    val coroutineScope = rememberCoroutineScope()

                    val (bbjson) = remember { mutableStateOf<JSONObject?>(null) }

                    val deleteBoundingB: () -> Unit = {
                        coroutineScope.launch {
                            deleteBB(label)
                        }
                    }

                    ElevatedButton(
                        onClick = { deleteBoundingB() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                    ) {
                        Text("Eliminar", style = TextStyle(fontSize = 20.sp))
                    }
                }
            }
            }

        }

}

@Preview
@Composable
fun holaBB(){
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