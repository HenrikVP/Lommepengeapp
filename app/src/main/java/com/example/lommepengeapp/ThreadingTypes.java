package com.example.lommepengeapp;

import static android.widget.Toast.LENGTH_LONG;

import android.app.job.JobScheduler;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadingTypes extends AppCompatActivity {
    void exampleMethod(){

        //Thread (basic, low-level)
        new Thread(() -> {

            runOnUiThread(new Thread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Posting from other thread on main thread", LENGTH_LONG).show();
                }
            }));

            // Background work
        }).start();

        //Runnable (with Thread)
        Runnable runnable = () -> {
            // Background work
        };

        new Thread(runnable).start();

        //HandlerThread (background thread with Looper)
        HandlerThread handlerThread = new HandlerThread("Worker");
        handlerThread.start();

        Handler handler = new Handler(handlerThread.getLooper());
        handler.post(() -> {
            // Background work
        });

        //ExecutorService (recommended for most cases)
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            // Background work
        });

        //With result
        ExecutorService executor2 = Executors.newSingleThreadExecutor();
        Handler mainHandler = new Handler(Looper.getMainLooper());

        executor2.execute(() -> {
            String result = "Done";

            mainHandler.post(() -> {
                // Update UI
            });
        });

        //WorkManager (best for deferrable work)
        OneTimeWorkRequest work =
                new OneTimeWorkRequest.Builder(MyWorker.class).build();

        WorkManager.getInstance(getApplicationContext()).enqueue(work);

        //JobScheduler (system-level jobs)
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
    }
}
