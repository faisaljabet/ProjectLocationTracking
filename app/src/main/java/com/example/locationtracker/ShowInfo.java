package com.example.locationtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


public class ShowInfo extends MainActivity {

    private static final String url = "http://10.23.175.249:8080/MobileSilencer/json_user_fetch.php";
    private TextView project_name2;
    private TextView categories2;
    private TextView descriptions2;
    private TextView affiliated_agencies2;
    private TextView project_start_times2;
    private TextView project_completion_times2;
    private TextView total_budgets2;
    private TextView completion_percentages2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "Hello "+id, Toast.LENGTH_LONG);
        Log.i("Mashrafi-1", id+"");
        setContentView(R.layout.activity_show_info);
        initView();
    }

    private void initView() {
        project_name2 = findViewById(R.id.project_name2);
        project_name2.setText(project_names.get(id)+"\n");

        categories2 = findViewById(R.id.categories2);
        categories2.setText(categories.get(id)+"\n");

        descriptions2 = findViewById(R.id.description2);
        descriptions2.setText(descriptions.get(id)+"\n");

        affiliated_agencies2 = findViewById(R.id.affiliated_agency2);
        affiliated_agencies2.setText(affiliated_agencies.get(id)+"\n");

        project_start_times2 = findViewById(R.id.project_start_time2);
        project_start_times2.setText(project_start_times.get(id)+"\n");

        project_completion_times2 = findViewById(R.id.project_completion_time2);
        project_completion_times2.setText(project_completion_times.get(id)+"\n");

        total_budgets2 = findViewById(R.id.total_budget2);
        total_budgets2.setText(total_budgets.get(id)+"\n");

        completion_percentages2 = findViewById(R.id.completion_percentage2);
        completion_percentages2.setText(completion_percentages.get(id)+"\n");
    }
}