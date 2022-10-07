package nl.vanwijngaarden.koen.ui.settings.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.vanwijngaarden.koen.ui.util.Border
import nl.vanwijngaarden.koen.ui.util.border

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingSegmentedButtonsItem(
    title: String,
    desc: String,
    checked: Int,
    options: List<String>,
    onChange: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(top = 16.dp)
    ) {
        Column() {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp
            )
            Text(
                desc,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.alpha(0.8F)
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            options.mapIndexed { i, string ->
                val shape = when (i) {
                    0 -> RoundedCornerShape(topStartPercent = 50, bottomStartPercent = 50)
                    options.size - 1 -> RoundedCornerShape(
                        topEndPercent = 50,
                        bottomEndPercent = 50
                    )
                    else -> RoundedCornerShape(0)
                }

                val border = Border(1.dp, MaterialTheme.colorScheme.outline)
                val containerColor =
                    if (i == checked) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent
                val contentColor =
                    if (i == checked) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurface

                @Composable
                fun ButtonContent() {
                    if (i == checked) Icon(
                        imageVector = Icons.Outlined.Check,
                        contentDescription = "Checked",
                        tint = contentColor,
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .size(18.dp)
                    )
                    Text(text = string, color = contentColor)
                }

                CompositionLocalProvider(
                    LocalMinimumTouchTargetEnforcement provides false,
                ) {
                    when (i) {
                        0, options.size - 1 -> OutlinedButton(
                            onClick = { onChange(i) },
                            shape = shape,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = containerColor,
                                contentColor = contentColor
                            ),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            ButtonContent()
                        }
                        1 -> OutlinedButton(
                            onClick = { onChange(i) },
                            shape = shape,
                            border = null,
                            modifier = Modifier
                                .background(containerColor)
                                .border(top = border, bottom = border)
                                .weight(1f),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            ButtonContent()
                        }
                        else -> OutlinedButton(
                            onClick = { onChange(i) },
                            shape = shape,
                            modifier = Modifier
                                .background(containerColor)
                                .border(
                                    top = border,
                                    bottom = border,
                                    start = border
                                )
                                .weight(1f),
                            border = null,
                            contentPadding = PaddingValues(0.dp),
                        ) {
                            ButtonContent()
                        }
                    }
                }


            }
        }
    }
}