package com.saitejajanjirala.gopodcast.ui.components

import android.text.Html
import android.text.TextUtils
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import kotlin.math.max

@Composable
fun HtmlText(
    htmlContent: String,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context ->
            TextView(context).apply {
                maxLines =3
                ellipsize = TextUtils.TruncateAt.END
            }
        },
        modifier = modifier,
        update = { textView ->
            textView.text = Html.fromHtml(htmlContent, Html.FROM_HTML_MODE_LEGACY)
        }
    )
}