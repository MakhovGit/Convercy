package org.convert.convercy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.convert.convercy.R
import org.convert.convercy.model.intent.IntentContract
import org.convert.convercy.model.screens.ScreenEvents
import org.convert.convercy.model.screens.ScreenStates
import org.convert.convercy.ui.theme.ExchangeCardColor
import org.convert.convercy.ui.theme.LittleHeaderColor

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CurrencyAmount(
    intent: IntentContract<ScreenStates, ScreenEvents>,
    screenState:ScreenStates.ExchangeScreenState,
    isReadOnlyMode: Boolean = false
) {
    val listWeight = 1.0F
    val fieldWeight = 1.0F
    var isExpanded by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(
        horizontalArrangement = Arrangement.spacedBy(40.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = isExpanded.not() },
            modifier = Modifier.weight(listWeight)
        ) {
            TextField(
                value = if (isReadOnlyMode.not()) screenState.fromCurrencyType
                    else screenState.toCurrencyType,
                onValueChange = {},
                readOnly = true,
                leadingIcon = {
                    Icon(painter = painterResource(
                        id = if(isReadOnlyMode.not()) screenState.fromCurrencyFlagResId
                         else screenState.toCurrencyFlagResId
                    ),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
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
                val currencyList = if (isReadOnlyMode.not()) screenState.fromCurrencyList
                    else screenState.toCurrencyList
                currencyList.forEach { item ->
                    DropdownMenuItem(
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = item.second),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp),
                                tint = Color.Unspecified
                            )
                        },
                        text = { Text(text = item.first) },
                        onClick = {
                            isExpanded = false
                            intent.handleEvent(
                                if (isReadOnlyMode.not())
                                   ScreenEvents.ExchangeScreenEvents.FromCurrencyTypeChangedEvent(item.first)
                                else
                                   ScreenEvents.ExchangeScreenEvents.ToCurrencyTypeChangedEvent(item.first)
                                )
                            },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
        TextField(
            value = if (isReadOnlyMode.not()) screenState.fromCurrencyAmount
                      else screenState.toCurrencyAmount,
            onValueChange = {
                if (isReadOnlyMode.not()) {
                    intent.handleEvent(ScreenEvents.ExchangeScreenEvents.FromCurrencyAmountChangedEvent(it))
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
            shape = RoundedCornerShape(10),
            modifier = Modifier
                .weight(fieldWeight)
                .clickable(enabled = isReadOnlyMode.not()) {}
        )
    }
}

@Composable
fun ExchangeCard(
    intent: IntentContract<ScreenStates, ScreenEvents>,
    screenState: ScreenStates.ExchangeScreenState
) {
    Surface(
        color = ExchangeCardColor,
        shape = RoundedCornerShape(10),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(id = R.string.amount_header),
                color = LittleHeaderColor,
            )
            Spacer(modifier = Modifier.height(10.dp))
            CurrencyAmount(
                intent = intent,
                screenState = screenState
            )
            Spacer(modifier = Modifier.height(30.dp))
            Icon(painter = painterResource(id = R.drawable.ic_divider),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(50.dp)
                    .clickable {
                        intent.handleEvent(ScreenEvents.ExchangeScreenEvents.SwapCurrenciesEvent)
                    }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.convertible_amount_header),
                color = LittleHeaderColor,
            )
            Spacer(modifier = Modifier.height(10.dp))
            CurrencyAmount(
                intent = intent,
                screenState = screenState,
                isReadOnlyMode = true
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}