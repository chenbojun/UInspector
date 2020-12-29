package com.huya.mobile.uinspector

import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import androidx.annotation.AnyThread
import androidx.annotation.MainThread
import androidx.core.content.ContextCompat
import com.huya.mobile.uinspector.lifecycle.UInspectorLifecycle
import com.huya.mobile.uinspector.mask.UInspectorMask
import com.huya.mobile.uinspector.notification.UInspectorNotificationService
import com.huya.mobile.uinspector.notification.UInspectorNotificationService.Companion.PENDING_RUNNING
import com.huya.mobile.uinspector.state.UInspectorState
import com.huya.mobile.uinspector.util.log
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author YvesCheung
 * 2020/12/28
 */
@Suppress("unused")
object UInspector {

    //public api -----------------------------------------------------------------------------------

    @JvmField
    val currentState = UInspectorState()

    @JvmStatic
    @AnyThread
    fun create(context: Context) {
        if (init.compareAndSet(false, true)) {
            application = context.applicationContext as Application
            application.registerActivityLifecycleCallbacks(lifecycle)
            stop()
        }
    }

    @JvmStatic
    @AnyThread
    fun destroy() {
        if (init.compareAndSet(true, false)) {
            application.unregisterActivityLifecycleCallbacks(lifecycle)
            application.stopService(Intent(application, UInspectorNotificationService::class.java))
        }
    }

    @JvmStatic
    @AnyThread
    fun start() = startService(true)

    @JvmStatic
    @AnyThread
    fun stop() = startService(false)

    //private impl ---------------------------------------------------------------------------------

    private lateinit var application: Application

    private val init = AtomicBoolean(false)

    private val lifecycle = UInspectorLifecycle()

    private fun startService(running: Boolean) {
        if (!init.get()) throw IllegalStateException("UInspector has not been init")
        ContextCompat.startForegroundService(
            application,
            Intent(application, UInspectorNotificationService::class.java)
                .putExtra(PENDING_RUNNING, running)
        )
    }

    @MainThread
    internal fun changeStateByService(running: Boolean) {
        val oldView = currentState.view
        if (running) { //Add a [UInspectorMask] into rootView
            lifecycle.runInActivity { activity ->
                val rootView =
                    requireNotNull(activity.findViewById<ViewGroup>(android.R.id.content))
                if (oldView != null) {
                    if (oldView.parent !== rootView) { //State not right!! remove the old view
                        (oldView.parent as? ViewGroup)?.removeView(oldView)
                    } else {
                        return@runInActivity //Already added.
                    }
                }

                currentState.view = UInspectorMask(activity).also { newView ->
                    rootView.addView(newView)
                }
            }
        } else { //Remove the old [UInspectorMask]
            if (oldView != null) {
                (oldView.parent as? ViewGroup)?.removeView(oldView)
            }
            currentState.view = null
        }

        currentState.isRunning = running
        log("change state to ${if (running) "RUNNING" else "IDLE"}")
    }
}