package com.example.sqldb;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String DBNAME  = "user.db";
    private static final int VERSON     = 1;
    private static final String TABLE_NAME = "t_user";

    private MySQLite mMySQLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		// �������ݿ�
        mMySQLite = new MySQLite(this, DBNAME, null, VERSON);
        SQLiteDatabase database = mMySQLite.getReadableDatabase();

        database.close();
    }

		   ���������ݡ�
    public void insert1(View view) {
        SQLiteDatabase database = mMySQLite.getWritableDatabase();
        String sql = "insert into t_user (c_name,c_age,c_phone) values(?,?,?)";
        database.execSQL(sql, new Object[]{"zhangsan", 23, "888888"});
    }

    public void insert2(View view) {
        SQLiteDatabase database = mMySQLite.getWritableDatabase();
        // ������ ��������ݲ���ȫΪ�գ�
        ContentValues values = new ContentValues();
        values.put("c_name","lisi");
        values.put("c_age",25);
        values.put("c_phone","666666");
        long insert = database.insert("t_user", null, values);
        if (insert == -1) {
            Toast.makeText(MainActivity.this, "���ݲ���ʧ�ܣ�", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(MainActivity.this, "����ɹ���idΪ"+insert, Toast.LENGTH_SHORT).show();
        }
    }


			��ɾ�����ݡ�
    public void delete1(View view){
        SQLiteDatabase database = mMySQLite.getReadableDatabase();
        //ɾ���������30�ļ�¼
        String sql = "delete from t_user where c_age>?";
        database.execSQL(sql, new Object[]{30});

    }

    public void delete2(View view){
        SQLiteDatabase database = mMySQLite.getReadableDatabase();

        int delete = database.delete(TABLE_NAME, "c_age>?", new String[]{"30"});

        Toast.makeText(this, "ɾ����"+delete+"����¼", Toast.LENGTH_SHORT).show();

    }

			 ���������ݡ�
    public void update1(View view){
        SQLiteDatabase database = mMySQLite.getReadableDatabase();

        String sql = "update t_user set c_name=? where c_age<?";

        database.execSQL(sql, new Object[]{"hehe",30});


    }

    public void update2(View view){
        SQLiteDatabase database = mMySQLite.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("c_name", "hehe");
        //����ֵ�����ĵ�����
        int update = database.update(TABLE_NAME, values, "c_age<?", new String[]{"30"});
        Toast.makeText(this, "update="+update, Toast.LENGTH_SHORT).show();

    }
			 ����ѯ���ݡ�
    public void query1(View view){
        SQLiteDatabase database = mMySQLite.getReadableDatabase();

        //��ѯ����age>18���û�
        String sql ="select c_phone,c_name,c_age from t_user where c_age>? ";
        
        Cursor cursor = database.rawQuery(sql, new String[]{"18"}); // ��sql���

        List<User> users = new ArrayList<User>();
       
        while(cursor.moveToNext()){
            User user = new User();
            //��ȡ��ǰ�еĸ����ֶ�
            user.phone = cursor.getString(0);
            // ��ȡ�ֶε�����ֵ
            // int columnIndex = cursor.getColumnIndex("c_name");
            // Log.d("tag", "c_name�ֶε�����ֵ="+columnIndex);
            // user.name = cursor.getString(columnIndex);

            user.name = cursor.getString(1);

            user.age = cursor.getInt(2);

            users.add(user);
        }
        cursor.close();

        Log.d("tag", users.toString());

    }

    public void query2(View view) {
        List<User> users = new ArrayList<User>();
        SQLiteDatabase database = mMySQLite.getReadableDatabase();
        
        Cursor cursor = database.query(TABLE_NAME, new String[]{"c_phone", "c_name", "c_age"}, "c_age>?", new String[]{"18"}, null, null, null);
        //����cursor
        while (cursor.moveToNext()) {
            User user = new User();
            user.phone = cursor.getString(0);
            user.age = cursor.getInt(2);
            user.name = cursor.getString(1);

            users.add(user);
        }
        cursor.close();
        Log.d("tag", users.toString());
       }
	   
	   
	   
	   ������Ҫôȫ�ɹ���Ҫôȫʧ�ܡ�
	   //��ÿ�˷�1000Ԫ
	public void sendMoney(View view){
		SQLiteDatabase database = sqLiteOpenHelper.getReadableDatabase();
		
		String sql = "update t_user set c_money = 1000";
		
		database.execSQL(sql);
		
		Toast.makeText(this, "��Ǯ�ɹ�", Toast.LENGTH_SHORT).show();
	}
	
	//��boboyu��daivweiת500Ԫ
	public void changeMoney(View view){
		SQLiteDatabase database = sqLiteOpenHelper.getReadableDatabase();
		
		/*
		 * 1. ��������֮������в���������ʱ�ڴ��н��е�
		 */
		database.beginTransaction();
		
		String sql = "update t_user set c_money=c_money-500 where c_name=?";
		//2. �۳�boboyu500Ԫ
		database.execSQL(sql,new Object[]{"boboyu"});
		Log.d("tag", "�۳���boboyu500Ԫ");
		
		
//		int i = 1/0;//����ʱ�쳣
		 
		
		String sql2 = "update t_user set c_money=c_money+500 where c_name=?";
		//3. ��daiwei��500Ԫ
		database.execSQL(sql2,new String[]{"daiwei"});
		Log.d("tag", "��daiwei����500Ԫ");
		
		/*
		 * 4. �ύ���񣬽���ʱ�ڴ��е�����ͬ�����µ����ݿ��ļ���
		 */
		database.setTransactionSuccessful();
		/*
		 * 5. ��������
		 */
		database.endTransaction();
		
		
	}
    }
