package co.anbora.labs.jsoncrack.ide.fileType

import co.anbora.labs.jsoncrack.ide.SUPPORTED_EXTENSIONS
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.service
import com.intellij.util.xmlb.XmlSerializerUtil
import com.intellij.util.xmlb.annotations.XCollection

@State(
    name = "JSONCrackN",
    storages = [Storage("JSONCrackN.xml")]
)
class FileTypeService: PersistentStateComponent<FileTypeService.ToolchainState?> {

    private var state = ToolchainState()

    fun extensions(): Set<String> {
        if (state.extensions.isEmpty()) {
            this.addExtensions(SUPPORTED_EXTENSIONS)
        }
        return state.extensions
    }

    class ToolchainState {
        @XCollection
        var extensions = mutableSetOf<String>()
    }

    override fun getState(): ToolchainState = this.state

    override fun loadState(state: ToolchainState) {
        XmlSerializerUtil.copyBean(state, this.state)
    }

    fun addExtensions(supportedExtensions: Set<String>) {
        this.state.extensions.clear()
        this.state.extensions.addAll(supportedExtensions)
    }

    companion object {
        val fileTypeSettings
            get() = service<FileTypeService>()
    }
}