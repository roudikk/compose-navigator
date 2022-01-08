package com.roudikk.composenavigator.ui.screens.nested

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.roudikk.compose_navigator.NavOptions
import com.roudikk.compose_navigator.NavTransition
import com.roudikk.compose_navigator.Screen
import com.roudikk.compose_navigator.animation.navigationFadeIn
import com.roudikk.compose_navigator.animation.navigationFadeOut
import com.roudikk.compose_navigator.animation.navigationSlideInVertically
import com.roudikk.compose_navigator.animation.navigationSlideOutVertically
import com.roudikk.compose_navigator.findNavigator
import com.roudikk.composenavigator.AppPreview
import kotlinx.parcelize.Parcelize

@Parcelize
class NestedScreen(
    private val count: Int
) : Screen {

    override val key: String
        get() = "${super.key}_$count"

    @Composable
    override fun Content(animatedVisibilityScope: AnimatedVisibilityScope) {
        NestedContent(count = count)
    }
}

@Composable
private fun NestedContent(count: Int) {
    val navigator = findNavigator()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        IconButton(
            enabled = navigator.canGoBack(),
            onClick = {
                navigator.popBackStack()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "back"
            )
        }

        Surface(
            tonalElevation = 10.dp,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.size(100.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "$count",
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }

        IconButton(
            onClick = {
                navigator.navigate(
                    NestedScreen(count + 1),
                    navOptions = NavOptions(
                        navTransition = NavTransition(
                            enter = navigationSlideInVertically { it / 2 }
                                    + navigationFadeIn(),
                            exit = navigationSlideOutVertically { -it / 2 }
                                    + navigationFadeOut(),
                            popEnter = navigationSlideInVertically { -it / 2 }
                                    + navigationFadeIn(),
                            popExit = navigationSlideOutVertically { it / 2 }
                                    + navigationFadeOut()
                        )
                    )
                )
            }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add"
            )
        }
    }
}

@Preview(
    device = Devices.PIXEL_3
)
@Composable
private fun NestedContentPreview() = AppPreview {
    NestedContent(count = 4)
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_3
)
@Composable
private fun NestedContentPreviewDark() = AppPreview {
    NestedContent(count = 4)
}