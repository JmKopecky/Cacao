package dev.prognitio.cacao;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import dev.prognitio.cacao.activities.CourseDisplayActivity;

public class AssignmentNotifWorker extends Worker {

    public AssignmentNotifWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        System.out.println("Hello from notifWorker!");
        Assignment assignment = Assignment.fromString(getInputData().getString("assignment"));

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        CharSequence name = "cacao_assignment_notif_channel";
        String description = "Assignment notification of due date channel for Cacao.";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("cacao_assignment", name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(getApplicationContext(), NotificationManager.class);
        notificationManager.createNotificationChannel(channel);


        Intent intent = new Intent(getApplicationContext(), CourseDisplayActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "cacao_assignment")
                .setSmallIcon(R.drawable.cacao_logo)
                .setContentTitle("Assignment Due: " + assignment.title)
                .setContentText("Your assignment (" + assignment.title + ") is due on " + assignment.dueDate + ". If you have turned it in, mark it as complete in Cacao.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        notificationManager.notify(assignment.title, 0, builder.build());


        return Result.success();
    }
}
