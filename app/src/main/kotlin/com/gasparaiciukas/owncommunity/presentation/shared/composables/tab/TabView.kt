package com.gasparaiciukas.owncommunity.presentation.shared.composables.tab

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gasparaiciukas.owncommunity.presentation.utils.extensions.customTabIndicatorOffset
import com.gasparaiciukas.owncommunity.presentation.utils.theme.Colors
import com.gasparaiciukas.owncommunity.presentation.utils.theme.TextStyles
import kotlinx.collections.immutable.PersistentList

@Composable
fun TabView(
    selectedTabIndex: Int,
    tabs: PersistentList<String>,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val tabWidths =
        remember {
            val tabWidthStateList = mutableStateListOf<Dp>()
            repeat(tabs.size) {
                tabWidthStateList.add(0.dp)
            }
            tabWidthStateList
        }
    TabRow(
        modifier =
            Modifier
                .then(modifier),
        selectedTabIndex = selectedTabIndex,
        divider = {},
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier =
                    Modifier.customTabIndicatorOffset(
                        currentTabPosition = tabPositions[selectedTabIndex],
                        tabWidth = tabWidths[selectedTabIndex],
                    ),
                color = Colors.SocialBlue,
                height = 4.dp,
            )
        },
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                text = {
                    Text(
                        text = title,
                        style =
                            TextStyles.secondary.copy(
                                fontWeight =
                                    if (selectedTabIndex == index) {
                                        FontWeight.Bold
                                    } else {
                                        FontWeight.Normal
                                    },
                                lineHeight = 16.sp,
                            ),
                        onTextLayout = { textLayoutResult ->
                            tabWidths[index] = with(density) { textLayoutResult.size.width.toDp() }
                        },
                    )
                },
            )
        }
    }
}
