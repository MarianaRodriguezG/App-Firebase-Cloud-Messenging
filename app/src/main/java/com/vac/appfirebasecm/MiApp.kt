package com.vac.appfirebasecm

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

class MiApp : Application() {

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "notification_fcm"
    }

    override fun onCreate() {
        super.onCreate()

        // ✅ Mostrar token en Logcat
        Firebase.messaging.token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM_TOKEN", "❌ No se pudo obtener el token", task.exception)
                return@addOnCompleteListener
            }

            val token = task.result
            Log.d("FCM_TOKEN", "✅ Token generado: $token")
        }

        // ✅ Crear canal de notificación para Android 8+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Notificaciones FCM",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Canal para mensajes push de Firebase"
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(canal)
        }
    }
}
