package com.kododake.aabrowser

import android.content.Intent
import androidx.car.app.CarAppService
import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.Session
import androidx.car.app.model.Action
import androidx.car.app.model.Header
import androidx.car.app.model.Pane
import androidx.car.app.model.PaneTemplate
import androidx.car.app.model.Row
import androidx.car.app.model.Template
import androidx.car.app.validation.HostValidator

class AABrowserCarAppService : CarAppService() {
    override fun createHostValidator(): HostValidator {
        return HostValidator.ALLOW_ALL_HOSTS_VALIDATOR
    }

    override fun onCreateSession(): Session {
        return AABrowserSession()
    }
}

class AABrowserSession : Session() {
    override fun onCreateScreen(intent: Intent): Screen {
        return AABrowserCarScreen(carContext)
    }
}

class AABrowserCarScreen(carContext: CarContext) : Screen(carContext) {
    override fun onGetTemplate(): Template {
        val launchIntent = Intent(carContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        val launchAction = Action.Builder()
            .setTitle(carContext.getString(R.string.app_name))
            .setOnClickListener {
                carContext.startActivity(launchIntent)
            }
            .build()

        val pane = Pane.Builder()
            .addRow(
                Row.Builder()
                    .setTitle(carContext.getString(R.string.app_name))
                    .addText("Tap below to open AA Browser.")
                    .build()
            )
            .addAction(launchAction)
            .build()

        return PaneTemplate.Builder(pane)
            .setHeader(
                Header.Builder()
                    .setTitle(carContext.getString(R.string.app_name))
                    .build()
            )
            .build()
    }
}
