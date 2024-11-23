package co.anbora.labs.jsoncrack.ide.fileType

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import javax.swing.JComponent


class FileTypeDialog(title: String, project: Project): DialogWrapper(project, false) {

    private val fileTypePanel = FileTypePanel()

    init {
        this.title = title
        init()
    }

    override fun createCenterPanel(): JComponent = fileTypePanel.mainPanel

    fun getExtension(): String = fileTypePanel.patternField.text

    override fun doValidate(): ValidationInfo? {
        if (fileTypePanel.patternField.text.isEmpty()) {
            return ValidationInfo("Not valid extension", fileTypePanel.patternField)
        }

        if (!fileTypePanel.patternField.text.startsWith(".")) {
            return ValidationInfo("Extension must start with `.`", fileTypePanel.patternField)
        }

        return null
    }
}