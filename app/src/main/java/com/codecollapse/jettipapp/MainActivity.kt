package com.codecollapse.jettipapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codecollapse.jettipapp.components.InputField
import com.codecollapse.jettipapp.ui.theme.JetTipAppTheme
import com.codecollapse.jettipapp.widgets.RoundIconButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MainContent()
                //TopHeader()
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    JetTipAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxHeight(),
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }
    }
}


@Preview(name = "TopHeaderPreview")
@Composable
fun TopHeader(totalPerPerson: Double = 134.0) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(12.dp)
            .clip(shape = CircleShape.copy(all = CornerSize(12.dp))), color = Color(0xFFABD9FF)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val total = "%.2f".format(totalPerPerson)
            Text(
                text = "Total Per Person",
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "$$total",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview(name = "MainContentPreview")
@Composable
fun MainContent() {
    BillForm() { billAmount ->

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(modifier: Modifier = Modifier, onValChange: (String) -> Unit = {}) {
    val totalBillState = remember {
        mutableStateOf("")
    }
    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }

    val sliderPositionState = remember {
        mutableStateOf(0f)
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    Column() {
        TopHeader()
        Surface(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(corner = CornerSize(8.dp)),
            border = BorderStroke(width = 1.dp, color = Color.LightGray)
        ) {
            Column(
                modifier = Modifier.padding(6.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                val numberOfPeople = remember {
                    mutableStateOf(1)
                }

                InputField(
                    valueState = totalBillState,
                    labelId = "Enter Bill",
                    enabled = true,
                    isSingleLine = true,
                    onAction = KeyboardActions {
                        if (validState.not()) return@KeyboardActions
                        onValChange(totalBillState.value.trim())
                        keyboardController?.hide()
                    }
                )

                //  if (validState) {
                Row(modifier = Modifier.padding(3.dp), horizontalArrangement = Arrangement.Start) {
                    Text(
                        text = "Split", modifier = Modifier.align(
                            alignment = Alignment.CenterVertically
                        )
                    )
                    Spacer(modifier = Modifier.width(120.dp))
                    Row(
                        modifier = Modifier.padding(horizontal = 3.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        RoundIconButton(imageVector = Icons.Default.Remove, onClick = {
                            if (numberOfPeople.value > 1) {
                                numberOfPeople.value--
                            }
                        })
                        Text(
                            text = numberOfPeople.value.toString(), modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(start = 9.dp, end = 9.dp)
                        )
                        RoundIconButton(imageVector = Icons.Default.Add, onClick = {
                            numberOfPeople.value++
                        })
                    }
                }

                //Tip Row
                Row(modifier = Modifier.padding(horizontal = 3.dp, vertical = 12.dp)) {
                    Text(
                        text = "Tip",
                        modifier = Modifier.align(alignment = Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(200.dp))
                    Text(
                        text = "$33.00",
                        modifier = Modifier.align(alignment = Alignment.CenterVertically)
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "33%")
                    Spacer(modifier = Modifier.height(14.dp))
                    Slider(
                        value = sliderPositionState.value,
                        onValueChange = { newVal ->
                            sliderPositionState.value = newVal
                        },
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        steps = 5,
                        onValueChangeFinished = {})
                }
                /* } else {
                     Box() {}
                 }*/
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetTipAppTheme {
        MyApp {
            // TopHeader()
        }
    }
}