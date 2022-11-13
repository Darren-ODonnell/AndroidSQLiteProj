package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Database.DBHandler;
import com.example.myapplication.Models.CurrentUser;
import com.example.myapplication.Models.Task;
import com.example.myapplication.Models.User;
import com.example.myapplication.Models.UserTask;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskActivity extends AppCompatActivity {

    DBHandler dbHandler;
    List<Task> taskList;
    TaskAdapter adapter;

    EditText categoryET, descriptionET, deadlineET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        dbHandler = new DBHandler(this);

        findViewById(R.id.createTaskButton).setOnClickListener(v -> addTask());
        createRecyclerView();


    }

    public void createRecyclerView(){
        taskList = new ArrayList<>();
        taskList = dbHandler.getTasks(CurrentUser.getInstance().getUser());
        adapter = new TaskAdapter();
        RecyclerView recyclerView = findViewById(R.id.taskRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setResults(dbHandler.getAllTasks());
    }

    private void addTask(){
        categoryET = findViewById(R.id.category);
        descriptionET = findViewById(R.id.description);
        deadlineET = findViewById(R.id.deadline);

        String category = categoryET.getText().toString();
        String description = descriptionET.getText().toString();
        String deadline = deadlineET.getText().toString();
        String status = "incomplete";

        Task task = new Task();
        task.setCategory(category);
        task.setDescription(description);
        task.setStatus(status);

        Pattern pattern = Pattern.compile("^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$");
        Matcher matcher = pattern.matcher(deadline);

            if(matcher.find()){
                task.setDeadlineAsString(deadline);
                dbHandler.insert(task);
                UserTask ut = createUserTask(task);
                dbHandler.insert(ut);
                adapter.setResults(dbHandler.getAllTasks());
            }else
                Toast.makeText(this, "Date invalid: use dd/mm/yyyy format", Toast.LENGTH_SHORT).show();
    }

    public UserTask createUserTask(Task t){
        User u = CurrentUser.getInstance().getUser();
        UserTask ut = new UserTask();
        ut.setTask(t);
        ut.setUser(u);
        return ut;
    }
}
