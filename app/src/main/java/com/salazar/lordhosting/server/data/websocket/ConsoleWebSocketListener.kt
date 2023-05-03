package com.salazar.lordhosting.server.data.websocket

import android.util.Log
import com.salazar.lordhosting.server.data.response.EventResponse
import com.salazar.lordhosting.server.data.response.WebSocketEvent
import com.squareup.moshi.Moshi
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

class ConsoleWebSocketListener @Inject constructor(
    private val token: String,
    private val onEvent: (WebSocketEvent) -> Unit
) : WebSocketListener() {
    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)

        // Create a JSON object with the authentication token
        val jsonObject = JSONObject()
        jsonObject.put("event", "auth")
        jsonObject.put("args", JSONArray().put(token))

        // Send the JSON object as a string to the WebSocket
        webSocket.send(jsonObject.toString())
        Log.d("WEBSOCKET MESSAGE", "WEBSOCKET OPEN")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)

        Log.d(TAG, text)

        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(EventResponse::class.java)
        val res = adapter.fromJson(text) ?: return

        when (res.event) {
            "console output" -> {
                val args = res.args?.firstOrNull() ?: return
                val output = WebSocketEvent.ConsoleOutput(output = args)
                // handle server stats event
                onEvent(output)
            }
            "stats" -> {
                val adapter = moshi.adapter(WebSocketEvent.Stats::class.java)
                val args = res.args?.firstOrNull() ?: return
                val stats = adapter.fromJson(args) ?: return
                // handle server stats event
                onEvent(stats)
            }
        }
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)

        Log.d(TAG, "onClosed")
        Log.d(TAG, reason)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)

        Log.d(TAG, "onFailure")
        Log.d(TAG, t.message.toString())
        Log.d(TAG, response?.message ?: "")
    }

    companion object {
        const val TAG = "WEBSOCKET"
    }
}