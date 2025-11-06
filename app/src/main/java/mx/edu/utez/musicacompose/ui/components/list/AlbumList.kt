package mx.edu.utez.musicacompose.ui.components.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mx.edu.utez.musicacompose.data.model.AlbumConCanciones
import mx.edu.utez.musicacompose.ui.cards.AlbumCard

@Composable
fun AlbumList(lista: List<AlbumConCanciones>, x: (AlbumConCanciones) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items = lista, key = { it.album.id }) { albumConCanciones ->
            AlbumCard(albumConCanciones.album) { x(albumConCanciones) }
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}
