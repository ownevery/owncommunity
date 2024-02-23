package com.gasparaiciukas.owncommunity.domain.shared.comment

data class Comment(
    val id: String,
) {
    companion object {
        val EMPTY =
            Comment(
                id = "",
            )
    }
}
