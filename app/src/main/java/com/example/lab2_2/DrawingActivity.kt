package com.example.lab2_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.lab2_2.ui.theme.Lab2_2Theme
data class Line(
    val start: Offset,
    val end: Offset,
    val color: Color = Color.Black,
    val strokeWidth: Dp = 10.dp
)
class DrawingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val buttonNames = arrayOf(
                stringResource(R.string.rect),
                stringResource(R.string.circle),
                stringResource(R.string.kitty),
                stringResource(R.string.surname),
//                stringResource(R.string.save)
            )
            val myView: MyGraphView? = MyGraphView(applicationContext)
            val viewRemember = remember {
                mutableStateOf(myView)
            }

            Lab2_2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val textMeasurer = rememberTextMeasurer()
                    Column(
                        Modifier.fillMaxSize()
                    ) {
                        MakeTopButtons(buttonNames,viewRemember.value)
                        CustomView(viewRemember.value)
                    }
                }
            }
        }
    }

}
    @Composable
    fun MakeTopButtons(buttonNames: Array<String>, myView: MyGraphView?) {
        var PrimitiveMenuState by remember { mutableStateOf(false) }
        var ColorMenuState by remember { mutableStateOf(false) }
        var ImageMenuState by remember { mutableStateOf(false) }

        Column {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
    //                .border(BorderStroke(2.dp, Color.Blue))
            ) {
                    Button(
//                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            PrimitiveMenuState = !PrimitiveMenuState
                        }
                    ){
                        Text(text = stringResource(R.string.primitive))
                    }
                    Button(
//                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            ColorMenuState = !ColorMenuState
                        }
                    ){
                        Text(text = stringResource(R.string.color))
                    }
                    Button(
                        onClick = {
                            ImageMenuState = !ImageMenuState
                        }
                    ){
                        Text(text = stringResource(R.string.image))
                    }
            }
                DropdownMenu(
                    expanded = ColorMenuState,
                    onDismissRequest = {ColorMenuState = false},
                    modifier = Modifier.fillMaxWidth()
                ){
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.black), color = Color.Magenta) },
                        onClick = {
                            myView?.setColor(android.graphics.Color.MAGENTA)
                            ColorMenuState = !ColorMenuState
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.green), color = Color.Green) },
                        onClick = {
                            myView?.setColor(android.graphics.Color.GREEN)
                            ColorMenuState = !ColorMenuState
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.red), color = Color.Red) },
                        onClick = {
                            myView?.setColor(android.graphics.Color.RED)
                            ColorMenuState = !ColorMenuState
                        }
                    )
                }
                    DropdownMenu(
                        expanded = ImageMenuState,
                        onDismissRequest = {ImageMenuState = false},
                        modifier = Modifier.fillMaxWidth()
                    ){
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.save)) },
                            onClick = {
                                myView?.onSaveClick()
                                ImageMenuState = !ImageMenuState
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.upload)) },
                            onClick = {
                                myView?.uploadImage()
                                ImageMenuState = !ImageMenuState
                            }
                        )
                    }

                    DropdownMenu(
                        expanded = PrimitiveMenuState,
                        onDismissRequest = { PrimitiveMenuState = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        buttonNames.forEach {
                            DropdownMenuItem(
//                                modifier = Modifier.fillMaxWidth(),
                                text = { Text(it)},
                                onClick = {
                                    myView!!.funcArray[buttonNames.lastIndexOf(it)]()
                                    PrimitiveMenuState = !PrimitiveMenuState
                                })
                        }

                    }
        }
    }

    @Composable
    fun CustomView(myView: MyGraphView?) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                myView!!
            },
        )
    }
