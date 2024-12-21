package com.chaitanyakhiratkar.myreceipts.navigation

import Register
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.chaitanyakhiratkar.myreceipts.constants.Routes
import com.chaitanyakhiratkar.myreceipts.constants.Stacks
import com.chaitanyakhiratkar.myreceipts.screens.Home
import com.chaitanyakhiratkar.myreceipts.screens.Login
import com.chaitanyakhiratkar.myreceipts.screens.OTP
import com.chaitanyakhiratkar.myreceipts.screens.StoreProfile

@Composable
fun MainNavigation(initStack: String) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = initStack, builder = {

        navigation(startDestination = Routes.LOGIN, route = Stacks.ON_BOARD) {
            composable(Routes.LOGIN) {
                Login(navController)
            }
            composable(Routes.REGISTER) {
                Register(navController)
            }
            composable(Routes.OTP) {
                OTP(navController){

                }
            }
        }

        navigation(startDestination = Routes.HOME, route = Stacks.MAIN) {
            composable(Routes.HOME) {
                Home(navController)
            }
            composable(Routes.PROFILE) {
                StoreProfile(navController)
            }
        }

    })
}