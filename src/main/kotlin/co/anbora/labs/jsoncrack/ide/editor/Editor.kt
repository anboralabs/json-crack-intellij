package co.anbora.labs.jsoncrack.ide.editor

import com.intellij.openapi.editor.colors.EditorColorsListener
import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorLocation
import com.intellij.openapi.fileEditor.FileEditorState
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.openapi.util.UserDataHolderBase
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.openapi.vfs.newvfs.BulkFileListener
import com.intellij.openapi.vfs.newvfs.events.VFileEvent
import com.jetbrains.rd.util.lifetime.LifetimeDefinition
import org.jetbrains.annotations.NotNull
import java.beans.PropertyChangeListener
import javax.swing.JComponent


class Editor(project: Project, private val file: VirtualFile) : FileEditor, DumbAware {
    private val lifetimeDef = LifetimeDefinition()
    private val lifetime = lifetimeDef.lifetime
    private val userDataHolder = UserDataHolderBase()

    override fun getName(): String = "Diagram"
    override fun getFile() = file

    private var view : WebView = WebView(lifetime, file)

    init {
        val messageBus = project.messageBus

        messageBus.connect().subscribe(VirtualFileManager.VFS_CHANGES,
            object : BulkFileListener {
                override fun after(@NotNull events: MutableList<out VFileEvent>) {
                    for (event in events) {
                        if (event.isFromSave && event.file?.path == file.path) {
                            view.initializeSchemeHandler(file).also {
                                view.reload(true)
                            }
                        }
                    }
                }
            }
        )

        messageBus.connect().subscribe(EditorColorsManager.TOPIC, EditorColorsListener {
            view.reload(true)
        })
    }

    override fun <T : Any?> getUserData(key: Key<T>): T? {
        return userDataHolder.getUserData(key)
    }

    override fun <T : Any?> putUserData(key: Key<T>, value: T?) {
        userDataHolder.putUserData(key, value)
    }

    override fun getComponent(): JComponent {
        return view.component
    }

    override fun getPreferredFocusedComponent(): JComponent {
        return view.component
    }

    override fun setState(state: FileEditorState) {}

    override fun isModified(): Boolean {
        return false
    }

    override fun isValid(): Boolean {
        return true
    }

    fun openDevTools() {
        view.openDevTools()
    }

    override fun addPropertyChangeListener(listener: PropertyChangeListener) {}

    override fun removePropertyChangeListener(listener: PropertyChangeListener) {}

    override fun getCurrentLocation(): FileEditorLocation? = null

    override fun dispose() {
        lifetimeDef.terminate(true)
    }
}
