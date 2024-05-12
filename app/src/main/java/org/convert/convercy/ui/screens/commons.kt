package org.convert.convercy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.convert.convercy.R
import org.convert.convercy.ui.theme.ExchangeCardColor
import org.convert.convercy.ui.theme.LittleHeaderColor

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CurrencyAmount(items: List<String>) {
    val defaultValue = "0.1"
    val listWeight = 1.0F
    val fieldWeight = 1.0F
    var selectedText by remember { mutableStateOf(items.first()) }
    var isExpanded by remember { mutableStateOf(false) }
    var currentAmount by remember { mutableStateOf(defaultValue) }
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = isExpanded.not() },
            modifier = Modifier.weight(listWeight)
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
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
                items.forEachIndexed { index, text ->
                    DropdownMenuItem(
                        text = { Text(text = text) },
                        onClick = {
                            selectedText = items[index]
                            isExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
        TextField(
            value = currentAmount,
            onValueChange = { currentAmount = it},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = ExchangeCardColor,
                unfocusedIndicatorColor = ExchangeCardColor
            ),
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
            shape = RoundedCornerShape(10),
            modifier = Modifier.weight(fieldWeight)
        )
    }
}

@Composable
fun ExchangeCard(items: List<String>) {
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
            CurrencyAmount(items = items)
            Spacer(modifier = Modifier.height(20.dp))
            Divider()
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(id = R.string.convertible_amount_header),
                color = LittleHeaderColor,
            )
            Spacer(modifier = Modifier.height(10.dp))
            CurrencyAmount(items = items)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}