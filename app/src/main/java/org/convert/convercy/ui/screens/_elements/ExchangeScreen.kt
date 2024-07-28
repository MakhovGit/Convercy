package org.convert.convercy.ui.screens._elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import org.convert.convercy.R
import org.convert.convercy.ui.screens.main_screen.MainScreenContract
import org.convert.convercy.ui.theme.BigHeaderColor
import org.convert.convercy.ui.theme.CommonTextColor
import org.convert.convercy.ui.theme.LittleHeaderColor
import org.convert.convercy.ui.theme.ScreenBackgroundColor

@Composable
fun ExchangeScreen(
    screenState: MainScreenContract.State,
    exchangeCardOnClick: () -> Unit,
    onCurrencyAmountClick: (isReadOnlyMode: Boolean, currency: String) -> Unit,
    onCurrencyAmountValueChange: (value: String) -> Unit
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
            Text(
                color = BigHeaderColor,
                text = stringResource(id = R.string.exchange_screen_main_header),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = dimensionResource(id = R.dimen.exchange_screen_title_font_size).value.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.exchange_screen_title_padding))
            )
            ExchangeCard(
                screenState = screenState,
                onClick = exchangeCardOnClick,
                onCurrencyAmountClick = onCurrencyAmountClick,
                onCurrencyAmountValueChange = onCurrencyAmountValueChange
            )
            VerticalSpacer(height = dimensionResource(id = R.dimen.spacer_20))
            Text(
                text = stringResource(id = R.string.exchange_screen_exchange_rate_header),
                color = LittleHeaderColor,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.common_padding))
            )
            Text(
                text = screenState.currencyInfo.convertString,
                color = CommonTextColor,
                fontSize = dimensionResource(id = R.dimen.exchange_screen_convert_string_font_size).value.sp,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.common_padding))
            )
        }
    }
}