// Create an explicit intent for an Activity in your app.
Intent intent = new Intent(this, AlertDetails.class);
intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
.setSmallIcon(R.drawable.notification_icon)
.setContentTitle("My notification")
.setContentText("Hello World!")
.setPriority(NotificationCompat.PRIORITY_DEFAULT)
// Set the intent that fires when the user taps the notification.
.setContentIntent(pendingIntent)
.setAutoCancel(true);
