package com.example.background.workers

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.background.R
import timber.log.Timber

class BlurWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    @SuppressLint("RestrictedApi")
    override fun doWork(): Result {
       val appContext = applicationContext
        makeStatusNotification("Blurring image", appContext)

        return try {
            val picture = BitmapFactory.decodeResource(
                    appContext.resources,
                    R.drawable.test
            )

            val output = blurBitmap(picture, appContext)

            // Write bitmap to a temp file
            val outputUri = writeBitmapToFile(appContext, output)

            makeStatusNotification("Output is $outputUri", appContext)
            Result.Success()
        } catch (t: Throwable) {
            Timber.e(t)
            Result.failure()
        }
        
    }
}