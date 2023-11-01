package com.cj.petchecker.frameworks.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cj.petchecker.home.ui.HomeView
import com.cj.petchecker.inspection.ui.InspectionMainView
import com.cj.petchecker.more.ui.MoreView
import com.cj.petchecker.statistics.ui.StatisticsView

const val HOME = "HOME"
const val INSPECTION = "INSPECTION"
const val STATISTICS = "STATISTICS"
const val MORE = "MORE"

sealed class BottomNavItem(
    val title: String, val icon: ImageVector, val screenRoute: String
) {
    object home: BottomNavItem("홈", Icons.Rounded.Home, HOME)
    object inspection: BottomNavItem("검사", Icons.Rounded.Search, INSPECTION)
    object statistics: BottomNavItem("통계", Icons.Rounded.History, STATISTICS)
    object more: BottomNavItem("더 보기", Icons.Rounded.MoreHoriz, MORE)
}

@Composable
fun NavigationGraph(navController: NavHostController){
    NavHost(navController = navController, startDestination = BottomNavItem.home.screenRoute){
        composable(BottomNavItem.home.screenRoute){
            HomeView()
        }

        composable(BottomNavItem.inspection.screenRoute){
            InspectionMainView()
        }

        composable(BottomNavItem.statistics.screenRoute){
            StatisticsView()
        }

        composable(BottomNavItem.more.screenRoute){
            MoreView()
        }
    }
}