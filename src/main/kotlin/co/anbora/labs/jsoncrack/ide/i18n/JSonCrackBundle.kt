package co.anbora.labs.jsoncrack.ide.i18n

import com.intellij.DynamicBundle
import org.jetbrains.annotations.NonNls

object JSonCrackBundle {

    @NonNls
    private val INSTANCE: DynamicBundle = DynamicBundle(JSonCrackBundle::class.java, "messages.jsoncrack")

    fun message(key: String): String {
        return INSTANCE.getMessage(key)
    }

}