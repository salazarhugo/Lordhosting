package com.salazar.lordhosting.server.ui.console

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.salazar.lordhosting.R
import kotlinx.coroutines.flow.filter

@Composable
fun ConsoleScreen(
    uiState: ConsoleUiState,
    onConsoleUIAction: (ConsoleUIAction) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Console",
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onConsoleUIAction(ConsoleUIAction.OnBackPressClick)
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding()),
        ) {
            Console(
                command = uiState.command,
                modifier = Modifier.fillMaxSize(),
                outputText = uiState.consoleOutput,
                onConsoleUIAction = onConsoleUIAction,
            )
        }
    }
}

@Composable
fun Console(
    command: String,
    modifier: Modifier = Modifier,
    outputText: List<String>,
    onConsoleUIAction: (ConsoleUIAction) -> Unit,
) {
    val state = rememberLazyListState()

    LaunchedEffect(outputText) {
        snapshotFlow { state.firstVisibleItemIndex }
            // Only scroll to the bottom if the first visible item is at index 0
            .filter { it == 0 }
            .collect {
                state.scrollToItem(0)
            }
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Black)
            .border(2.dp, Color.DarkGray, RoundedCornerShape(12.dp)),
    ) {
        LazyColumn(
            state = state,
            modifier = Modifier
                .weight(1f)
                .simpleVerticalScrollbar(state = state),
            reverseLayout = true,
        ) {
            items(outputText.reversed()) { text ->
                Text(text, Modifier.padding(4.dp))
            }

        }

        TextField(
            value = command,
            onValueChange = {
                onConsoleUIAction(ConsoleUIAction.OnCommandChange(it))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Type a command...")
            },
            leadingIcon = {
                Icon(Icons.Default.ChevronRight, contentDescription = null)
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(onSend = {
                onConsoleUIAction(ConsoleUIAction.OnSendCommandClick)
            })
        )
    }
}

@Composable
fun Modifier.simpleVerticalScrollbar(
    state: LazyListState,
    width: Dp = 8.dp
): Modifier {
    val targetAlpha = if (state.isScrollInProgress) 1f else 0f
    val duration = if (state.isScrollInProgress) 150 else 500

    val alpha by animateFloatAsState(
      targetValue = targetAlpha,
      animationSpec = tween(durationMillis = duration)
    )
    val color = MaterialTheme.colorScheme.onSurface

    return drawWithContent {
      drawContent()

      val firstVisibleElementIndex = state.layoutInfo.visibleItemsInfo.firstOrNull()?.index
      val needDrawScrollbar = state.isScrollInProgress || alpha > 0.0f

      // Draw scrollbar if scrolling or if the animation is still running and lazy column has content
      if (needDrawScrollbar && firstVisibleElementIndex != null) {
        val elementHeight = this.size.height / state.layoutInfo.totalItemsCount
        val scrollbarOffsetY = firstVisibleElementIndex * elementHeight
        val scrollbarHeight = state.layoutInfo.visibleItemsInfo.size * elementHeight

        drawRect(
            color = color,
            topLeft = Offset(this.size.width - width.toPx(), size.height - scrollbarHeight - scrollbarOffsetY),
            size = Size(width.toPx(), scrollbarHeight),
            alpha = alpha,
        )
      }
    }
  }