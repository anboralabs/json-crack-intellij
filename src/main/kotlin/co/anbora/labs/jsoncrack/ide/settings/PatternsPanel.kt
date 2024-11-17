package co.anbora.labs.jsoncrack.ide.settings

import com.intellij.openapi.fileTypes.FileTypesBundle
import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.JBColor
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBScrollPane
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import javax.swing.DefaultListModel
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.ListSelectionModel


class PatternsPanel: JPanel() {

    private val myList = JBList<String>(DefaultListModel())

    private val TITLE_INSETS = JBUI.insetsTop(8)


    init {
        layout = BorderLayout()
        myList.selectionMode = ListSelectionModel.SINGLE_SELECTION
        //myList.setCellRenderer(ExtensionRenderer())
        myList.emptyText.setText(FileTypesBundle.message("filetype.settings.no.patterns"))
        myList.border = JBUI.Borders.empty()

        val decorator: ToolbarDecorator = ToolbarDecorator.createDecorator(myList)
            .setScrollPaneBorder(JBUI.Borders.empty())
            .setPanelBorder(JBUI.Borders.customLine(JBColor.border(), 1, 1, 0, 1))
            .setAddAction {  }
            .setEditAction {  }
            .setRemoveAction {  }
            .disableUpDownActions()
        add(decorator.createPanel(), BorderLayout.NORTH)
        val scrollPane: JScrollPane = JBScrollPane(myList)
        scrollPane.border = JBUI.Borders.customLine(JBColor.border(), 0, 1, 1, 1)
        add(scrollPane, BorderLayout.CENTER)


        border = IdeBorderFactory.createTitledBorder(
            FileTypesBundle.message("filetype.registered.patterns.group"),
            false,
            TITLE_INSETS
        ).setShowLine(false)
    }

}