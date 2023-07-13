package co.anbora.labs.jsoncrack.ide

import com.intellij.openapi.vfs.VirtualFile

private val SUPPORTED_EXTENSIONS = arrayOf(".json")

fun VirtualFile?.isJsonFile(): Boolean {
    if (this == null) {
        return false
    }

    if (this.isDirectory || !this.exists()) {
        return false
    }

    val extensions = SUPPORTED_EXTENSIONS

    return extensions.any { ext -> this.name.lowercase().endsWith(ext) }
}