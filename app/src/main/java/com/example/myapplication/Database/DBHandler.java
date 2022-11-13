package com.example.myapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Models.Task;
import com.example.myapplication.Models.User;
import com.example.myapplication.Models.UserTask;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "BookDB";
    public static final int DATABASE_VERSION = 1;

    public DBHandler( Context context ) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
        createTables(this.getWritableDatabase());
    }
    @Override
    public void onCreate( SQLiteDatabase db ) {
        createTables(db);
    }

    public void createTables(SQLiteDatabase db){
        dropTables(db);
        String sqlStatement =
                "CREATE TABLE IF NOT EXISTS User(" +
                        "id Integer Primary key AUTOINCREMENT," +
                        "email Text, " +
                        "name Text, " +
                        "password Text, " +
                        "phoneNumber Text)" ;
        db.execSQL(sqlStatement);

        sqlStatement =
                "CREATE TABLE IF NOT EXISTS Task(" +
                        "id Integer Primary key AUTOINCREMENT," +
                        "status Text, " +
                        "category Text," +
                        "description Text, " +
                        "deadline Integer)";
        db.execSQL(sqlStatement);

        sqlStatement =
                "CREATE TABLE IF NOT EXISTS UserTask(" +
                        "id Integer Primary key AUTOINCREMENT," +
                        "userId Integer, " +
                        "taskId Integer, " +
                        "FOREIGN KEY(userId) REFERENCES User(id), " +
                        "FOREIGN KEY(taskId) REFERENCES Task(id))";

        db.execSQL(sqlStatement);
    }

    @Override
    public void onUpgrade( SQLiteDatabase db,
                           int oldVersion, int newVersion ) {
        dropTables(db);
        createTables(db);
    }

    private void dropTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS UserTask");
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Task");
    }

//    public void deleteUser(String id){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String sql = "DELETE FROM User WHERE id = ?";
//        db.rawQuery(sql, new String[]{id});
//    }
//
//    public void deleteTask(String id){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String sql = "DELETE FROM Task WHERE id = ?";
//        db.rawQuery(sql, new String[]{id});
//    }
//
//    public void deleteUserTask(String id){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String sql = "DELETE FROM UserTask WHERE id = ?";
//        db.rawQuery(sql, new String[]{id});
//    }
//
//    public void update(String id, User u){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String sql = "UPDATE User SET(name = ?, password = ?, phoneNumber = ?) WHERE id = ?";
//        db.rawQuery(sql, new String[]{u.getName(), u.getPassword(), u.getPhoneNumber(), id});
//    }
//
//    public void update(String id, UserTask ut){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String sql = "UPDATE User SET(UserId = ?, TaskId = ?) WHERE id = ?";
//        db.rawQuery(sql, new String[]{
//                String.valueOf(ut.getUser().getId()),
//                String.valueOf(ut.getTask().getId()),
//                id
//        });
//    }
//
//    public void update(String id, Task t){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String sql = "UPDATE Task SET(status = ?, category = ?, description = ?, deadline = ?) WHERE id = ?";
//        db.rawQuery(sql, new String[]{t.getStatus(), t.getCategory(), t.getDescription(), t.getDeadlineAsMilliString(), id});
//    }
//
//    public User getUserById(String id){
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c = db.rawQuery("SELECT * FROM user WHERE ID = ?", new String[] {id});
//        List<User> list = extractUserFromCursor(c);
//        return list.get(0);
//    }
//
//    public UserTask getUserTaskById(String id){
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c = db.rawQuery("SELECT * FROM UserTask WHERE ID = ?", new String[] {id});
//        List<UserTask> list = extractUserTaskFromCursor(c);
//        return list.get(0);
//    }
//
//    public Task getTaskById(String id){
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c = db.rawQuery("SELECT * FROM Task WHERE ID = ?", new String[] {id});
//        List<Task> list = extractTaskFromCursor(c);
//        return list.get(0);
//    }
//
    public List<Task> getAllTasks(){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM Task", null);
            List<Task> list = extractTaskFromCursor(c);
            return list;
    }
//
//    public List<User> getAllUsers(){
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c = db.rawQuery("SELECT * FROM User", null);
//        List<User> list = extractUserFromCursor(c);
//        return list;
//    }
//
//    public List<UserTask> getAllUserTasks(){
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c = db.rawQuery("SELECT * FROM UserTask", null);
//        List<UserTask> list = extractUserTaskFromCursor(c);
//        return list;
//    }
//
//    private List<UserTask> extractUserTaskFromCursor(Cursor cursor) {
//        List<UserTask> list = new ArrayList<>();
//        while(cursor.moveToNext()){
//            UserTask u = new UserTask();
//            u.setId(cursor.getInt(0));
//            u.setUser(
//                    getUserById(
//                            Integer.toString(
//                                    cursor.getInt(1))));
//            u.setTask(
//                    getTaskById(
//                            Integer.toString(
//                                    cursor.getInt(2))));
//
//            list.add(u);
//        }
//        return list;
//    }
//
    private List<Task> extractTaskFromCursor(Cursor cursor) {
        List<Task> tasks = new ArrayList<>();
        while(cursor.moveToNext()){
            Task t = new Task();
            t.setId(cursor.getInt(0));
            t.setStatus(cursor.getString(1));
            t.setCategory(cursor.getString(2));
            t.setDescription(cursor.getString(3));
            t.setDeadline(new Date(cursor.getLong(4)));

            tasks.add(t);
        }
        return tasks;
    }
//
//    private List<User> extractUserFromCursor(Cursor cursor) {
//        List<User> users = new ArrayList<>();
//        if(cursor.moveToFirst()){
//            do{
//                User u = new User();
//                u.setId(cursor.getInt(0));
//                u.setName(cursor.getString(1));
//                u.setPhoneNumber(cursor.getString(2));
//
//                users.add(u);
//            }while(cursor.moveToNext());
//        }
//        return users;
//    }

    public List<Task> getTasks(User user){//Uses CurrentUser
        SQLiteDatabase db = this.getWritableDatabase();
        List<Task> tasks = new ArrayList<>();
        //Get UserTasks with userId;
        //Get Tasks from UserTasks.taskId
        return getAllTasks();

    }

    public boolean insert(Task t){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("category", t.getCategory());
        contentValues.put("description", t.getDescription());
        contentValues.put("deadline", t.getDeadlineAsMilliString());
        contentValues.put("status", t.getStatus());

        long result = db.insert("task", null, contentValues);
        if(result == -1)
            return false;
        else
            return true;

    }

    public boolean insert(UserTask u) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("taskId", u.getTask().getId());
        contentValues.put("userId", u.getUser().getId());

        long result = db.insert("userTask", null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insert(User u){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", u.getName());
        contentValues.put("password", u.getPassword());
        contentValues.put("phoneNumber", u.getPhoneNumber());
        contentValues.put("email", u.getEmail());
        long result = db.insert("user", null, contentValues);
        if(result == -1)
            return false;
        else
            return true;

    }

    public boolean checkUsername(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "select * from user where name = ?", new String[]{username}
        );

            if (cursor.getCount() > 0) {
                return true;
            }
        return false;
    }

    public boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "Select * from user where name = ? and password = ?", new String[]{username, password}
        );
        if(cursor.getCount() > 0){
            return true;
        }
        return false;
    }

}
