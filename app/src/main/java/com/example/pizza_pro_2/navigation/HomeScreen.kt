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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.example.pizza_pro_2.domain.shared.SharedFormEvent
import com.example.pizza_pro_2.domain.shared.SharedFormState
import com.example.pizza_pro_2.navigation.graphs.BottomNavGraph
import com.example.pizza_pro_2.presentation.components.InfoDialog
import com.example.pizza_pro_2.presentation.screens.Screen

@Composable
fun HomeScreen(sharedState: SharedFormState, onSharedEvent: (SharedFormEvent) -> Unit) {
    var isVisible by rememberSaveable { mutableStateOf(false) }

    val navController = rememberNavController()

    Scaffold(
        topBar = { TopBar(navController = navController, onDialogShow = { isVisible = it }) },
        bottomBar = { BottomBar(navController = navController) },
        content = { innerPadding ->
            if (isVisible) {
                InfoDialog(
                    title = stringResource(id = R.string.pizza_info),
                    text = stringResource(id = R.string.pizza_card_info),
                    onDismiss = { isVisible = it },
                    dismissButton = R.string.cancel
                )
            }

            Box(modifier = Modifier.padding(innerPadding)) {
                BottomNavGraph(
                    navController = navController,
                    sharedState = sharedState,
                    onSharedEvent = onSharedEvent
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController, onDialogShow: (Boolean) -> Unit) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val header = when (currentDestination?.route) {
        Screen.Shop.route -> stringResource(id = R.string.shop)
        Screen.Cart.route -> stringResource(id = R.string.cart)
        Screen.Feedback.route -> stringResource(id = R.string.feedback)
        Screen.Settings.route -> stringResource(id = R.string.settings)
        Screen.Account.route -> stringResource(id = R.string.account)
        Screen.History.route -> stringResource(id = R.string.history)
        Screen.AboutApp.route -> stringResource(id = R.string.about_app)
        else -> ""
    }
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
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = { onDialogShow(true) }) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = stringResource(id = R.string.pizza_info)
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
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
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
                painter = painterResource(id = if (isSelected) screen.selectedIconId else screen.unselectedIconId),
                contentDescription = stringResource(id = R.string.app_bar_icon)
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