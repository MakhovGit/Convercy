package org.convert.convercy.ui.screens._elements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import org.convert.convercy.R
import org.convert.convercy.ui.screens.main_screen.MainScreenContract
import org.convert.convercy.ui.theme.ExchangeCardColor
import org.convert.convercy.utils.MAX_WEIGHT

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyAmount(
    screenState: MainScreenContract.State,
    onClick: (isReadOnlyMode: Boolean, currency: String) -> Unit,
    onValueChange: (value: String) -> Unit,
    isReadOnlyMode: Boolean = false
) {
    val listWeight = Float.MAX_WEIGHT
    val fieldWeight = Float.MAX_WEIGHT
    var isExpanded by remember { mutableStateOf(false) }
    var textFieldValue by remember { mutableStateOf(screenState.currencyInfo.fromCurrencyAmount) }
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.currency_amount_row_arrangement_space)),
        modifier = Modifier.fillMaxWidth()
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = isExpanded.not() },
            modifier = Modifier.weight(listWeight)
        ) {
            TextField(
                value = if (isReadOnlyMode.not()) screenState.currencyInfo.fromCurrencyType
                else screenState.currencyInfo.toCurrencyType,
                onValueChange = {},
                readOnly = true,
                leadingIcon = {
                    Icon(
                        painter = painterResource(
                            id = if (isReadOnlyMode.not()) screenState.currencyInfo.fromCurrencyFlagResId
                            else screenState.currencyInfo.toCurrencyFlagResId
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(dimensionResource(id = R.dimen.currency_amount_leading_icon_size)),
                        tint = Color.Unspecified
                    )
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = isExpanded
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = ExchangeCardColor,
                    unfocusedContainerColor = ExchangeCardColor,
                    focusedIndicatorColor = ExchangeCardColor,
                    unfocusedIndicatorColor = ExchangeCardColor
                ),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
                modifier = Modifier
                    .menuAnchor()
                    .onFocusChanged { keyboardController?.hide() }
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
                modifier = Modifier.background(color = ExchangeCardColor)
            ) {
                val currencyList =
                    if (isReadOnlyMode.not()) screenState.currencyInfo.fromCurrencyList
                    else screenState.currencyInfo.toCurrencyList
                currencyList.forEach { item ->
                    DropdownMenuItem(
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = item.second),
                                contentDescription = null,
                                modifier = Modifier.size(dimensionResource(id = R.dimen.currency_amount_leading_icon_size)),
                                tint = Color.Unspecified
                            )
                        },
                        text = { Text(text = item.first) },
                        onClick = {
                            isExpanded = false
                            onClick(isReadOnlyMode, item.first)
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
        TextField(
            value = if (isReadOnlyMode.not()) textFieldValue
            else screenState.currencyInfo.toCurrencyAmount,
            onValueChange = {
                textFieldValue = it
                if (isReadOnlyMode.not()) {
                    onValueChange(it)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            readOnly = isReadOnlyMode,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = ExchangeCardColor,
                unfocusedIndicatorColor = ExchangeCardColor
            ),
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_rounded_corner)),
            modifier = Modifier
                .weight(fieldWeight)
                .clickable(enabled = isReadOnlyMode.not()) {}
        )
    }
}