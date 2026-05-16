package co.anbora.labs.jsoncrack.ide.webView

import org.cef.callback.CefCallback
import org.cef.callback.CefResourceReadCallback
import org.cef.callback.CefResourceSkipCallback
import org.cef.handler.CefResourceHandler
import org.cef.misc.BoolRef
import org.cef.misc.IntRef
import org.cef.misc.LongRef
import org.cef.misc.StringRef
import org.cef.network.CefRequest
import org.cef.network.CefResponse
import java.io.IOException
import java.io.InputStream
import java.net.URI

class JSONCrackCefResourceHandler(
    private val uri: URI,
    private val myStream: InputStream?
): CefResourceHandler, CefResourceHandlerCompatibility {
    override fun processRequest(req: CefRequest, callback: CefCallback): Boolean {
        callback.Continue()
        return true
    }

    override fun open(
        p0: CefRequest?,
        p1: BoolRef?,
        p2: CefCallback?
    ): Boolean = false

    override fun getResponseHeaders(response: CefResponse, responseLength: IntRef, redirectUrl: StringRef?) {
        if (uri.path.endsWith(".html")) {
            response.mimeType = "text/html"
        } else if (uri.path.endsWith(".js")) {
            response.mimeType = "application/javascript"
        } else if (uri.path.endsWith(".css")) {
            response.mimeType = "text/css"
        } else if (uri.path.endsWith(".svg")) {
            response.mimeType = "image/svg+xml"
        }

        if (myStream === null) {
            response.status = 404
        } else {
            response.status = 200
        }
    }

    override fun readResponse(dataOut: ByteArray, bytesToRead: Int, bytesRead: IntRef, callback: CefCallback): Boolean {
        if (myStream === null) {
            bytesRead.set(0)
            return false
        }

        try {
            val availableSize = myStream.available()
            return if (availableSize > 0) {
                bytesRead.set(myStream.read(dataOut, 0, bytesToRead.coerceAtMost(availableSize)))
                true
            } else {
                bytesRead.set(0)
                try {
                    myStream.close()
                } catch (_: IOException) {}

                false
            }
        } catch (ex: IOException) {
            return false
        }
    }

    override fun read(
        p0: ByteArray?,
        p1: Int,
        p2: IntRef?,
        p3: CefResourceReadCallback?
    ): Boolean = false

    override fun skip(
        p0: Long,
        p1: LongRef?,
        p2: CefResourceSkipCallback?
    ): Boolean = false

    override fun cancel() {}
}