package com.example.classattendance;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.classattendance.UserModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="dbClassAttendance.db";
    private static final int DATABASE_VERSION = 4;

    public static final String COLUMN_ID = "_id";

    private static final String TABLE_USER="user";
    private static final String COLUMN_NAME="name";
    private static final String COLUMN_SURNAME="surname";
    private static final String COLUMN_PROFESSOR= "is_professor";
    private static final String COLUMN_EMAIL="email";
    private static final String COLUMN_PASSWORD="password";


    private static final String TABLE_SUBJECT="subject";
    protected static final String COLUMN_SUBJECT_NAME="subjectName";

    private static final String TABLE_CLASSES="class";
    private static final String COLUMN_CLASS_TITLE="classTitle";
    private static final String COLUMN_START_TIMESTAMP = "class_start_timestamp";
    private static final String COLUMN_END_TIMESTAMP = "class_end_timestamp";

    public static final String TABLE_ATTENDANCE = "attendance";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_SUBJECT_ID = "subject_id";
    public static final String COLUMN_CLASS_ID = "class_id";

    public static final String TABLE_USER_SUBJECT = "usersubject";
    public static final String TABLE_SURVEY = "survey";
    public static final String COLUMN_GRADE = "grade";
    public static final String COLUMN_COMMENT = "comment";
    // Create tables
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME+" TEXT, "
            + COLUMN_SURNAME+" TEXT, "
            + COLUMN_PROFESSOR + " TEXT DEFAULT 'false',"
            + COLUMN_EMAIL+" TEXT, "
            + COLUMN_PASSWORD + " TEXT"
            + ")";

    private static final String CREATE_TABLE_SUBJECT = "CREATE TABLE " + TABLE_SUBJECT + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_SUBJECT_NAME + " TEXT"
            + ")";

    private static final String CREATE_TABLE_CLASSES = "CREATE TABLE " + TABLE_CLASSES + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CLASS_TITLE + " TEXT,"
            + COLUMN_START_TIMESTAMP+ " TEXT,"
            + COLUMN_END_TIMESTAMP+ " TEXT,"
            + COLUMN_SUBJECT_ID + " INTEGER,"
            + " FOREIGN KEY (" + COLUMN_SUBJECT_ID + ") REFERENCES " + TABLE_SUBJECT + "(" + COLUMN_ID + ")"
            + ")";

    private static final String CREATE_TABLE_ATTENDANCE = "CREATE TABLE " + TABLE_ATTENDANCE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_ID + " INTEGER,"
            + COLUMN_CLASS_ID + " INTEGER,"
            + " FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_ID + "),"
            + " FOREIGN KEY (" + COLUMN_CLASS_ID + ") REFERENCES " + TABLE_CLASSES + "(" + COLUMN_ID + ")"
            + ")";

    private static final String CREATE_TABLE_USER_SUBJECT = "CREATE TABLE " + TABLE_USER_SUBJECT + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_SUBJECT_ID + " INTEGER,"
            + COLUMN_USER_ID + " INTEGER,"
            + " FOREIGN KEY (" + COLUMN_SUBJECT_ID + ") REFERENCES " + TABLE_SUBJECT + "(" + COLUMN_ID + "),"
            + " FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_ID + ")"
            + ")";

    private static final String CREATE_TABLE_SURVEY = "CREATE TABLE " + TABLE_SURVEY + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CLASS_ID + " INTEGER,"
            + COLUMN_USER_ID + " INTEGER,"
            + COLUMN_GRADE + " TEXT, "
            + COLUMN_COMMENT + " TEXT,"
            + " FOREIGN KEY (" + COLUMN_CLASS_ID + ") REFERENCES " + TABLE_CLASSES + "(" + COLUMN_ID + "),"
            + " FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_ID + ")"
            + ")";

    SQLiteDatabase database;

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database=getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_USER);
            db.execSQL(CREATE_TABLE_SUBJECT);
            db.execSQL(CREATE_TABLE_CLASSES);
            db.execSQL(CREATE_TABLE_ATTENDANCE);
            db.execSQL(CREATE_TABLE_USER_SUBJECT);
            db.execSQL(CREATE_TABLE_SURVEY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDANCE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_SUBJECT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SURVEY);
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    public boolean RegisterUser(UserModel um){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put(COLUMN_NAME, um.getName());
        cv.put(COLUMN_SURNAME,um.getSurname());
        cv.put(COLUMN_PROFESSOR,um.getProfessor());
        cv.put(COLUMN_EMAIL,um.getEmail());
        cv.put(COLUMN_PASSWORD,um.getPassword());

        long insert=db.insert(TABLE_USER,null,cv);
        db.close();

        if(insert==-1) return false;
        else return true;
    }
    public Boolean checkEmail(String email){
        SQLiteDatabase info=this.getWritableDatabase();
        Cursor cursor=info.rawQuery("Select * from "+TABLE_USER+" where "+COLUMN_EMAIL+" =? ", new String[]{email});

        if(cursor.getCount()>0) return true;
        else return false;
    }
    @SuppressLint("Range")
    public int checkLogin(String email, String password){
        SQLiteDatabase info=this.getReadableDatabase();
        String[] columns={COLUMN_ID};
        String selection=COLUMN_EMAIL + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs={email, password};

        Cursor cursor=info.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);
        int userId = -1;

        if (cursor!=null && cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            cursor.close();
        }
        info.close();
        return userId;

    }
    @SuppressLint("Range")
    public Boolean checkIsProfessor(String email, String password){
        SQLiteDatabase info=this.getWritableDatabase();
        Cursor cursor=info.rawQuery("Select " + COLUMN_PROFESSOR + " from "+TABLE_USER+" where "+COLUMN_EMAIL+" =? and "+COLUMN_PASSWORD+" =? ", new String[]{email, password});
        boolean isProfessor = false;
        if (cursor.moveToFirst()) {
            int columnIndexProfessor = cursor.getColumnIndex(String.valueOf(COLUMN_PROFESSOR));
            if (columnIndexProfessor != -1) { // User exists
                String professorValue = cursor.getString(columnIndexProfessor);
                isProfessor = professorValue.equals("true");
            }
        }
        cursor.close();
        return isProfessor;
    }
    @SuppressLint("Range")
    public UserModel getUserById(int userId) {
        SQLiteDatabase db=this.getReadableDatabase();
        String[] columns={COLUMN_ID, COLUMN_NAME, COLUMN_SURNAME, COLUMN_EMAIL};
        String selection=COLUMN_ID + " = ?";
        String[] selectionArgs={String.valueOf(userId)};

        Cursor cursor=db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);
        UserModel user=null;

        if (cursor!=null && cursor.moveToFirst()) {
            user = new UserModel();
            user.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            user.setSurname(cursor.getString(cursor.getColumnIndex(COLUMN_SURNAME)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
            cursor.close();
        }
        db.close();
        return user;
    }

    @SuppressLint("Range")
    public SubjectModel getSubjectById(int subjectId) {
        SQLiteDatabase db=this.getReadableDatabase();
        String[] columns={COLUMN_ID, COLUMN_SUBJECT_NAME};
        String selection=COLUMN_ID + " = ?";
        String[] selectionArgs={String.valueOf(subjectId)};
        Cursor cursor=db.query(TABLE_SUBJECT, columns, selection, selectionArgs, null, null, null);
        SubjectModel s=null;
        if (cursor!=null && cursor.moveToFirst()) {

            s = new SubjectModel();
            s.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            s.setName(cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT_NAME)));
            cursor.close();
        }
        db.close();
        return s;
    }
    @SuppressLint("Range")
    public ClassModel getClassById(int classId) {
        SQLiteDatabase db=this.getReadableDatabase();
        String[] columns={COLUMN_ID, COLUMN_CLASS_TITLE};
        String selection=COLUMN_ID + " = ?";
        String[] selectionArgs={String.valueOf(classId)};
        Cursor cursor=db.query(TABLE_CLASSES, columns, selection, selectionArgs, null, null, null);
        ClassModel c=null;
        if (cursor!=null && cursor.moveToFirst()) {

            c = new ClassModel();
            c.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            c.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_CLASS_TITLE)));
            cursor.close();
        }
        db.close();
        return c;
    }

    public boolean AddSubject(SubjectModel sm){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put(COLUMN_SUBJECT_NAME, sm.getName());

        long insert=db.insert(TABLE_SUBJECT,null,cv);
        db.close();
        if(insert==-1) return false;
        else return true;
    }

    public Cursor getSubjects(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * from "+TABLE_SUBJECT, null);
        return cursor;
    }
    public List<ClassModel> getClassesForSubject(int subjectId) {
        List<ClassModel> classes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " +
                TABLE_CLASSES + "." + COLUMN_CLASS_TITLE + ", " +
                TABLE_CLASSES + "." + COLUMN_START_TIMESTAMP + ", " +
                TABLE_CLASSES + "." + COLUMN_END_TIMESTAMP + ", " +
                TABLE_CLASSES + "." + COLUMN_ID +
                " FROM " + TABLE_CLASSES +
                " WHERE " + TABLE_CLASSES + "." + COLUMN_SUBJECT_ID + " = ?"+
                " ORDER BY " + TABLE_CLASSES + "." + COLUMN_START_TIMESTAMP + " ASC";
        Cursor cursor=db.rawQuery(query, new String[]{String.valueOf(subjectId)});
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COLUMN_CLASS_TITLE));
                @SuppressLint("Range") String startTimestamp = cursor.getString(cursor.getColumnIndex(COLUMN_START_TIMESTAMP));
                @SuppressLint("Range") String endTimestamp = cursor.getString(cursor.getColumnIndex(COLUMN_END_TIMESTAMP));
                @SuppressLint("Range") int id=Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                ClassModel classModel = new ClassModel(id, title, startTimestamp, endTimestamp);
                classes.add(classModel);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return classes;
    }

    public Cursor getSubjectsForUser(int userId){
        SQLiteDatabase db=this.getReadableDatabase();
        String query = "SELECT " + TABLE_SUBJECT + "." + COLUMN_ID + ", " +
                TABLE_SUBJECT + "." + COLUMN_SUBJECT_NAME +
                " FROM " + TABLE_SUBJECT +
                " JOIN " + TABLE_USER_SUBJECT +
                " ON " + TABLE_SUBJECT + "." + COLUMN_ID + " = " + TABLE_USER_SUBJECT + "." + COLUMN_SUBJECT_ID +
                " WHERE " + TABLE_USER_SUBJECT + "." + COLUMN_USER_ID + " = ?";

        Cursor cursor=db.rawQuery(query, new String[]{String.valueOf(userId)});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor getSubjectsThatUserIsNotEnrolledTo(int userId){
        SQLiteDatabase db=this.getReadableDatabase();
        String query = "SELECT " + DatabaseHandler.TABLE_SUBJECT + "." + DatabaseHandler.COLUMN_ID + ", " +
                DatabaseHandler.TABLE_SUBJECT + "." + DatabaseHandler.COLUMN_SUBJECT_NAME +
                " FROM " + DatabaseHandler.TABLE_SUBJECT +
                " WHERE " + DatabaseHandler.TABLE_SUBJECT + "." + DatabaseHandler.COLUMN_ID +
                " NOT IN (SELECT " + DatabaseHandler.COLUMN_SUBJECT_ID +
                " FROM " + DatabaseHandler.TABLE_USER_SUBJECT +
                " WHERE " + DatabaseHandler.COLUMN_USER_ID + " = ?)";

        Cursor cursor=db.rawQuery(query, new String[]{String.valueOf(userId)});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public boolean enrollUserInSubject(int userId, int subjectId){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_USER_ID, userId);
        cv.put(COLUMN_SUBJECT_ID, subjectId);
        long result = db.insert(TABLE_USER_SUBJECT, null, cv);
        db.close();
        if(result!=-1) return true;
        else  return false;
    }

    public boolean addClass(int subjectId, String title, long start,long end){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_SUBJECT_ID, subjectId);
        cv.put(COLUMN_CLASS_TITLE, title);
        cv.put(COLUMN_START_TIMESTAMP, start);
        cv.put(COLUMN_END_TIMESTAMP, end);
        long insert = db.insert(TABLE_CLASSES, null, cv);
        db.close();
        if(insert!=-1) return true;
        else  return false;
    }
    @SuppressLint("Range")
    public String getSubjectForClass(int classId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " +
                TABLE_SUBJECT + "." + COLUMN_SUBJECT_NAME +
                " FROM " + TABLE_SUBJECT +
                " JOIN " + TABLE_CLASSES +
                " ON " + TABLE_SUBJECT + "." + COLUMN_ID + " = " + TABLE_CLASSES + "." + COLUMN_SUBJECT_ID +
                " WHERE " + TABLE_CLASSES + "." + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(classId)});
        String subjectName=null;
        if (cursor != null && cursor.moveToFirst()) {
            subjectName=cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT_NAME));
            cursor.close();
        }
        db.close();
        return subjectName;
    }
    public List<ClassModel> getClassesForUser(int userId) {
        List<ClassModel> classes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " +
                TABLE_CLASSES + "." + COLUMN_CLASS_TITLE + ", " +
                TABLE_CLASSES + "." + COLUMN_START_TIMESTAMP + ", " +
                TABLE_CLASSES + "." + COLUMN_END_TIMESTAMP + ", " +
                TABLE_CLASSES + "." + COLUMN_ID +
                " FROM " + TABLE_CLASSES +
                " JOIN " + TABLE_SUBJECT +
                " ON " + TABLE_CLASSES + "." + COLUMN_SUBJECT_ID + " = " + TABLE_SUBJECT + "." + COLUMN_ID +
                " JOIN " + TABLE_USER_SUBJECT +
                " ON " + TABLE_SUBJECT + "." + COLUMN_ID + " = " + TABLE_USER_SUBJECT + "." + COLUMN_SUBJECT_ID +
                " WHERE " + TABLE_USER_SUBJECT + "." + COLUMN_USER_ID + " = ?"+
                " ORDER BY " + TABLE_CLASSES + "." + COLUMN_START_TIMESTAMP + " ASC";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COLUMN_CLASS_TITLE));
                @SuppressLint("Range") String startTimestamp = cursor.getString(cursor.getColumnIndex(COLUMN_START_TIMESTAMP));
                @SuppressLint("Range") String endTimestamp = cursor.getString(cursor.getColumnIndex(COLUMN_END_TIMESTAMP));
                @SuppressLint("Range") int id=Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                ClassModel classModel = new ClassModel(id, title, startTimestamp, endTimestamp);
                classes.add(classModel);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return classes;
    }
    public boolean NewSurvey(SurveyModel sm,int userId, int classId){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put(COLUMN_GRADE, sm.getGrade());
        cv.put(COLUMN_COMMENT,sm.getComment());
        cv.put(COLUMN_USER_ID,userId);
        cv.put(COLUMN_CLASS_ID,classId);

        long insert=db.insert(TABLE_SURVEY,null,cv);
        db.close();

        if(insert==-1) return false;
        else return true;
    }
    public boolean newAttendance(int userId, int classId){
        SQLiteDatabase db=this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_ATTENDANCE +
                " WHERE " + COLUMN_USER_ID + " = ? AND " + COLUMN_CLASS_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), String.valueOf(classId)});

        if (cursor != null && cursor.getCount() == 0) {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_USER_ID, userId);
            cv.put(COLUMN_CLASS_ID, classId);
            long result=db.insert(TABLE_ATTENDANCE, null, cv);
            cursor.close();
            db.close();
            if(result!=-1) return true;
            else  return false;
        } else return true;
    }
    public List<UserModel>getAttendedStudentsForClass(int classId){
        List<UserModel> students = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + TABLE_USER + "." + COLUMN_ID + ", " +
                TABLE_USER+ "." + COLUMN_NAME + ", " +
                TABLE_USER+ "." + COLUMN_SURNAME +
                " FROM " + TABLE_USER +
                " JOIN " + TABLE_ATTENDANCE +
                " ON " + TABLE_USER + "." + COLUMN_ID + " = " + TABLE_ATTENDANCE + "." + COLUMN_USER_ID +
                " WHERE " + TABLE_ATTENDANCE + "." + COLUMN_CLASS_ID + " = ?";
        Cursor cursor=db.rawQuery(query, new String[]{String.valueOf(classId)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.COLUMN_ID));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_NAME));
                @SuppressLint("Range") String surname = cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_SURNAME));

                UserModel user = new UserModel(id,name,surname,null,null,null);
                students.add(user);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return students;
    }
}
