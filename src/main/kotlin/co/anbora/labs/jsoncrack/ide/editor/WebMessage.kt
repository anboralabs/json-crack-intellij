package co.anbora.labs.jsoncrack.ide.editor

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "event"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = IncomingMessage.Event.Initialized::class, name = "init"),
)
sealed class IncomingMessage {
    sealed class Event : IncomingMessage() {
        object Initialized : Event()
    }
}
