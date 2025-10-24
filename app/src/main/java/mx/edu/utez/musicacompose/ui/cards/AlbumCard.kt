package mx.edu.utez.musicacompose.ui.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import mx.edu.utez.musicacompose.data.model.Album

@Composable
fun AlbumCard(usr: Album, x: (Album) -> Unit){
    Column {
        Card (modifier = Modifier.clickable{x(usr)}.fillMaxWidth())  {
            Row (verticalAlignment = Alignment.CenterVertically){
                Image(
                    modifier = Modifier.size(160.dp),
                    painter = painterResource(usr.imagen),
                    contentDescription = "Imagen de album"
                )
                Spacer(modifier = Modifier.width(16.dp))
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