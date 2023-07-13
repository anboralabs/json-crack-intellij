package co.anbora.labs.jsoncrack.ide.services

import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.ui.jcef.JBCefApp


@Service(Service.Level.PROJECT)
class ProjectService(project: Project) {

    init {
        if (!JBCefApp.isSupported()) {
            thisLogger().info(project.name)
            thisLogger().warn("It's version is not compatible with your IDE.")
        }
    }
}
