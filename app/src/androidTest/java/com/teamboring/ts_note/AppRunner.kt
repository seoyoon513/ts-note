package com.teamboring.ts_note

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class AppRunner: AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, TSNoteApp::class.java.name, context)
    }
}