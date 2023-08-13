package com.gasparaitis.owncommunity.presentation.search

import com.gasparaitis.owncommunity.domain.shared.profile.model.Profile
import com.gasparaitis.owncommunity.presentation.shared.composables.post.PostAction

sealed class SearchAction {
    object OnSearchIconRepeatClick : SearchAction()
    object OnSearchBarClick : SearchAction()
    class OnSearchBarQueryChange(val query: String) : SearchAction()
    class OnTabSelected(val index: Int) : SearchAction()
    class OnPostAction(val postAction: PostAction) : SearchAction()
    class OnProfileBodyClick(val profile: Profile) : SearchAction()
    class OnProfileFollowButtonClick(val profile: Profile) : SearchAction()
}
