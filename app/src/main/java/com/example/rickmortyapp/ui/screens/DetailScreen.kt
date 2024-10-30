package com.example.rickmortyapp.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.ImageSource
import com.example.rickmortyapp.R
import com.example.rickmortyapp.models.Character
import com.example.rickmortyapp.models.Episode
import com.example.rickmortyapp.models.Location
import com.example.rickmortyapp.models.Origin
import com.example.rickmortyapp.services.CharacterService
import com.example.rickmortyapp.utils.Trip_origin
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun DetailScreen(paddingValues: PaddingValues, id: Int){
    val scrollState = rememberScrollState()
    var character by remember {
        mutableStateOf(Character(
            created = "",
            episode = listOf(),
            gender = "",
            id = 0,
            image = "",
            location = Location("",""),
            name = "",
            origin = Origin("",""),
            species = "",
            status = "",
            type = "",
            url = ""
        ))
    }
    
    var episodes by remember {
        mutableStateOf(listOf<Episode>())
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        scope.launch {
            val BASE_URL = "https://rickandmortyapi.com/api/"
            val characterService = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CharacterService::class.java)
            val response = characterService.getCharacterById(id)
            val responseEpisode = characterService.getEpisodes()
            isLoading = false
            character = response
            episodes = responseEpisode.results
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
        Box{
            Image(painter = painterResource(id = R.drawable.wallpaper), contentDescription = null)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Row(
                    modifier = Modifier.padding(top = 15.dp, start = 15.dp, end = 15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = character.image,
                        contentDescription = character.name,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape),
                        placeholder = painterResource(id = R.drawable.image_not_found),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = character.name,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = character.status,
                            color = when (character.status) {
                                "Alive" -> Color.Green
                                "Dead" -> Color.Red
                                else -> Color.Black
                            },
                            fontSize = 20.sp,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 15.dp, end = 15.dp)
                ) {
                    Text(
                        text = "Details",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row {
                        Icon(Icons.Default.Person, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Specie: ${character.species}", fontSize = 18.sp, color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Icon(Icons.Default.Info, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Gender: ${character.gender}", fontSize = 18.sp, color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Icon(Icons.Default.DateRange, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Created: ${character.created}", fontSize = 18.sp, color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "Locations",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Column(modifier = Modifier.padding(top = 8.dp)) {
                        Row {
                            Icon(Icons.Default.Place, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Current: ${character.location.name}", fontSize = 18.sp, color = Color.White)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            Icon(Trip_origin, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Origin: ${character.origin.name}", fontSize = 18.sp, color = Color.White)
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = "Episodes",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Column(
                        modifier = Modifier
                            .verticalScroll(scrollState)
                            .padding(top = 10.dp, start = 5.dp, bottom = 15.dp)
                    ) {
                        for (e in episodes) {
                            for (ep in character.episode) {
                                if (e.url == ep) {
                                    Text(
                                        text = e.name,
                                        fontSize = 18.sp,
                                        color = Color.White,
                                        modifier = Modifier.padding(bottom = 5.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}