package com.example.proekt_122;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoadDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_data);
        // Находим TextView для отображения результата
        final TextView resultView = findViewById(R.id.result);

        // Находим кнопку и устанавливаем на нее обработчик событий
        Button button = findViewById(R.id.btnLoadData);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLoadData(resultView);
            }
        });
    }

    public void onClickLoadData(TextView resultView) {
        // Выполняем запрос к провайдеру контента для получения данных
        Cursor cursor = getContentResolver().query(Uri.parse("content://com.demo.user.provider/users"), null, null, null, null);

        // Проверяем, есть ли данные в результате запроса
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                // Если есть, строим строку с результатом
                StringBuilder strBuild = new StringBuilder();
                int idIndex = cursor.getColumnIndex("id");
                int nameIndex = cursor.getColumnIndex("name");
                do {
                    int id = cursor.getInt(idIndex);
                    String name = cursor.getString(nameIndex);
                    strBuild.append("\n" + id + "-" + name);
                } while (cursor.moveToNext());
                // Отображаем результат в TextView
                resultView.setText(strBuild.toString());
            } else {
                // Если нет данных, показываем соответствующее сообщение
                resultView.setText("No Records Found");
            }
            cursor.close();
        } else {
            Toast.makeText(this, "Cursor is null", Toast.LENGTH_SHORT).show();
        }
    }
}
