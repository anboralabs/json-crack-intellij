package co.anbora.labs.jsoncrack.ide.editor

import java.util.*


enum class Theme {
    LIGHT,
    DARK;

    override fun toString(): String {
        return name.lowercase(Locale.getDefault())
    }
}
