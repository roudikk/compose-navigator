package com.roudikk.composenavigator.ui.screens.details

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.roudikk.compose_navigator.NavOptions
import com.roudikk.compose_navigator.Screen
import com.roudikk.compose_navigator.findNavigator
import com.roudikk.composenavigator.AppPreview
import com.roudikk.composenavigator.MaterialSharedAxisTransitionX
import com.roudikk.composenavigator.ui.composables.AppTopAppBar
import com.roudikk.composenavigator.ui.screens.home.HomeScreen
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
class DetailsScreen(
    private val item: String
) : Screen {

    @Composable
    override fun Content(animatedVisibilityScope: AnimatedVisibilityScope) {
        DetailsContent(item = item)
    }
}

@Composable
private fun DetailsContent(item: String) {
    val navigator = findNavigator()

    Scaffold(
        topBar = {
            AppTopAppBar(
                title = "Details",
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navigator.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Item: $item",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.size(16.dp))

            Button(
                modifier = Modifier
                    .widthIn(min = 300.dp),
                onClick = {
                    val newItem = UUID.randomUUID().toString().split("-")[0]
                    navigator.navigate(
                        navigationNode = DetailsScreen(newItem),
                        navOptions = NavOptions(
                            navTransition = MaterialSharedAxisTransitionX
                        )
                    )
                }
            ) {
                Text(text = "New random item")
            }

            Button(
                modifier = Modifier
                    .widthIn(min = 300.dp),
                onClick = {
                    navigator.sendResult<HomeScreen>(item)
                    navigator.popToRoot()
                }
            ) {
                Text(text = "Send result back to home")
            }
        }
    }
}

@Preview
@Composable
private fun DetailsContentPreview() = AppPreview {
    DetailsContent(item = "Test Item")
}