package com.example.lab2_2

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.Serializable

data class TV(val name: String, val time:String, val channel: String, val fio:String, var picture: String = R.drawable.no_picture.toString() ):Serializable
class ItemViewModel : ViewModel() {
    private var TVList = mutableStateListOf(
        TV("Akame ga Kill", "14.30","Ani", "Takahiro", R.drawable.akame.toString()),
        TV("One Piece", "8.30","Jump", "Eiichiro Oda", R.drawable.one_piece.toString()),
        TV("JoJo", "9.20","Netflix", "Hirohiko Araki", R.drawable.jojo.toString()),
        TV("Majo no Takkyuubin", "10.00","Ghibli", "Hayao Miyazaki", R.drawable.kiki.toString()),
        TV("Howl no Ugoku Shiro", "11.00","Ghibli", "Hayao Miyazaki", R.drawable.howl.toString()),
        TV("Shingeki no Kyojin", "12.45", "Bessatsu", "Hajime Isayama", R.drawable.titans.toString()),
        TV("ReZero", "13.10", "White Fox", "Tappei Nagatsuki", R.drawable.rezero.toString()),
        TV("Sen to Chihiro no Kamikakushi", "13.50", "Ghibli", "Hayao Miyazaki", R.drawable.ghost.toString()),
        TV("Dororo", "18.00", "Shounen Sunday", "Osamu Tezuka", R.drawable.dororo.toString())
    )
    private val _TVListFlow = MutableStateFlow(TVList)
    val TVListFlow: StateFlow<List<TV>> get() = _TVListFlow
    fun clearList(){
        TVList.clear()
    }
    fun addTVToHead(tv: TV) {
        TVList.add(0, tv)
    }
    fun addTVToEnd(tv: TV) {
        TVList.add(tv)
    }
    fun isContains(tv: TV): Boolean {
        return TVList.contains(tv)
    }
    fun removeItem(item: TV) {
        val index = TVList.indexOf(item)
        TVList.remove(TVList[index])
    }
    fun homeTV(){
        clearList()
        TVList.add(TV("Akame ga Kill", "14.30","Ani", "Takahiro", R.drawable.akame.toString()))
        TVList.add(TV("One Piece", "8.30","Jump", "Eiichiro Oda", R.drawable.one_piece.toString()))
        TVList.add(TV("JoJo", "9.20","Netflix", "Hirohiko Araki", R.drawable.jojo.toString()))
        TVList.add(TV("Majo no Takkyuubin", "10.00","Ghibli", "Hayao Miyazaki", R.drawable.kiki.toString()))
        TVList.add(TV("Howl no Ugoku Shiro", "11.00","Ghibli", "Hayao Miyazaki", R.drawable.howl.toString()))
        TVList.add(TV("Shingeki no Kyojin", "12.45", "Bessatsu", "Hajime Isayama", R.drawable.titans.toString()))
        TVList.add(TV("ReZero", "13.10", "White Fox", "Tappei Nagatsuki", R.drawable.rezero.toString()))
        TVList.add(TV("Sen to Chihiro no Kamikakushi", "13.50", "Ghibli", "Hayao Miyazaki", R.drawable.ghost.toString()))
        TVList.add(TV("Dororo", "18.00", "Shounen Sunday", "Osamu Tezuka", R.drawable.dororo.toString()))
    }
    fun changeImage(index: Int, value: String){
        TVList[index] = TVList[index].copy(picture = value)
    }
}