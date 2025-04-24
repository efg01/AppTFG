package com.tfg.prueba.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tfg.prueba.screens.AddScreen
import com.tfg.prueba.screens.BoundingBox
import com.tfg.prueba.screens.ChangeScreen
import com.tfg.prueba.screens.FirstScreen
import com.tfg.prueba.screens.SecondScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.FirstScreen.route){
        composable(route = AppScreens.FirstScreen.route){
            FirstScreen(navController)
        }
        composable(route = AppScreens.SecondScreen.route){
            SecondScreen(navController)
        }
        composable(route = AppScreens.BoundinBoxes.route){
            BoundingBox(navController)
        }
        composable(route = AppScreens.ChangeScreen.route){
            ChangeScreen(navController)
        }
        composable(route = AppScreens.AddBB.route){
            AddScreen(navController)
        }
    }
}