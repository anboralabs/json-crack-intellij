package co.anbora.labs.jsoncrack.ide.editor

import co.anbora.labs.jsoncrack.ide.fileType.FileTypeService.Companion.fileTypeSettings
import co.anbora.labs.jsoncrack.ide.isJsonFile
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorPolicy
import com.intellij.openapi.fileEditor.FileEditorProvider
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

private const val EDITOR_TYPE_ID = "co.anbora.labs.jsoncrack.editor"


class EditorProvider : FileEditorProvider, DumbAware  {
    override fun accept(project: Project, file: VirtualFile): Boolean {
        return file.isJsonFile(fileTypeSettings.extensions())
    }

    override fun createEditor(project: Project, file: VirtualFile): FileEditor = Editor(project, file)

    override fun getEditorTypeId() = EDITOR_TYPE_ID

    override fun getPolicy() = FileEditorPolicy.PLACE_AFTER_DEFAULT_EDITOR
}
