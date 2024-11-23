package co.anbora.labs.jsoncrack.ide.settings

import co.anbora.labs.jsoncrack.ide.fileType.FileTypeDialog
import co.anbora.labs.jsoncrack.ide.i18n.JSonCrackBundle
import com.intellij.openapi.project.Project
import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.JBColor
import com.intellij.ui.ListUtil
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBScrollPane
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import javax.swing.DefaultListModel
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.ListSelectionModel


class PatternsPanel(
    private val project: Project,
    private val extensions: Collection<String>
): JPanel() {

    private val myList = JBList<String>(DefaultListModel())
    private val TITLE_INSETS = JBUI.insetsTop(8)

    init {
        layout = BorderLayout()
        myList.selectionMode = ListSelectionModel.SINGLE_SELECTION
        //myList.setCellRenderer(ExtensionRenderer())
        myList.emptyText.setText(JSonCrackBundle.message("filetype.settings.no.patterns"))
        myList.border = JBUI.Borders.empty()

        val decorator: ToolbarDecorator = ToolbarDecorator.createDecorator(myList)
            .setScrollPaneBorder(JBUI.Borders.empty())
            .setPanelBorder(JBUI.Borders.customLine(JBColor.border(), 1, 1, 0, 1))
            .setAddAction { showAddExtensionDialog() }
            .setRemoveAction { removeExtension() }
            .disableUpDownActions()
        add(decorator.createPanel(), BorderLayout.NORTH)
        val scrollPane: JScrollPane = JBScrollPane(myList)
        scrollPane.border = JBUI.Borders.customLine(JBColor.border(), 0, 1, 1, 1)
        add(scrollPane, BorderLayout.CENTER)


        border = IdeBorderFactory.createTitledBorder(
            JSonCrackBundle.message("filetype.registered.patterns.group"),
            false,
            TITLE_INSETS
        ).setShowLine(false)


        this.refill()
    }

    private fun refill() {
        val model: DefaultListModel<String> =
            myList.model as DefaultListModel<String>

        model.addAll(extensions)
    }

    private fun addExtensions(extension: String) {
        val model: DefaultListModel<String> =
            myList.model as DefaultListModel<String>

        model.addElement(extension)
    }

    private fun removeExtension() {
        val selectedItem = myList.selectedValue
        if (selectedItem != null) {
            ListUtil.removeSelectedItems(myList)
        }
    }

    fun getExtensions(): Set<String> {
        val model: DefaultListModel<String> =
            myList.model as DefaultListModel<String>

        return model.elements().toList().toSet()
    }

    private fun showAddExtensionDialog() {
        val dialog = FileTypeDialog("Add Extension", project)

        if (dialog.showAndGet()) {
            this.addExtensions(dialog.getExtension())
        }
    }

}