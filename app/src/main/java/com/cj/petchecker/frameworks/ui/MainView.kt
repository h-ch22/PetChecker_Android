package com.cj.petchecker.frameworks.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cj.petchecker.frameworks.models.BottomNavItem
import com.cj.petchecker.frameworks.models.NavigationGraph
import com.cj.petchecker.ui.theme.PetCheckerColorPalette
import com.cj.petchecker.ui.theme.PetCheckerTheme
import com.cj.petchecker.ui.theme.accent
import com.cj.petchecker.ui.theme.gray
import com.cj.petchecker.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(){
    val navItems = listOf(
        BottomNavItem.home,
        BottomNavItem.inspection,
        BottomNavItem.statistics,
        BottomNavItem.more
    )

    val selectedIndex = remember {
        mutableStateOf(0)
    }

    val navController = rememberNavController()

    PetCheckerTheme {
        Scaffold(
            bottomBar = {
                NavigationBar(containerColor = PetCheckerColorPalette.current.background, contentColor = accent) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    navItems.forEachIndexed { index, bottomNavItem ->
                        NavigationBarItem(selected = index == selectedIndex.value, onClick = {
                            selectedIndex.value = index
                            navController.navigate(bottomNavItem.screenRoute){
                                navController.graph.startDestinationRoute?.let{
                                    popUpTo(it){
                                        saveState = true
                                    }

                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }, icon = {
                            Icon(imageVector = bottomNavItem.icon, contentDescription = null)
                        }, colors = NavigationBarItemDefaults.colors(
                            selectedTextColor = accent,
                            selectedIconColor = white,
                            indicatorColor = accent.copy(alpha = 0.75F),
                            unselectedIconColor = gray,
                            unselectedTextColor = gray
                        ), label = {
                            Text(text = bottomNavItem.title)
                        }, alwaysShowLabel = false)
                    }
                }
            }
        ) {
            Box(modifier = Modifier.padding(it)){
                NavigationGraph(navController = navController)
            }
        }
    }
}

@Preview
@Composable
fun MainViewPreview(){
    MainView()
}