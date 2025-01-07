package com.orbixstar.kmpapp.screens.list

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.orbixstar.kmpapp.data.ProductObject
import com.orbixstar.kmpapp.screens.LoadingScreenContent
import com.orbixstar.kmpapp.theme.Colors.primary
import orbixstar_coding_challenge.composeapp.generated.resources.Res
import orbixstar_coding_challenge.composeapp.generated.resources.edit
import orbixstar_coding_challenge.composeapp.generated.resources.location
import orbixstar_coding_challenge.composeapp.generated.resources.logo
import orbixstar_coding_challenge.composeapp.generated.resources.notification
import orbixstar_coding_challenge.composeapp.generated.resources.person
import orbixstar_coding_challenge.composeapp.generated.resources.search
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ListScreen(
    navigateToDetails: (objectId: Int) -> Unit
) {
    val viewModel = koinViewModel<ListViewModel>()
    val objects by viewModel.objects.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.systemBarsPadding()
    ) {
        PageHeader()

        AnimatedContent(objects.isNotEmpty()) { objectsAvailable ->
            if (objectsAvailable) {
                ObjectGrid(
                    objects = objects,
                    onObjectClick = navigateToDetails,
                )
            } else {
                LoadingScreenContent(Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun PageHeader() {
    Column {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(Res.drawable.logo),
                contentDescription = "Center Image",
                modifier = Modifier.size(28.dp)
            )
            Row(
                modifier = Modifier.padding(8.dp).fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Res.drawable.person),
                    contentDescription = "Start Image",
                    modifier = Modifier.size(24.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(Res.drawable.notification),
                        contentDescription = "End Image 1",
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Image(
                        painter = painterResource(Res.drawable.search),
                        contentDescription = "End Image 2",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
        HorizontalDivider(
            thickness = 1.dp, color = Color.LightGray, modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun ObjectGrid(
    objects: List<ProductObject>,
    onObjectClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(180.dp),
        modifier = modifier.fillMaxSize(),
    ) {
        items(objects, key = { it.id }) { obj ->
            ObjectFrame(
                obj = obj,
                onClick = { onObjectClick(obj.id) },
            )
        }
    }
}

@Composable
private fun ObjectFrame(
    obj: ProductObject, onClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(10.dp).clickable { onClick() }.fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
    ) {
        AsyncImage(
            model = obj.image,
            contentDescription = obj.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().height(120.dp)
        )
        HorizontalDivider(
            thickness = 1.dp, color = Color.LightGray, modifier = Modifier.fillMaxWidth()
        )

        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                obj.title,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                fontWeight = FontWeight.Bold
            )
            RowWithLogo(painterResource(Res.drawable.edit), obj.category)
            RowWithLogo(painterResource(Res.drawable.location), obj.location.toString())
            RowWithSubTexts("RS " + obj.price.toString(), obj.date.toString())
        }
    }
}

@Composable
private fun RowWithLogo(
    logo: Painter, text: String
) {
    Row(
        modifier = Modifier.padding(top = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        Image(
            painter = logo,
            contentDescription = text,
            modifier = Modifier.padding(end = 4.dp).size(12.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = text, style = MaterialTheme.typography.bodySmall, color = Color.Gray
        )
    }
}

@Composable
private fun RowWithSubTexts(
    text1: String, text2: String
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text1,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = primary,
            modifier = Modifier.padding(top = 4.dp)
        )

        Text(
            text = text2,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}
