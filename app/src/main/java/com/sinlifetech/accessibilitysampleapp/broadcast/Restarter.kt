package com.sinlifetech.accessibilitysampleapp.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import com.sinlifetech.accessibilitysampleapp.service.ToastAccessibilityService


class Restarter : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
        Toast.makeText(p0, "Service restarted", Toast.LENGTH_SHORT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            p0?.startForegroundService(Intent(p0, ToastAccessibilityService::class.java))
        } else {
            p0?.startService(Intent(p0, ToastAccessibilityService::class.java))
        }
    }
}