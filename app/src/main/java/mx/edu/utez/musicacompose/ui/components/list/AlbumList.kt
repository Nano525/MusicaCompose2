package mx.edu.utez.musicacompose.ui.components.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.edu.utez.musicacompose.R
import mx.edu.utez.musicacompose.data.model.Album
import mx.edu.utez.musicacompose.ui.cards.AlbumCard
import mx.edu.utez.musicacompose.ui.theme.MusicaComposeTheme

@Composable
fun AlbumList(lista: List<Album>, x: (Album) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        //Iterar la lista en Java es con foreach
        items(items = lista, key = {it.id} ) {Album ->
            AlbumCard(Album) { x(Album) }
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}
