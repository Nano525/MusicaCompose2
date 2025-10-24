package mx.edu.utez.musicacompose.ui.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.utez.musicacompose.data.model.Cancion

@Composable
fun CancionCard(Cancion: Cancion) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.padding(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = Cancion.nombre,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = Cancion.artista,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Row(
                modifier = Modifier.padding(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = Cancion.duracion,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = Cancion.genero,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

    }
}