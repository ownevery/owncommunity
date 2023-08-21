package com.gasparaitis.owncommunity.presentation.shared.composables.post

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gasparaitis.owncommunity.R
import com.gasparaitis.owncommunity.domain.shared.post.model.Post
import com.gasparaitis.owncommunity.domain.shared.post.model.PostActionItem
import com.gasparaitis.owncommunity.domain.shared.post.model.PostType
import com.gasparaitis.owncommunity.presentation.utils.extensions.humanReadableTimeAgo
import com.gasparaitis.owncommunity.presentation.utils.modifier.noRippleClickable
import com.gasparaitis.owncommunity.presentation.utils.theme.Colors
import com.gasparaitis.owncommunity.presentation.utils.theme.TextStyles

@Composable
fun PostView(
    item: Post,
    onAction: (PostAction) -> Unit,
) {
    Divider(
        modifier = Modifier.fillMaxWidth(),
        thickness = 1.dp,
        color = Color.DarkGray,
    )
    Column(
        modifier = Modifier
            .padding(top = 24.dp),
    ) {
        PostTopRow(
            profileImage = painterResource(id = item.profileImage),
            authorName = item.authorName,
            postedTimeAgo = item.postedAtTimestamp.humanReadableTimeAgo
        ) { onAction(PostAction.OnAuthorClick(item)) }
        PostBody(
            item = item,
            onClick = { onAction(PostAction.OnBodyClick(item)) },
        )
        PostBottomRow(
            item = item,
            onAction = onAction,
        )
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun PostBody(
    item: Post,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier.noRippleClickable(onClick),
    ) {
        PostBodyText(
            text = item.bodyText,
            singleLine = item.type !is PostType.TextOnly,
        )
        if (item.type is PostType.Images) {
            PostImageView(images = item.type.images)
        }
    }
}

@Composable
private fun PostImageView(
    images: List<Int>,
    itemWidth: Dp = 320.dp,
    itemHeight: Dp = 180.dp,
) {
    if (images.isEmpty()) return
    if (images.size == 1) {
        PostSingleImage(
            image = images.first(),
            itemWidth = itemWidth,
            itemHeight = itemHeight,
        )
        return
    }
    PostImagePager(
        images = images,
        itemWidth = itemWidth,
        itemHeight = itemHeight,
    )
}

@Composable
private fun PostImage(
    @DrawableRes image: Int,
    width: Dp,
    height: Dp,
    modifier: Modifier = Modifier,
) {
    Image(
        modifier = Modifier
            .then(modifier)
            .size(width = width, height = height)
            .clip(RoundedCornerShape(16.dp)),
        painter = painterResource(id = image),
        contentScale = ContentScale.FillBounds,
        contentDescription = "Post image",
    )
}

@Composable
private fun PostSingleImage(
    @DrawableRes image: Int,
    itemWidth: Dp,
    itemHeight: Dp,
) {
    PostImage(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .padding(horizontal = 24.dp),
        image = image,
        width = itemWidth,
        height = itemHeight,
    )
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun PostImagePager(
    images: List<Int>,
    itemWidth: Dp,
    itemHeight: Dp,
) {
    val state = rememberPagerState { images.size }
    Column {
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(start = 24.dp),
            state = state,
            pageSpacing = 12.dp,
            pageSize = PageSize.Fixed(itemWidth),
            beyondBoundsPageCount = 3,
        ) { index ->
            PostImage(
                image = images[index],
                width = itemWidth,
                height = itemHeight,
            )
        }
        PostImagePagerIndicator(
            pageCount = images.size,
            currentPage = state.currentPage,
        )
    }
}

@Composable
private fun PostImagePagerIndicator(
    pageCount: Int,
    currentPage: Int,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(pageCount) { index ->
            Box(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clip(CircleShape)
                    .background(if (currentPage == index) Colors.SocialPink else Colors.LightGray)
                    .size(6.dp),
            )
        }
    }
}

@Composable
private fun PostTopRow(
    profileImage: Painter,
    authorName: String,
    postedTimeAgo: String,
    onAuthorClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.noRippleClickable(onAuthorClick)
        ) {
            PostTopRowProfileImage(
                image = profileImage,
            )
            PostTopRowTextColumn(
                name = authorName,
                time = postedTimeAgo,
            )
        }
        Spacer(Modifier.weight(1f))
        PostTopRowMoreButton()
    }
}

@Composable
private fun PostTopRowProfileImage(
    image: Painter,
) {
    Image(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape),
        painter = image,
        contentScale = ContentScale.Crop,
        contentDescription = "Profile image",
    )
}

@Composable
private fun PostTopRowTextColumn(
    name: String,
    time: String,
) {
    Column(
        modifier = Modifier.padding(start = 8.dp),
    ) {
        Text(
            text = name,
            style = TextStyles.body,
            color = Colors.SocialWhite,
        )
        Text(
            text = time,
            style = TextStyles.secondary,
            color = Colors.LightGray,
        )
    }
}

@Composable
private fun PostTopRowMoreButton() {
    Icon(
        painter = painterResource(id = R.drawable.ic_dots_vertical),
        tint = Colors.LightGray,
        contentDescription = "See more",
    )
}

@Composable
private fun PostBodyText(
    text: String,
    singleLine: Boolean = false,
) {
    Text(
        modifier = Modifier
            .padding(top = 16.dp)
            .padding(horizontal = 24.dp),
        text = text,
        style = TextStyles.secondary,
        lineHeight = 24.sp,
        maxLines = if (singleLine) 1 else 6,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
private fun PostBottomRow(
    item: Post,
    onAction: (PostAction) -> Unit,
) {
    val actions = PostActionItem.actions(item)
    Row(
        modifier = Modifier
            .padding(top = 18.dp)
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(actions.size) { index ->
            PostActionRow(
                onClick = { onAction(actions[index].action) },
                isActive = actions[index].isActive,
                painter = painterResource(id = actions[index].icon),
                description = actions[index].description,
                text = actions[index].count,
            )
        }
        Spacer(Modifier.weight(1f))
        PostBookmarkIconButton(
            onClick = { onAction(PostAction.OnBookmarkClick(item)) },
            isActive = item.isBookmarked,
        )
    }
}

@Composable
private fun PostActionRow(
    onClick: () -> Unit,
    isActive: Boolean,
    painter: Painter,
    description: String,
    text: String,
) {
    Row(
        modifier = Modifier.noRippleClickable(onClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painter,
            tint = if (isActive) Colors.SocialPink else Colors.SocialWhite,
            contentDescription = description,
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = text,
            style = TextStyles.tertiary,
            color = if (isActive) Colors.SocialPink else Colors.SocialWhite,
        )
        Spacer(Modifier.width(20.dp))
    }
}

@Composable
private fun PostBookmarkIconButton(
    onClick: () -> Unit,
    isActive: Boolean,
) {
    IconButton(onClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_bookmark),
            tint = if (isActive) Colors.SocialPink else Colors.SocialWhite,
            contentDescription = "Bookmark",
        )
    }
}
