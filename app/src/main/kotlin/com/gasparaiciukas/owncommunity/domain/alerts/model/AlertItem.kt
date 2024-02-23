package com.gasparaiciukas.owncommunity.domain.alerts.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.gasparaiciukas.owncommunity.R
import java.util.UUID

data class AlertItem(
    val id: String,
    val section: Section,
    val type: Type,
    val title: String,
    val timestamp: Long,
    val isRead: Boolean,
) {
    companion object {
        val EMPTY =
            AlertItem(
                id = UUID.randomUUID().toString(),
                section = Section.Today,
                type = Type.Like,
                title = "",
                timestamp = 0L,
                isRead = false,
            )
    }

    enum class Type(
        @DrawableRes val icon: Int
    ) {
        Like(R.drawable.ic_alert_like),
        Comment(R.drawable.ic_alert_comment),
        Birthday(R.drawable.ic_alert_birthday),
        Mention(R.drawable.ic_alert_mention)
    }

    enum class Section(
        @StringRes val nameResId: Int,
    ) {
        Today(R.string.alert_item_section_today),
        Last30Days(R.string.alert_item_section_last_30_days),
        Older(R.string.alert_item_section_older),
    }
}
