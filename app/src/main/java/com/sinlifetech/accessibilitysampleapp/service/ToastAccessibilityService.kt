package com.sinlifetech.accessibilitysampleapp.service

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Toast
import com.sinlifetech.accessibilitysampleapp.broadcast.Restarter
import java.util.*

/**
 * Created by dj110 on 10/21/18.
 */
class ToastAccessibilityService : AccessibilityService() {

    val TAG: String = "AService"

    var history: LinkedList<String> = LinkedList()

    override fun onDestroy() {
        super.onDestroy()
        var intent : Intent = Intent()
        intent.setAction("restartservice")
        intent.setClass(this, Restarter::class.java)
        sendBroadcast(intent)
    }

    override fun onAccessibilityEvent(p0: AccessibilityEvent?) {
        // Get Event
        val type: Int? = p0?.eventType
        val nodeInfo: AccessibilityNodeInfo? = p0?.source
        val activityNodeInfo: AccessibilityNodeInfo? = rootInActiveWindow  // Current Activity NodeInfo

        // Text
        // activityNodeInfo?.text
        // contentDescription
        // activityNodeInfo?.contentDescription
            val packagename = activityNodeInfo?.packageName
            val packagename2 = nodeInfo?.packageName

        if (packagename?.equals("com.android.chrome")!!) {
            //Log.d(TAG, "From Chrome")
            logViewHierarchy(activityNodeInfo, 0)
        }

        var typeName: String

        when (type) {
            AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED -> {
                typeName = "TYPE_NOTIFICATION_STATE_CHANGED"
            }
            AccessibilityEvent.TYPE_VIEW_CLICKED -> {
                typeName = "TYPE_VIEW_CLICKED"
            }
            AccessibilityEvent.TYPE_VIEW_FOCUSED -> {
                typeName = "TYPE_VIEW_FOCUSED"
            }
            AccessibilityEvent.TYPE_VIEW_LONG_CLICKED -> {
                typeName = "TYPE_VIEW_LONG_CLICKED"
            }
            AccessibilityEvent.TYPE_VIEW_SELECTED -> {
                typeName = "TYPE_VIEW_SELECTED"
            }
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                typeName = "TYPE_WINDOW_STATE_CHANGED"
            }
            AccessibilityEvent.TYPE_ANNOUNCEMENT -> {
                typeName = "TYPE_ANNOUNCEMENT"
            }
            AccessibilityEvent.TYPE_GESTURE_DETECTION_END -> {
                typeName = "TYPE_GESTURE_DETECTION_END"
            }
            AccessibilityEvent.TYPE_GESTURE_DETECTION_START -> {
                typeName = "TYPE_GESTURE_DETECTION_START"
            }
            AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END -> {
                typeName = "TYPE_TOUCH_EXPLORATION_GESTURE_END"
            }
            AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START -> {
                typeName = "TYPE_TOUCH_EXPLORATION_GESTURE_START"
            }
            AccessibilityEvent.TYPE_TOUCH_INTERACTION_END -> {
                typeName = "TYPE_TOUCH_INTERACTION_END"
            }
            AccessibilityEvent.TYPE_TOUCH_INTERACTION_START -> {
                typeName = "TYPE_TOUCH_INTERACTION_START"
            }
            AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED -> {
                typeName = "TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED"
            }
            AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED -> {
                typeName = "TYPE_VIEW_ACCESSIBILITY_FOCUSED"
            }
            AccessibilityEvent.TYPE_VIEW_HOVER_ENTER -> {
                typeName = "TYPE_VIEW_HOVER_ENTER"
            }
            AccessibilityEvent.TYPE_VIEW_HOVER_EXIT -> {
                typeName = "TYPE_VIEW_HOVER_EXIT"
            }
            AccessibilityEvent.TYPE_VIEW_SCROLLED -> {
                typeName = "TYPE_VIEW_SCROLLED"
            }
            AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED -> {
                typeName = "TYPE_VIEW_TEXT_SELECTION_CHANGED"
            }
            AccessibilityEvent.TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY -> {
                typeName = "TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY"
            }
            AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED -> {
                typeName = "TYPE_WINDOW_CONTENT_CHANGED"
            }
            else -> {
                typeName = "UNKNOWN_TYPE"
            }
        }
        if (packagename?.equals("com.android.chrome")!!) {
          //  toast = Toast.makeText(applicationContext, typeName, Toast.LENGTH_SHORT)
          //  toast.show()
        }
    }

    override fun onInterrupt() {
    }

    // Helpers
    fun logViewHierarchy(nodeInfo: AccessibilityNodeInfo?, depth: Int) {
        when(nodeInfo?.childCount) {
            null -> return
            0 -> {
                /*
                Log.i(TAG, "${" ".repeat(depth)} " + "View : ${nodeInfo.className} "
                + "(id=${nodeInfo.viewIdResourceName})")
*/
                if (nodeInfo.className.equals("android.widget.EditText")) {
                    val text = nodeInfo.text.toString()
                    Log.i(TAG, "Text :" + text)
                    if (!text.isEmpty() && !history.contains(text)) {
                        if (history.size < 5) {
                            history.add(text)
                        } else {
                            history.removeAt(0)
                            history.add(text)
                        }
                        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
                    }
                }

                nodeInfo.text
            }
            else -> {
                (0 until nodeInfo.childCount).map {
                    nodeInfo.getChild(it)
                }.forEach{
                    logViewHierarchy(it, depth+1)
                }
            }
        }
    }
}
