package com.gasparaitis.owncommunity.presentation.story

import androidx.lifecycle.ViewModel
import com.gasparaitis.owncommunity.domain.shared.story.usecase.StoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.mutate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val storyUseCase: StoryUseCase,
) : ViewModel() {
    private val _uiState: MutableStateFlow<StoryState> = MutableStateFlow(StoryState.EMPTY)
    val uiState: StateFlow<StoryState> = _uiState.asStateFlow()

    init {
        onCreated()
    }

    private fun onCreated() {
        val stories = storyUseCase.getStories()
        _uiState.update { state ->
            state.copy(
                selectedTabIndex = state.selectedTabIndex,
                stories = stories,
            )
        }
    }

    fun onEvent(event: StoryState.Event) {
        when (event) {
            is StoryState.OnProfileClick -> onProfileClick()
            StoryState.OnStoryReplyBarClick -> onStoryReplyBarClick()
            is StoryState.OnStoryReplyQueryChange -> onStoryReplyQueryChange(event.query)
            StoryState.OnStoryReplySend -> onStoryReplySend()
            is StoryState.OnTabSelected -> onTabSelected(event.index)
            is StoryState.OnStoryGoBack -> onStoryGoBack(event.storyIndex, event.storyItemIndex)
            is StoryState.OnStoryGoForward ->
                onStoryGoForward(
                    event.storyIndex,
                    event.storyItemIndex,
                )
            StoryState.NavigateToProfileScreen -> {}
        }
    }

    fun onEventHandled(event: StoryState.Event?) {
        _uiState.update { state ->
            if (state.event == event) {
                state.copy(
                    event = null,
                )
            } else {
                state
            }
        }
    }

    private fun onStoryGoBack(
        storyIndex: Int,
        storyEntryIndex: Int
    ) {
        _uiState.update { state ->
            val stories =
                state.stories.mutate { storyList ->
                    storyList.mapIndexed { index, story ->
                        if (index == storyIndex) {
                            story.copy(
                                storyEntries =
                                    story.storyEntries.mutate { storyEntryList ->
                                        storyEntryList.mapIndexed { entryIndex, storyEntry ->
                                            if (entryIndex == storyEntryIndex) {
                                                storyEntry.copy(isSeen = false)
                                            } else {
                                                storyEntry
                                            }
                                        }
                                    },
                            )
                        } else {
                            story
                        }
                    }
                }
            state.copy(
                stories = stories,
            )
        }
    }

    private fun onStoryGoForward(
        storyIndex: Int,
        storyEntryIndex: Int
    ) {
        _uiState.update { state ->
            val stories =
                state.stories.mutate { storyList ->
                    storyList.mapIndexed { index, story ->
                        if (index == storyIndex) {
                            story.copy(
                                storyEntries =
                                    story.storyEntries.mutate { storyEntryList ->
                                        storyEntryList.mapIndexed { entryIndex, storyEntry ->
                                            if (entryIndex == storyEntryIndex) {
                                                storyEntry.copy(isSeen = true)
                                            } else {
                                                storyEntry
                                            }
                                        }
                                    },
                            )
                        } else {
                            story
                        }
                    }
                }
            state.copy(
                stories = stories,
            )
        }
    }

    private fun onProfileClick() {
        _uiState.update { state ->
            state.copy(
                event = StoryState.NavigateToProfileScreen,
            )
        }
    }

    private fun onStoryReplyBarClick() {
    }

    private fun onStoryReplyQueryChange(text: String) {
        _uiState.update { it.copy(searchText = it.searchText.copy(text = text)) }
    }

    private fun onStoryReplySend() {
    }

    private fun onTabSelected(index: Int) {
        _uiState.update { it.copy(selectedTabIndex = index) }
    }
}
