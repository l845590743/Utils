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

		// 创建数据库
        mMySQLite = new MySQLite(this, DBNAME, null, VERSON);
        SQLiteDatabase database = mMySQLite.getReadableDatabase();

        database.close();
    }

		   【插入数据】
    public void insert1(View view) {
        SQLiteDatabase database = mMySQLite.getWritableDatabase();
        String sql = "insert into t_user (c_name,c_age,c_phone) values(?,?,?)";
        database.execSQL(sql, new Object[]{"zhangsan", 23, "888888"});
    }

    public void insert2(View view) {
        SQLiteDatabase database = mMySQLite.getWritableDatabase();
        // 参数二 插入的数据不能全为空，
        ContentValues values = new ContentValues();
        values.put("c_name","lisi");
        values.put("c_age",25);
        values.put("c_phone","666666");
        long insert = database.insert("t_user", null, values);
        if (insert == -1) {
            Toast.makeText(MainActivity.this, "数据插入失败！", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(MainActivity.this, "插入成功，id为"+insert, Toast.LENGTH_SHORT).show();
        }
    }


			【删除数据】
    public void delete1(View view){
        SQLiteDatabase database = mMySQLite.getReadableDatabase();
        //删除年龄大于30的记录
        String sql = "delete from t_user where c_age>?";
        database.execSQL(sql, new Object[]{30});

    }

    public void delete2(View view){
        SQLiteDatabase database = mMySQLite.getReadableDatabase();

        int delete = database.delete(TABLE_NAME, "c_age>?", new String[]{"30"});

        Toast.makeText(this, "删除了"+delete+"条记录", Toast.LENGTH_SHORT).show();

    }

			 【更新数据】
    public void update1(View view){
        SQLiteDatabase database = mMySQLite.getReadableDatabase();

        String sql = "update t_user set c_name=? where c_age<?";

        database.execSQL(sql, new Object[]{"hehe",30});


    }

    public void update2(View view){
        SQLiteDatabase database = mMySQLite.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("c_name", "hehe");
        //返回值：更改的条数
        int update = database.update(TABLE_NAME, values, "c_age<?", new String[]{"30"});
        Toast.makeText(this, "update="+update, Toast.LENGTH_SHORT).show();

    }
			 【查询数据】
    public void query1(View view){
        SQLiteDatabase database = mMySQLite.getReadableDatabase();

        //查询所有age>18的用户
        String sql ="select c_phone,c_name,c_age from t_user where c_age>? ";
        
        Cursor cursor = database.rawQuery(sql, new String[]{"18"}); // 纯sql语句

        List<User> users = new ArrayList<User>();
       
        while(cursor.moveToNext()){
            User user = new User();
            //获取当前行的各个字段
            user.phone = cursor.getString(0);
            // 获取字段的索引值
            // int columnIndex = cursor.getColumnIndex("c_name");
            // Log.d("tag", "c_name字段的索引值="+columnIndex);
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
        //遍历cursor
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
	   
	   
	   
	   【事务，要么全成功，要么全失败】
	   //给每人分1000元
	public void sendMoney(View view){
		SQLiteDatabase database = sqLiteOpenHelper.getReadableDatabase();
		
		String sql = "update t_user set c_money = 1000";
		
		database.execSQL(sql);
		
		Toast.makeText(this, "分钱成功", Toast.LENGTH_SHORT).show();
	}
	
	//让boboyu给daivwei转500元
	public void changeMoney(View view){
		SQLiteDatabase database = sqLiteOpenHelper.getReadableDatabase();
		
		/*
		 * 1. 开启事务，之后的所有操作都是临时内存中进行的
		 */
		database.beginTransaction();
		
		String sql = "update t_user set c_money=c_money-500 where c_name=?";
		//2. 扣除boboyu500元
		database.execSQL(sql,new Object[]{"boboyu"});
		Log.d("tag", "扣除了boboyu500元");
		
		
//		int i = 1/0;//运行时异常
		 
		
		String sql2 = "update t_user set c_money=c_money+500 where c_name=?";
		//3. 给daiwei加500元
		database.execSQL(sql2,new String[]{"daiwei"});
		Log.d("tag", "给daiwei加了500元");
		
		/*
		 * 4. 提交事务，将临时内存中的数据同步更新到数据库文件中
		 */
		database.setTransactionSuccessful();
		/*
		 * 5. 结束事务
		 */
		database.endTransaction();
		
		
	}
    }
