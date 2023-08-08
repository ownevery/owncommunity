package com.gasparaitis.owncommunity.domain.alerts.model

import androidx.annotation.StringRes
import com.gasparaitis.owncommunity.R

enum class AlertItemSection(
    @StringRes val nameResId: Int,
) {
    TODAY(R.string.alert_item_section_today),
    LAST_30_DAYS(R.string.alert_item_section_last_30_days),
    OLDER(R.string.alert_item_section_older),
}
