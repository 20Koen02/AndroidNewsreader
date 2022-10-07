package nl.vanwijngaarden.koen.ui.components

import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import nl.vanwijngaarden.koen.datastore.ApplicationPreferences

@Composable
fun HtmlText(html: String, modifier: Modifier = Modifier, linkTextColor: Int) {
    val context = LocalContext.current
    val customTextView = remember {
        TextView(context)
    }
    val dataStore = ApplicationPreferences(context)
    val savedTheme by dataStore.getTheme.collectAsState(0)
    val darkTheme = when (savedTheme) {
        0 -> isSystemInDarkTheme()
        1 -> false
        2 -> true
        else -> false
    }
    AndroidView(
        modifier = modifier,
        factory = { customTextView },
        update = {
            it.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
            it.textSize = 16F
            it.setTextColor(if (darkTheme) Color.White.hashCode() else Color.Black.hashCode())
            it.setLinkTextColor(linkTextColor)
            it.setLineSpacing(0f, 1.3F)
            it.movementMethod = LinkMovementMethod.getInstance()
        },
    )
}