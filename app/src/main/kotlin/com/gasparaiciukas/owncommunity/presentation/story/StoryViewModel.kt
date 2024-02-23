package com.gasparaiciukas.owncommunity.presentation.story

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gasparaiciukas.owncommunity.domain.shared.story.model.Story
import com.gasparaiciukas.owncommunity.domain.shared.story.usecase.StoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.mutate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
            is StoryState.OnPageSelected -> onPageSelected(event.index)
            is StoryState.OnStoryGoBack ->
                onStoryGoBack(
                    event.storyIndex,
                    event.scrollToPage,
                )

            is StoryState.OnStoryGoForward ->
                onStoryGoForward(
                    event.storyIndex,
                    event.scrollToPage,
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
        scrollToPage: suspend (Int) -> Unit,
    ) {
        val story = _uiState.value.stories[storyIndex]
        if (story.entryIndex == 0) {
            viewModelScope.launch {
                val previousPage = _uiState.value.currentPage.dec()
                scrollToPage(previousPage)
            }
        } else {
            _uiState.update { state ->
                val stories =
                    state.stories
                        .updateStoryAtIndex(
                            index = storyIndex,
                            update = { it.copy(entryIndex = it.entryIndex.dec()) },
                        )
                state.copy(
                    stories = stories,
                )
            }
        }
    }

    private fun onStoryGoForward(
        storyIndex: Int,
        scrollToPage: suspend (Int) -> Unit,
    ) {
        val story = _uiState.value.stories[storyIndex]
        if (story.entries.size.dec() == story.entryIndex) {
            viewModelScope.launch {
                val nextPage = _uiState.value.currentPage.inc()
                scrollToPage(nextPage)
            }
        } else {
            _uiState.update { state ->
                val stories =
                    state.stories
                        .updateStoryAtIndex(
                            index = storyIndex,
                        ) { it.copy(entryIndex = it.entryIndex.inc()) }
                state.copy(
                    stories = stories,
                )
            }
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

    private fun onPageSelected(index: Int) {
        _uiState.update { state ->
            val stories = state.stories.updateAllStories { story -> story.copy(entryIndex = 0) }
            state.copy(
                currentPage = index,
                stories = stories,
            )
        }
    }

    private fun PersistentList<Story>.updateStoryAtIndex(
        index: Int,
        update: (Story) -> Story,
    ): PersistentList<Story> =
        mutate { storyList ->
            storyList[index] = update(storyList[index])
        }

    private fun PersistentList<Story>.updateAllStories(
        update: (Story) -> Story,
    ): PersistentList<Story> = mutate { it.replaceAll(update) }
}
