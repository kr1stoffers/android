package com.example.lab2_2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.lab2_2.ui.theme.Lab2_2Theme

class InputActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab2_2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MakeInputPart()
                }
            }
        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MakeInputPart() {
        var tvShow by remember {
            mutableStateOf("")
        }
        var showTime by remember{
            mutableStateOf("")
        }
        var tvChannel by remember {
            mutableStateOf("")
        }
        var fio by remember {
            mutableStateOf("")
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TextField(
                label = { Text(stringResource(R.string.TVtitle)) },
                value = tvShow.toString(),
                onValueChange = { newText ->
                    tvShow = newText
                },
                modifier = Modifier.fillMaxWidth(),
//            singleLine = true,
                maxLines = 2
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                TextField(
                    label = { Text(stringResource(R.string.time)) },
                    value = showTime.toString(),
                    onValueChange = { newText ->
                        showTime = newText
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                TextField(
                    label = { Text(stringResource(R.string.channel)) },
                    value = tvChannel.toString(),
                    onValueChange = { newText ->
                        tvChannel = newText
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }
            TextField(
                label = { Text(stringResource(R.string.author)) },
                value = fio.toString(),
                onValueChange = { newText ->
                    fio = newText
                },
                modifier = Modifier.fillMaxWidth(),
//            singleLine = true,
                maxLines = 2
            )
            Button(
                onClick = {
//                        println("added $tvShow $showTime $tvChannel $fio")
                    val newShow = TV(tvShow, showTime, tvChannel, fio)
                    val intent = Intent()
                    intent.putExtra("newItem", newShow)
                    setResult(RESULT_OK, intent);
                    tvShow = ""
                    showTime = ""
                    tvChannel = ""
                    fio = ""
                    finish()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.addTV))
            }
        }
    }
}