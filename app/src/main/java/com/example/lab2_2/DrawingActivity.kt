package com.example.lab2_2

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.example.lab2_2.ui.theme.Lab2_2Theme

class DrawingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val drawingObjects = remember { mutableStateListOf("") } //список для рисования
            val buttonNames = arrayOf("Rect", "Circle", "Image") //массив с названиями кнопок
            Lab2_2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(Modifier.fillMaxSize()) { //колонка с интерфейсом
//вызываем метод для создания кнопок, ему передаем названия кнопок и список для рисования,
//в который при нажатии на кнопки записываются данные
                        MakeTopButtons(buttonNames,drawingObjects)
                        Canvas(modifier = Modifier.fillMaxSize()) { //сам канвас, на котором рисуем
                            val canvasQuadrantSize = size / 2F //делим размер канваса на 2
                            val canvasWidth = size.width //получаем ширину канваса
                            drawingObjects.forEach { //проходим по элементам списка для рисования
                                if (it.contains("Rect")) //если это прямоугольник
                                    drawRect( //то рисуем его
                                        color = Color.Magenta, //цвет рисования
                                        size = canvasQuadrantSize //размер
                                    )
                                if (it.contains("Circle")) //если это круг
                                    drawCircle( //то рисуем его
                                        Color.Red, //цвет рисования
                                        radius = canvasWidth / 4f //и радиус
                                    )
                                if (it.contains("Image")) { //если это изображение
                                    val mBitmapFromSdcard = //то считываем с сд карты файл
                                        BitmapFactory.decodeFile("/data/user/0/com.example.lab2_2/files/").asImageBitmap()
                                    drawImage( //и выводим его на канвас
                                        image = mBitmapFromSdcard,
                                        topLeft = Offset(x = 0f, y = 0f) //координаты верхнего
                                    ) //левого угла для картинки
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
//метод для создания кнопок
@Composable
fun MakeTopButtons(buttonNames: Array<String>, drawingObjects: SnapshotStateList<String>) {
    Row( //ряд для создания кнопок
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .border(BorderStroke(2.dp, Color.Blue))
    ) {
        buttonNames.forEach { //цикл по названиям кнопок
            Button(onClick = { //создаем кнопку и описываем обработчик нажатия на нее
//удаляем из списка для рисования объект с названием кнопки, если он там был
                drawingObjects.remove(it)
                drawingObjects.add(it) //и снова добавляем, это нужно для правильного
            }) { //расположения объектов на холсте, в порядке нажатия
                Text(text = it) //текст кнопки
            }
        }
    }
}
