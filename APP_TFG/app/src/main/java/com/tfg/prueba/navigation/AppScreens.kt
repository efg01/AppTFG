package com.tfg.prueba.navigation

sealed class AppScreens(val route: String) {
    object FirstScreen: AppScreens("first_screen")
    object SecondScreen: AppScreens("second_screen")
    object BoundinBoxes: AppScreens("bounding_boxes")
    object ChangeScreen: AppScreens("change_screen")
    object AddBB: AppScreens("add_bb")
}