package com.gasparaitis.owncommunity.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.gasparaitis.owncommunity.R
import com.gasparaitis.owncommunity.utils.compose.ui.slightDarkGradientBrush
import com.gasparaitis.owncommunity.utils.compose.ui.theme.Colors
import com.gasparaitis.owncommunity.utils.compose.ui.theme.TextStyles
import com.ramcosta.composedestinations.annotation.Destination

@Destination(start = true)
@Composable
fun HomeScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        HomeScreenContent()
    }
}

@Composable
private fun HomeScreenContent() {
    HomeTopRow()
    HomeStoryHorizontalPager()
    HomeItem()
}

@Preview
@Composable
fun HomeTopRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Good Morning, Justas.",
            style = TextStyles.title,
        )
        Box {
            Icon(
                modifier = Modifier
                    .size(10.dp)
                    .zIndex(1f)
                    .align(Alignment.TopEnd),
                painter = painterResource(id = R.drawable.ic_notification_badge),
                tint = Color.Unspecified,
                contentDescription = "Notification badge",
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_message),
                tint = Colors.PureWhite,
                contentDescription = "Favorite",
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun HomeStoryHorizontalPager(
    images: List<Painter> = listOf(
        painterResource(id = R.drawable.demo_story_1),
        painterResource(id = R.drawable.demo_story_2),
        painterResource(id = R.drawable.demo_story_3),
        painterResource(id = R.drawable.demo_story_4),
    ),
    itemWidth: Dp = 100.dp,
    itemHeight: Dp = 140.dp,
) {
    val state = rememberPagerState()
    HorizontalPager(
        modifier = Modifier.padding(start = 24.dp, top = 32.dp),
        pageCount = images.size,
        pageSize = PageSize.Fixed(itemWidth),
        pageSpacing = 12.dp,
        state = state,
        beyondBoundsPageCount = 5,
    ) { index ->
        Image(
            modifier = Modifier
                .size(width = itemWidth, height = itemHeight)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.FillHeight,
            painter = images[index],
            contentDescription = "Story $index",
        )
        Box(
            modifier = Modifier
                .size(width = itemWidth, height = itemHeight)
                .clip(RoundedCornerShape(16.dp))
                .background(brush = slightDarkGradientBrush()),
        )
    }
}

@Composable
fun HomeItem() {
}
