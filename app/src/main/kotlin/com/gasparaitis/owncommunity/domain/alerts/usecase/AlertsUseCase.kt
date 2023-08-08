package com.gasparaitis.owncommunity.domain.alerts.usecase

import com.gasparaitis.owncommunity.domain.alerts.model.AlertItem
import com.gasparaitis.owncommunity.domain.alerts.model.AlertItemSection
import com.gasparaitis.owncommunity.domain.alerts.model.AlertItemType
import com.gasparaitis.owncommunity.presentation.alerts.AlertsState

class AlertsUseCase {
    fun getState(): AlertsState = AlertsState.EMPTY.copy(
        alertItems = AlertsStateDemo.alerts.groupBy { it.section },
    )
}

// TODO: REMOVE ME
object AlertsStateDemo {
    val alert1 = AlertItem.EMPTY.copy(
        isRead = false,
        section = AlertItemSection.TODAY,
        type = AlertItemType.LIKE,
        title = "Sofia, John and +19 others liked your post.",
        date = "10m ago"
    )
    val alert2 = AlertItem.EMPTY.copy(
        isRead = false,
        section = AlertItemSection.TODAY,
        type = AlertItemType.LIKE,
        title = "Rebecca, Daisy and +11 others liked your post.",
        date = "30m ago"
    )
    val alert3 = AlertItem.EMPTY.copy(
        isRead = true,
        section = AlertItemSection.LAST_30_DAYS,
        type = AlertItemType.COMMENT,
        title = "Katrina, Denver and +2 others commented on your post.",
        date = "1d ago",
    )
    val alert4 = AlertItem.EMPTY.copy(
        isRead = false,
        section = AlertItemSection.OLDER,
        type = AlertItemType.BIRTHDAY,
        title = "Savannah Wilson is celebrating birthday today. Drop a wish! \uD83C\uDF89",
        date = "155d ago",
    )
    val alert5 = AlertItem.EMPTY.copy(
        isRead = true,
        section = AlertItemSection.OLDER,
        type = AlertItemType.MENTION,
        title = "Ralph Edwards mentioned you in a post.",
        date = "156d ago",
    )
    val alert6 = AlertItem.EMPTY.copy(
        isRead = false,
        section = AlertItemSection.OLDER,
        type = AlertItemType.MENTION,
        title = "Leslie Alexander mentioned you in a comment.",
        date = "793d ago",
    )
    val alerts = listOf(alert1, alert2, alert3, alert4, alert5, alert6, alert6)
}
