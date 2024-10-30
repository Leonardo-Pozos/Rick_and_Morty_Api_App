package com.example.rickmortyapp.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.rickmortyapp.R
import com.example.rickmortyapp.models.ApiResponse
import com.example.rickmortyapp.models.Character
import com.example.rickmortyapp.services.CharacterService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun HomeScreen(paddingValues: PaddingValues, navController: NavController){
    var characters by remember {
        mutableStateOf(listOf<Character>())
    }
    var isLoading by remember{
        mutableStateOf(true)
    }
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true){
        scope.launch {
            val BASE_URL = "https://rickandmortyapi.com/api/"
            val characterService = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CharacterService::class.java)
            try {
                val response = characterService.getApiResult()
                isLoading = false
                characters = response.results
                Log.i("Responsess", response.toString())
            } catch (e: Exception) {
                Log.e("Errorr", e.toString())
            }
        }
    }
    if(isLoading){
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator()
        }
    }else{
        Column (
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ){
            Text(text = "Rick and Morty", modifier = Modifier.fillMaxWidth().padding(paddingValues), textAlign = TextAlign.Center, fontSize = 30.sp)
            LazyColumn{
                items(characters) { character ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp) // Ajuste de altura para un diseño más compacto
                            .padding(8.dp)
                            .clickable {
                                navController.navigate("detail/${character.id}")
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                        ) {
                            AsyncImage(
                                model = character.image,
                                contentDescription = character.name,
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(8.dp)), // Bordes redondeados para mejorar la estética
                                placeholder = painterResource(id = R.drawable.image_not_found),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(16.dp)) // Espacio entre la imagen y la columna de texto
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(start = 8.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = character.name,
                                    color = Color.Black,
                                    fontSize = 20.sp
                                )
                                Text(
                                    text = character.species,
                                    color = Color.Gray,
                                    fontSize = 15.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}