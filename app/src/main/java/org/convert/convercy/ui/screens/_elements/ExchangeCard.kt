package org.convert.convercy.ui.screens._elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import org.convert.convercy.R
import org.convert.convercy.ui.screens.main_screen.MainScreenContract
import org.convert.convercy.ui.theme.ExchangeCardColor
import org.convert.convercy.ui.theme.LittleHeaderColor

@Composable
fun ExchangeCard(
    screenState: MainScreenContract.State,
    onClick: () -> Unit,
    onCurrencyAmountClick: (isReadOnlyMode: Boolean, currency: String) -> Unit,
    onCurrencyAmountValueChange: (value: String) -> Unit
) {
    Surface(
        color = ExchangeCardColor,
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.card_rounded_corner)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.common_padding))
    ) {
        Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.common_padding))) {
            VerticalSpacer(height = dimensionResource(id = R.dimen.spacer_10))
            Text(
                text = stringResource(id = R.string.exchange_screen_amount_header),
                color = LittleHeaderColor,
            )
            VerticalSpacer(height = dimensionResource(id = R.dimen.spacer_10))
            CurrencyAmount(
                screenState = screenState,
                onClick = onCurrencyAmountClick,
                onValueChange = onCurrencyAmountValueChange
            )
            VerticalSpacer(height = dimensionResource(id = R.dimen.spacer_30))
            Icon(painter = painterResource(id = R.drawable.ic_divider),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(dimensionResource(id = R.dimen.exchange_card_icon_size))
                    .clickable {
                        onClick()
                    }
            )
            VerticalSpacer(height = dimensionResource(id = R.dimen.spacer_20))
            Text(
                text = stringResource(id = R.string.exchange_screen_convertible_amount_header),
                color = LittleHeaderColor,
            )
            VerticalSpacer(height = dimensionResource(id = R.dimen.spacer_10))
            CurrencyAmount(
                screenState = screenState,
                onClick = onCurrencyAmountClick,
                onValueChange = onCurrencyAmountValueChange,
                isReadOnlyMode = true
            )
            VerticalSpacer(height = dimensionResource(id = R.dimen.spacer_10))
        }
    }
}