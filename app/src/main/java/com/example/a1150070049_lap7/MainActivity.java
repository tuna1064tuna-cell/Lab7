
package com.example.a1150070049_lap7;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edtUrl = findViewById(R.id.edtUrl);
        Button btnDownload = findViewById(R.id.btnDownload);

        btnDownload.setOnClickListener(v -> {
            String url = edtUrl.getText().toString().trim();
            if (url.isEmpty() || !url.startsWith("http")) {
                Toast.makeText(this, "URL không hợp lệ!", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent svc = new Intent(this, DownloadService.class);
            svc.putExtra("url", url);
            startForegroundService(svc);
        });
    }
}
