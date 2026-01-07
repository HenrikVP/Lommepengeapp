package com.example.lommepengeapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import java.io.File;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initGUI();

        Gson gson;
    }

    void initGUI() {
        EditText input = findViewById(R.id.edit_inputField);
        TextView textField = findViewById(R.id.txt_notMyFirstTextField);
        Button myFirstButton = findViewById(R.id.btn_hugoButton);
        myFirstButton.setOnClickListener(v -> {
            sendToSecondActivity(input.getText().toString());
        });
    }

    void sendToSecondActivity(String text) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra("ourInput", text);
        startActivity(intent);
    }

    void sendEmail() {
        Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
        mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"hpo@tec.dk"});
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, "Very important");
        mailIntent.putExtra(Intent.EXTRA_TEXT, "A nigerian prince have blah blah");

        //Attachments
        Uri attachmentUri = FileProvider.getUriForFile(
                this,
                getPackageName() + ".provider",
                new File("exampleFile")
        );

        mailIntent.putExtra(Intent.EXTRA_STREAM, attachmentUri);
        mailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivity(mailIntent);
    }

    void openBrowser() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage("com.android.chrome");
        intent.setData(Uri.parse("https://www.google.com"));

        startActivity(intent);

        //Alternatively
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
        browserIntent.setPackage("com.android.chrome");

        if (browserIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(browserIntent);
        } else {
            // Fallback to default browser
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com")));
        }


    }

    void createCalenderEvent(){
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);

        intent.putExtra(CalendarContract.Events.TITLE, "Meeting");
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "Project discussion");
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Office");

        long startTime = Calendar.getInstance().getTimeInMillis();
        long endTime = startTime + (60 * 60 * 1000); // +1 hour

        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);

        startActivity(intent);
    }


}