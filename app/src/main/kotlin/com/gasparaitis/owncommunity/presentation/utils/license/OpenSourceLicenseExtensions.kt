package com.gasparaitis.owncommunity.presentation.utils.license

import android.content.Context
import android.content.Intent
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

fun Context.openOpenSourceLicensesScreen() {
    val intent = Intent(this, OssLicensesMenuActivity::class.java)
    startActivity(intent)
}
