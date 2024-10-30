package com.example.rickmortyapp.models

data class ResponseEpisode(
    val info: Info,
    val results: List<Episode>
)
