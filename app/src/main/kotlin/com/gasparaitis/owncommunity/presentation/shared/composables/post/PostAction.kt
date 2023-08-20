package com.gasparaitis.owncommunity.presentation.shared.composables.post

import com.gasparaitis.owncommunity.domain.shared.post.model.Post

sealed class PostAction {
    data class OnLikeClick(val item: Post) : PostAction()
    data class OnBodyClick(val item: Post) : PostAction()
    data class OnCommentClick(val item: Post) : PostAction()
    data class OnShareClick(val item: Post) : PostAction()
    data class OnBookmarkClick(val item: Post) : PostAction()
    data class OnAuthorClick(val item: Post) : PostAction()
}
