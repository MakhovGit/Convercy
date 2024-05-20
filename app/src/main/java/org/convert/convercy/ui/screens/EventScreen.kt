package org.convert.convercy.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.convert.convercy.R
import org.convert.convercy.model.intent.IntentContract
import org.convert.convercy.model.screens.ScreenEvents
import org.convert.convercy.model.screens.ScreenStates
import org.convert.convercy.ui.theme.CommonTextColor
import org.convert.convercy.ui.theme.ErrorScreenButtonColor
import org.convert.convercy.ui.theme.NoInternetIconColor
import org.convert.convercy.ui.theme.ScreenBackgroundColor

@Composable
fun EventScreen(intent: IntentContract<ScreenStates, ScreenEvents>) {
    Surface(
        color = ScreenBackgroundColor,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(150.dp))
            Icon(
                painter = painterResource(R.drawable.ic_no_internet),
                contentDescription = "Error icon",
                tint = NoInternetIconColor,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(200.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = stringResource(id = R.string.error_message),
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(id = R.string.error_hint),
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier.fillMaxHeight()
            ) {
                OutlinedButton(
                    onClick = {
                        intent.handleEvent(ScreenEvents.EventScreenEvents.ReconnectEvent)
                    },
                    shape = RoundedCornerShape(size = 10.dp),
                    border = BorderStroke(width = 1.dp, color = ErrorScreenButtonColor),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.reconnect_button),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        color = CommonTextColor,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}