package com.example.pizza_pro_2.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.domain.shared.SharedEvent
import com.example.pizza_pro_2.domain.shared.SharedState
import com.example.pizza_pro_2.navigation.graphs.BottomNavGraph
import com.example.pizza_pro_2.presentation.components.InfoDialog
import com.example.pizza_pro_2.presentation.screens.Screen

@Composable
fun HomeScreen(
    sharedState: SharedState,
    onSharedEvent: (SharedEvent) -> Unit
) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopBar(navController) {
                onSharedEvent(SharedEvent.DialogVisibilityChanged(true))
            }
        },
        bottomBar = {
            BottomBar(navController)
        },
        content = { innerPadding ->
            if (sharedState.isDialogVisible) {
                InfoDialog(
                    titleId = R.string.pizza_info,
                    textId = R.string.pizza_card_info,
                    onDismiss = {
                        onSharedEvent(SharedEvent.DialogVisibilityChanged(false))
                    },
                    dismissButton = R.string.cancel
                )
            }

            Box(modifier = Modifier.padding(innerPadding)) {
                BottomNavGraph(navController, sharedState, onSharedEvent)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController, onDialogShow: () -> Unit) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val header = stringResource(
        when (currentDestination?.route) {
            Screen.Cart.route -> R.string.cart
            Screen.Feedback.route -> R.string.feedback
            Screen.Settings.route -> R.string.settings
            Screen.Account.route -> R.string.account
            Screen.History.route -> R.string.history
            Screen.AboutApp.route -> R.string.about_app
            else -> R.string.shop
        }
    )
    val isChild = arrayOf(Screen.Account.route, Screen.History.route, Screen.AboutApp.route)
        .contains(currentDestination?.route)

    TopAppBar(
        title = { Text(text = header) },
        navigationIcon = {
            if (isChild) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = onDialogShow) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = stringResource(R.string.pizza_info)
                )
            }
        }
    )
}

@Composable
fun BottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val screens = listOf(
        BottomBarScreen.Shop,
        BottomBarScreen.Cart,
        BottomBarScreen.Feedback,
        BottomBarScreen.Settings
    )

    NavigationBar {
        screens.forEach { screen ->
            AddItem(screen, currentDestination, navController)
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

    NavigationBarItem(
        label = { if (!isSelected) Text(text = screen.title) },
        icon = {
            Icon(
                painter = painterResource(if (isSelected) screen.selectedIconId else screen.unselectedIconId),
                contentDescription = stringResource(R.string.app_bar_icon)
            )
        },
        selected = isSelected,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    )
}