package com.sinlifetech.accessibilitysampleapp

import android.app.Activity
import android.os.Bundle

// https://www.programcreek.com/java-api-examples/?class=android.view.accessibility.AccessibilityNodeInfo&method=performAction

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}
