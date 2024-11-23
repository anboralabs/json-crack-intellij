package co.anbora.labs.jsoncrack.ide

import com.intellij.openapi.vfs.VirtualFile

val SUPPORTED_EXTENSIONS = setOf(".json")

fun VirtualFile?.isJsonFile(extensions: Set<String>): Boolean {
    if (this == null) {
        return false
    }

    if (this.isDirectory || !this.exists()) {
        return false
    }

    return extensions.any { ext -> this.name.lowercase().endsWith(ext) }
}