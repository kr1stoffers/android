package com.example.lab2_2

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab2_2.ui.theme.Lab2_2Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val viewModel = ItemViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val lazyListState = rememberLazyListState()
            Lab2_2Theme  {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Gray,
                    contentColor = Color.Black
                ) {
                    Column(Modifier.fillMaxSize()) {
                        MakeInputPart(viewModel, lazyListState)
                        MakeList(viewModel, lazyListState)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MakeInputPart(model: ItemViewModel, lazyListState: LazyListState) {
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
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TextField(
            label = { Text("Программа") },
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
                label = { Text("Время показа") },
                value = showTime.toString(),
                onValueChange = { newText ->
                    showTime = newText
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            TextField(
                label = { Text("Канал") },
                value = tvChannel.toString(),
                onValueChange = { newText ->
                    tvChannel = newText
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }
        TextField(
            label = { Text("ФИО") },
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
                if(!model.isContains(TV(tvShow, showTime, tvChannel, fio))){
                    println("added $tvShow $showTime $tvChannel $fio")
                    model.addTVToHead(TV(tvShow, showTime, tvChannel, fio))
                    scope.launch {
                        lazyListState.scrollToItem(0)
                    }
                }else{
                    Toast.makeText(context, "В списке уже есть эта программа! 😔", Toast.LENGTH_LONG).show()
                }
                tvShow = ""
                showTime = ""
                tvChannel = ""
                fio = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add")
        }
    }
}
@Composable
fun MakeList(viewModel: ItemViewModel, lazyListState: LazyListState) {
    LazyColumn(
//        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        state = lazyListState
    ) {
        items(
            items = viewModel.TVListFlow.value,
            key = { tv -> tv.name },
            itemContent = { item ->
                ListColumn(item)
            }
        )
    }
}
@Composable
fun MakeAlertDialog(context: Context, dialogTitle: String, openDialog: MutableState<Boolean>) {
    var strValue = remember{ mutableStateOf("") }
    val strId = context.resources.getIdentifier(dialogTitle.replace(" ", ""), "string", context.packageName)
    try{
        if (strId != 0) strValue.value = context.getString(strId)
    } catch (e: Resources.NotFoundException) {
    }
    AlertDialog(
        onDismissRequest = { openDialog.value = false },
        title = { Text(text = dialogTitle) },
        text = { Text(text = strValue.value, fontSize = 14.sp) },
        confirmButton = {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { openDialog.value = false })
            { Text(text = "OK") }
        },

    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListColumn(item: TV){
    val context = LocalContext.current
    val openDialog = remember { mutableStateOf(false)}
    val langSelected = remember { mutableStateOf("") }
    if (openDialog.value)
        MakeAlertDialog(context, langSelected.value, openDialog)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .border(BorderStroke(2.dp, Color.DarkGray))
            .background(Color(0xFFd7d7d7))
            .combinedClickable (
                onClick = {
                    println("item = ${item.name}")
                    langSelected.value = item.name
//                    Toast.makeText(context, "item = ${item.name}", Toast.LENGTH_LONG).show()
                    openDialog.value = true
                }
            )
    ) {
        Text(
            text = item.name,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(10.dp),
        )
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ){
            Image(
                painter = painterResource(id = item.picture),
                contentDescription = "",
//            contentScale = ContentScale.FillWidth,
                modifier = Modifier.padding(bottom = 10.dp).clip(RoundedCornerShape(25.dp)).width(150.dp)

            )
            Column (
                horizontalAlignment = Alignment.Start,
//                verticalArrangement = Arrangement.spacedBy(50.dp)
                modifier = Modifier.width(80.dp),

            ){
                Text(
                    text = "Show time:",
                    fontSize = 10.sp,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = item.time.toString(),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom =  10.dp),
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = "Channel:",
                    fontSize = 10.sp,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = item.channel.toString(),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom =  10.dp),
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = "Author:",
                    fontSize = 10.sp,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = item.fio.toString(),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom =  10.dp),
                    fontStyle = FontStyle.Italic
                )

            }
        }


    }
}