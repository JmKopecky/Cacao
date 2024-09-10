package dev.prognitio.cacao;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class AssignmentNotifWorker extends Worker {

    public AssignmentNotifWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        createNotif();
        return Result.success();
    }
    
    public static void createNotif() {
        System.out.println("Hello from notifWorker!");
    }
}
