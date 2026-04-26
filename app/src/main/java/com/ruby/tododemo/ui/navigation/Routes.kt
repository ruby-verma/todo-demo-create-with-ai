package com.ruby.tododemo.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Route : NavKey {
    @Serializable
    data object Main : Route

    @Serializable
    data class AddEditTask(val taskId: Long? = null) : Route
}
