package mx.edu.utez.musicacompose.ui.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import mx.edu.utez.musicacompose.data.model.Album

@Composable
fun AlbumCard(usr: Album, x: (Album) -> Unit){
    Column {
        Card (modifier = Modifier.clickable{x(usr)}) {
            Row (verticalAlignment = Alignment.CenterVertically){
                Image(
                    painter = painterResource(usr.imagen),
                    contentDescription = "Imagen de album"
                )
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text=usr.nombre)
                    Text(text=usr.artista)
                }
            }
        }
    }
}