package com.gasparaitis.owncommunity.domain.alerts.usecase

import com.gasparaitis.owncommunity.domain.alerts.model.AlertItem
import com.gasparaitis.owncommunity.domain.alerts.model.AlertItemSection
import com.gasparaitis.owncommunity.domain.alerts.model.AlertItemType
import com.gasparaitis.owncommunity.presentation.alerts.AlertsState
import com.gasparaitis.owncommunity.presentation.utils.extensions.ClockUtils
import java.util.UUID
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes

class AlertsUseCase {
    fun getState(): AlertsState = AlertsState.EMPTY.copy(
        alertItems = alerts.groupBy { it.section },
    )
    companion object {
        private val alert1 = AlertItem.EMPTY.copy(
            id = UUID.randomUUID().toString(),
            isRead = false,
            section = AlertItemSection.TODAY,
            type = AlertItemType.LIKE,
            title = "Sofia, John and +19 others liked your post.",
            timestamp = ClockUtils.shiftCurrentTime((-10).minutes),
        )
        private val alert2 = AlertItem.EMPTY.copy(
            id = UUID.randomUUID().toString(),
            isRead = false,
            section = AlertItemSection.TODAY,
            type = AlertItemType.LIKE,
            title = "Rebecca, Daisy and +11 others liked your post.",
            timestamp = ClockUtils.shiftCurrentTime((-30).minutes),
        )
        private val alert3 = AlertItem.EMPTY.copy(
            id = UUID.randomUUID().toString(),
            isRead = true,
            section = AlertItemSection.LAST_30_DAYS,
            type = AlertItemType.COMMENT,
            title = "Katrina, Denver and +2 others commented on your post.",
            timestamp = ClockUtils.shiftCurrentTime((-1).days),
        )
        private val alert4 = AlertItem.EMPTY.copy(
            id = UUID.randomUUID().toString(),
            isRead = false,
            section = AlertItemSection.OLDER,
            type = AlertItemType.BIRTHDAY,
            title = "Savannah Wilson is celebrating birthday today. Drop a wish! \uD83C\uDF89",
            timestamp = ClockUtils.shiftCurrentTime((-155).days),
        )
        private val alert5 = AlertItem.EMPTY.copy(
            id = UUID.randomUUID().toString(),
            isRead = true,
            section = AlertItemSection.OLDER,
            type = AlertItemType.MENTION,
            title = "Ralph Edwards mentioned you in a post.",
            timestamp = ClockUtils.shiftCurrentTime((-156).days),
        )
        private val alert6 = AlertItem.EMPTY.copy(
            id = UUID.randomUUID().toString(),
            isRead = false,
            section = AlertItemSection.OLDER,
            type = AlertItemType.MENTION,
            title = "Leslie Alexander mentioned you in a comment.",
            timestamp = ClockUtils.shiftCurrentTime((-793).days),
        )
        val alerts = listOf(alert1, alert2, alert3, alert4, alert5, alert6, alert6)
    }
}
