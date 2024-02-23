package com.gasparaiciukas.owncommunity.domain.alerts.usecase

import com.gasparaiciukas.owncommunity.domain.alerts.model.AlertItem
import com.gasparaiciukas.owncommunity.presentation.utils.extensions.ClockUtils
import java.util.UUID
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentMap

class AlertListUseCase {
    fun getAlerts(): PersistentMap<AlertItem.Section, PersistentList<AlertItem>> =
        alerts.groupBy {
            it.section
        }.mapValues { (_, value) ->
            value.toPersistentList()
        }.toPersistentMap()

    fun getAreAllItemsRead(): Boolean = false

    companion object {
        private val alert1 =
            AlertItem.EMPTY.copy(
                id = UUID.randomUUID().toString(),
                isRead = false,
                section = AlertItem.Section.Today,
                type = AlertItem.Type.Like,
                title = "Sofia, John and +19 others liked your post.",
                timestamp = ClockUtils.shiftCurrentTime((-10).minutes),
            )
        private val alert2 =
            AlertItem.EMPTY.copy(
                id = UUID.randomUUID().toString(),
                isRead = false,
                section = AlertItem.Section.Today,
                type = AlertItem.Type.Like,
                title = "Rebecca, Daisy and +11 others liked your post.",
                timestamp = ClockUtils.shiftCurrentTime((-30).minutes),
            )
        private val alert3 =
            AlertItem.EMPTY.copy(
                id = UUID.randomUUID().toString(),
                isRead = true,
                section = AlertItem.Section.Last30Days,
                type = AlertItem.Type.Comment,
                title = "Katrina, Denver and +2 others commented on your post.",
                timestamp = ClockUtils.shiftCurrentTime((-1).days),
            )
        private val alert4 =
            AlertItem.EMPTY.copy(
                id = UUID.randomUUID().toString(),
                isRead = false,
                section = AlertItem.Section.Older,
                type = AlertItem.Type.Birthday,
                title = "Savannah Wilson is celebrating birthday today. Drop a wish! \uD83C\uDF89",
                timestamp = ClockUtils.shiftCurrentTime((-155).days),
            )
        private val alert5 =
            AlertItem.EMPTY.copy(
                id = UUID.randomUUID().toString(),
                isRead = true,
                section = AlertItem.Section.Older,
                type = AlertItem.Type.Mention,
                title = "Ralph Edwards mentioned you in a post.",
                timestamp = ClockUtils.shiftCurrentTime((-156).days),
            )
        private val alert6 =
            AlertItem.EMPTY.copy(
                id = UUID.randomUUID().toString(),
                isRead = false,
                section = AlertItem.Section.Older,
                type = AlertItem.Type.Mention,
                title = "Leslie Alexander mentioned you in a comment.",
                timestamp = ClockUtils.shiftCurrentTime((-793).days),
            )
        val alerts = persistentListOf(alert1, alert2, alert3, alert4, alert5, alert6, alert6)
    }
}
