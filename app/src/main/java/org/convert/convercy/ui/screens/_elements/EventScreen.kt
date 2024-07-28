package org.convert.convercy.ui.screens._elements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import org.convert.convercy.R
import org.convert.convercy.ui.theme.CommonTextColor
import org.convert.convercy.ui.theme.ErrorScreenButtonColor
import org.convert.convercy.ui.theme.NoInternetIconColor
import org.convert.convercy.ui.theme.ScreenBackgroundColor

@Composable
fun EventScreen(
    onClick: () -> Unit
) {
    Surface(
        color = ScreenBackgroundColor,
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.common_padding))
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            VerticalSpacer(height = dimensionResource(id = R.dimen.event_screen_spacer))
            Icon(
                painter = painterResource(R.drawable.ic_no_internet),
                contentDescription = stringResource(id = R.string.event_screen_icon_content_description),
                tint = NoInternetIconColor,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(dimensionResource(id = R.dimen.event_screen_icon_size))
            )
            VerticalSpacer(height = dimensionResource(id = R.dimen.spacer_30))
            Text(
                text = stringResource(id = R.string.event_screen_error_message),
                textAlign = TextAlign.Center,
                fontSize = dimensionResource(id = R.dimen.event_screen_event_title_font_size).value.sp,
                modifier = Modifier.fillMaxWidth()
            )
            VerticalSpacer(height = dimensionResource(id = R.dimen.spacer_10))
            Text(
                text = stringResource(id = R.string.event_screen_error_hint),
                textAlign = TextAlign.Center,
                fontSize = dimensionResource(id = R.dimen.event_screen_event_description_font_size).value.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(bottom = dimensionResource(id = R.dimen.event_screen_button_padding_bottom))
            ) {
                OutlinedButton(
                    onClick = onClick,
                    shape = RoundedCornerShape(size = dimensionResource(id = R.dimen.button_rounded_corner)),
                    border = BorderStroke(
                        width = dimensionResource(id = R.dimen.button_border_width),
                        color = ErrorScreenButtonColor
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.event_screen_reconnect_button),
                        textAlign = TextAlign.Center,
                        fontSize = dimensionResource(id = R.dimen.button_font_size).value.sp,
                        color = CommonTextColor,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}