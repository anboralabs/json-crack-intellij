package co.anbora.labs.jsoncrack.ide.settings

import com.intellij.openapi.Disposable
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import javax.swing.JComponent

class FileTypeSettingsConfigurable(private val project: Project): Configurable, Disposable {
    override fun createComponent(): JComponent = PatternsPanel()

    override fun isModified(): Boolean = false

    override fun apply() {
    }

    override fun getDisplayName(): String = "JSonCrack"

    override fun dispose() = Unit
}