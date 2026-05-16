package co.anbora.labs.jsoncrack.ide.webView

import org.cef.callback.CefCallback
import org.cef.callback.CefResourceReadCallback
import org.cef.callback.CefResourceSkipCallback
import org.cef.misc.BoolRef
import org.cef.misc.IntRef
import org.cef.misc.LongRef
import org.cef.network.CefRequest

interface CefResourceHandlerCompatibility {

    fun open(var1: CefRequest?, var2: BoolRef?, var3: CefCallback?): Boolean

    fun read(var1: ByteArray?, var2: Int, var3: IntRef?, var4: CefResourceReadCallback?): Boolean

    fun skip(var1: Long, var3: LongRef?, var4: CefResourceSkipCallback?): Boolean

}