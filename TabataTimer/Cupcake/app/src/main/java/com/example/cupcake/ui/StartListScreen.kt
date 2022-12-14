package com.example.cupcake.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cupcake.R
import com.example.cupcake.database.Timer

@Composable
fun StartListScreen(
    timers: List<Timer>,
    onAddButtonClicked: () -> Unit = {},
    onEditButtonClicked: (id: Int) -> Unit = {},
    onDeleteButtonClicked: (timer: Timer) -> Unit = {},
    onViewButtonClicked: (id: Int) -> Unit = {},
    onSettingsButtonClicked: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic, fontSize = 45.sp, color = Color(0xFFD81B60))) {
                    append("I")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic, fontSize = 45.sp)) {
                    append("nterval ")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic, fontSize = 45.sp,  color = Color(0xFFD81B60))) {
                    append("T")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic, fontSize = 45.sp)) {
                    append("imer")
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        timers.forEach() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(BorderStroke(1.dp, Color(0xFFD81B60)), shape = RoundedCornerShape(8.dp))
                    .height(60.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 5.dp).width(200.dp),
                    text = it.timerName,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                IconButton(
                    onClick = { onViewButtonClicked(it.timerId.toInt()) },
                    modifier = Modifier
                        .padding(5.dp) // margin
                        .clip(CircleShape)
                        .background(Color(0xFFEC407A))
                        .size(40.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_play_arrow_24),
                        contentDescription = null
                    )
                }
                IconButton(
                    onClick = { onEditButtonClicked(it.timerId.toInt()) },
                    modifier = Modifier
                        .padding(5.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFEC407A))
                        .size(40.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_edit_24),
                        contentDescription = null
                    )
                }
                IconButton(
                    onClick = { onDeleteButtonClicked(it) },
                    modifier = Modifier
                        .padding(5.dp) // margin
                        .clip(CircleShape)
                        .background(Color(0xFFEC407A))
                        .size(40.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_delete_24),
                        contentDescription = null
                    )
                }
            }

        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .height(60.dp),
            verticalAlignment = Alignment.Bottom) {
            IconButton(onClick = onAddButtonClicked,
                modifier = Modifier
                    .padding(5.dp) // margin
                    .clip(CircleShape)
                    .background(Color(0xFFEC407A))
                    .size(40.dp)) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_add_24),
                    contentDescription = null)
            }
            IconButton(onClick = onSettingsButtonClicked,
                modifier = Modifier
                    .padding(5.dp) // margin
                    .clip(CircleShape)
                    .background(Color(0xFFEC407A))
                    .size(40.dp)) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_settings_24),
                    contentDescription = null)
            }
        }
    }
}

@Preview
@Composable
fun StartListPreview() {
    StartListScreen(emptyList())
}