package com.example.profiles.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.profiles.DeliveryAppTheme
import com.example.profiles.R


@Composable
fun InfoItem(header: String, info: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier.width(130.dp)) {
        Column() {
            Text(
                text = header,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = info,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun ProfileItem(
    header: String, info: String, @DrawableRes image: Int, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(top = 16.dp, start = 8.dp)
    ) {
        Text(text = header, style = MaterialTheme.typography.titleLarge)
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = "No promotion yet",
                modifier = modifier
            )
            Text(text = info)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onHistoryClicked: () -> Unit,
    onAddressesClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(32.dp, 32.dp, 0.dp, 0.dp))
                .background(MaterialTheme.colorScheme.background)
        ) {
            ProfileItem(
                header = "My promotion",
                info = "No promotion yet",
                image = R.drawable.ic_promo_empty_96,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
            )
            ProfileItem(
                header = "Missions",
                info = "No missions yet",
                image = R.drawable.ic_star_96,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                InfoItem(
                    header = stringResource(R.string.history_of_orders),
                    info = " orders",
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onHistoryClicked() }
                )
                InfoItem(
                    header = stringResource(R.string.delivery_addresses),
                    info = " address",
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onAddressesClicked() }
                )
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
private fun ProfilePreview() {
    DeliveryAppTheme {
        Surface(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize(), color = Color.Black
        ) {
            ProfileScreen(
                {}, {}
            )
        }
    }
}