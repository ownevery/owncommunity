package com.gasparaitis.owncommunity.presentation.story

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gasparaitis.owncommunity.domain.shared.story.usecase.StoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val storyUseCase: StoryUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<StoryState> = MutableStateFlow(StoryState.EMPTY)
    val state: StateFlow<StoryState> = _state.asStateFlow()

    private val _navEvent = MutableSharedFlow<StoryNavEvent>()
    val navEvent = _navEvent.asSharedFlow()

    init {
        onCreated()
    }

    private fun onCreated() {
        val stories = storyUseCase.getStories()
        _state.update { state ->
            state.copy(
                selectedTabIndex = state.selectedTabIndex,
                stories = stories,
            )
        }
    }

    fun onAction(action: StoryAction) {
        when (action) {
            is StoryAction.OnProfileClick -> onProfileClick()
            StoryAction.OnStoryReplyBarClick -> onStoryReplyBarClick()
            is StoryAction.OnStoryReplyQueryChange -> onStoryReplyQueryChange(action.query)
            StoryAction.OnStoryReplySend -> onStoryReplySend()
            is StoryAction.OnTabSelected -> onTabSelected(action.index)
            is StoryAction.OnStoryGoBack -> onStoryGoBack(action.storyIndex, action.storyItemIndex)
            is StoryAction.OnStoryGoForward -> onStoryGoForward(action.storyIndex, action.storyItemIndex)
        }
    }

    private fun onStoryGoBack(storyIndex: Int, storyEntryIndex: Int) {
        _state.update { state ->
            state.copy(
                stories = state.stories.mapIndexed { index, story ->
                    if (index == storyIndex) {
                        story.copy(
                            storyEntries = story.storyEntries.mapIndexed { entryIndex, storyEntry ->
                                if (entryIndex == storyEntryIndex) {
                                    storyEntry.copy(isSeen = false)
                                } else {
                                    storyEntry
                                }
                            }
                        )
                    } else {
                        story
                    }
                }
            )
        }
    }

    private fun onStoryGoForward(storyIndex: Int, storyEntryIndex: Int) {
        _state.update { state ->
            state.copy(
                stories = state.stories.mapIndexed { index, story ->
                    if (index == storyIndex) {
                        story.copy(
                            storyEntries = story.storyEntries.mapIndexed { entryIndex, storyEntry ->
                                if (entryIndex == storyEntryIndex) {
                                    storyEntry.copy(isSeen = true)
                                } else {
                                    storyEntry
                                }
                            }
                        )
                    } else {
                        story
                    }
                }
            )
        }
    }

    private fun onProfileClick() {
        viewModelScope.launch {
            _navEvent.emit(StoryNavEvent.OpenProfile)
        }
    }

    private fun onStoryReplyBarClick() {
    }

    private fun onStoryReplyQueryChange(text: String) {
        _state.update { it.copy(searchText = it.searchText.copy(text = text)) }
    }

    private fun onStoryReplySend() {
    }

    private fun onTabSelected(index: Int) {
        _state.update { it.copy(selectedTabIndex = index) }
    }
}
