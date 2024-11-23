package co.anbora.labs.jsoncrack.ide.settings

import co.anbora.labs.jsoncrack.ide.fileType.FileTypeService.Companion.fileTypeSettings
import com.intellij.openapi.Disposable
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import javax.swing.JComponent

class FileTypeSettingsConfigurable(
    project: Project
): Configurable, Disposable {

    private val dialog = PatternsPanel(project, HashSet(fileTypeSettings.extensions()))

    override fun createComponent(): JComponent = dialog

    override fun isModified(): Boolean = fileTypeSettings.extensions().size != dialog.getExtensions().size

    override fun apply() {
        fileTypeSettings.addExtensions(dialog.getExtensions())
    }

    override fun getDisplayName(): String = "JSonCrack"

    override fun dispose() = Unit
}