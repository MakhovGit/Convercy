package org.convert.convercy.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.convert.convercy.R
import org.convert.convercy.model.intent.IntentContract
import org.convert.convercy.model.screens.ScreenEvents
import org.convert.convercy.model.screens.ScreenStates
import org.convert.convercy.ui.theme.BigHeaderColor
import org.convert.convercy.ui.theme.CommonTextColor
import org.convert.convercy.ui.theme.LittleHeaderColor
import org.convert.convercy.ui.theme.ScreenBackgroundColor

@Composable
fun ExchangeScreen(
    intent: IntentContract<ScreenStates, ScreenEvents>,
    screenState: ScreenStates.ExchangeScreenState
) {
    Surface(
        color = ScreenBackgroundColor,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                color = BigHeaderColor,
                text = stringResource(id = R.string.main_header),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )
            ExchangeCard(intent, screenState)
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.exchange_rate_header),
                color = LittleHeaderColor,
                modifier = Modifier.padding(10.dp)
            )
            Text(
                text = screenState.convertString,
                color = CommonTextColor,
                fontSize = 20.sp,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}