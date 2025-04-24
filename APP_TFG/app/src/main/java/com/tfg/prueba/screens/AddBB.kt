package com.tfg.prueba.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.tfg.prueba.ApiService
import com.tfg.prueba.BoundB
import com.tfg.prueba.Data
import com.tfg.prueba.TokenDto
import com.tfg.prueba.navigation.AppScreens
import com.tfg.prueba.ui.theme.BlueE
import com.tfg.prueba.uploadBB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response
import java.util.Date

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(navController: NavController) {
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
            AddBB(
                navController = navController
            )
        }
    )
}

@Composable
fun AddBB(navController: NavController, modifier: Modifier = Modifier){

    val bbState = remember { mutableStateOf<List<BoundB>?>(null) }

    LaunchedEffect(Unit) {


        val result: List<BoundB>? = withContext(Dispatchers.IO) {
            getBBApi()
        }

        result?.let {
            bbState.value = it
        }

    }

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        AddBodyContent(navController = navController, location = null,
            x1 = null,
            y1 = null,
            x2 = null,
            y2 = null)

        Spacer(modifier = Modifier.height(13.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBodyContent(navController: NavController, location: String?,x1: String?,y1: String?,x2: String?,y2: String?){
    var resultado by remember {mutableStateOf("")}
    var loc by remember { mutableStateOf("") }
    var x_mi by remember { mutableStateOf("") }
    var y_mi by remember { mutableStateOf("") }
    var x_ma by remember { mutableStateOf("") }
    var y_ma by remember { mutableStateOf("") }

    var muestraMensaje by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField (
            value = loc,
            onValueChange = { loc = it },
            label = {
                Text("Localización",
                    style = TextStyle(fontSize = 15.sp))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp)
                .padding(10.dp),
            singleLine = true,
            textStyle = TextStyle(fontSize = 16.sp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
            keyboardActions = KeyboardActions(onSearch = {}),
            interactionSource = MutableInteractionSource(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = BlueE,
                unfocusedBorderColor = Gray,
                focusedLabelColor = BlueE,
                unfocusedLabelColor = Gray,
                cursorColor = BlueE),
        )


        Text(text = "Puntos mínimos",
            style = TextStyle(fontSize = 20.sp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            textAlign = TextAlign.Center
        )

        OutlinedTextField (
            value = x_mi,
            onValueChange = { x_mi = it },
            label = {
                Text("Punto mínimo x",
                    style = TextStyle(fontSize = 15.sp))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            singleLine = true,
            textStyle = TextStyle(fontSize = 16.sp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            keyboardActions = KeyboardActions(onSearch = {}),
            interactionSource = MutableInteractionSource(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = BlueE,
                unfocusedBorderColor = Gray,
                focusedLabelColor = BlueE,
                unfocusedLabelColor = Gray,
                cursorColor = BlueE),
        )

        OutlinedTextField (
            value = y_mi,
            onValueChange = { y_mi = it },
            label = {
                Text("Punto mínimo y",
                    style = TextStyle(fontSize = 15.sp))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            singleLine = true,
            textStyle = TextStyle(fontSize = 16.sp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            keyboardActions = KeyboardActions(onSearch = {}),
            interactionSource = MutableInteractionSource(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = BlueE,
                unfocusedBorderColor = Gray,
                focusedLabelColor = BlueE,
                unfocusedLabelColor = Gray,
                cursorColor = BlueE),
        )


        Text(text = "Puntos máximos",
            style = TextStyle(fontSize = 20.sp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            textAlign = TextAlign.Center
        )

        OutlinedTextField (
            value = x_ma,
            onValueChange = { x_ma = it },
            label = {
                Text("Punto máximo x",
                    style = TextStyle(fontSize = 15.sp))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            singleLine = true,
            textStyle = TextStyle(fontSize = 16.sp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            keyboardActions = KeyboardActions(onSearch = {}),
            interactionSource = MutableInteractionSource(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = BlueE,
                unfocusedBorderColor = Gray,
                focusedLabelColor = BlueE,
                unfocusedLabelColor = Gray,
                cursorColor = BlueE)

        )

        OutlinedTextField (
            value = y_ma,
            onValueChange = { y_ma = it },
            label = {
                Text("Punto máximo y",
                    style = TextStyle(fontSize = 15.sp))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            keyboardActions = KeyboardActions(onSearch = {}),
            interactionSource = MutableInteractionSource(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = BlueE,
                unfocusedBorderColor = Gray,
                focusedLabelColor = BlueE,
                unfocusedLabelColor = Gray,
                cursorColor = BlueE),
            textStyle = TextStyle(fontSize = 16.sp)
        )

        val coroutineScope = rememberCoroutineScope()

        val createBB: (bbjson: BoundB) -> Unit = {

            coroutineScope.launch {
                uploadBB(it)
            }
        }

        if(muestraMensaje){

            val currentTimestamp = System.currentTimeMillis()
            val bb_new = BoundB(currentTimestamp.toString(),loc, x_mi, x_ma, y_mi, y_ma)

            val accion = AlertDialogBB(bb_new.toString())

            if(accion==1){ // significa que acepta los datos
                createBB(bb_new)
                muestraMensaje = false
                navController.navigate(route = AppScreens.BoundinBoxes.route)
            }else if(accion == 2){
                muestraMensaje = false
            }

        }else{
            OutlinedButton(
                onClick = {
                    muestraMensaje = true

                },
                enabled = loc.length >= 2 ,
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Añadir",
                    style = TextStyle(fontSize = 20.sp)
                )
            }
        }

        Text(
            text = resultado,
            modifier = Modifier.padding(10.dp)
        )
    }
}

var pulsado = false

@Composable
fun AlertDialogBB(json: String): Int {
    val openDialog = remember { mutableStateOf(true) }
    val accion = remember { mutableStateOf(0) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "¿Desea añadir estos datos?")
            },
            text = {
                Text(text = json.toString())
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        accion.value = 1
                        openDialog.value = false
                    }
                ) {
                    Text("Si")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        pulsado = true
                        accion.value = 2
                        openDialog.value = false
                    }
                ) {
                    Text("No")
                }
            }
        )
    }
    return accion.value
}
