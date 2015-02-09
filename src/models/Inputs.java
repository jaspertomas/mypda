package models;

import java.util.ArrayList;
import java.util.Date;

import utils.MyDatabaseHelper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Inputs {
    //------------FIELDS-----------
    public static final String tablename=Input.tablename;
    public static String[] fields=Input.fields;
    public static String[] fieldtypes=Input.fieldtypes;
    //-----------------------
    //-------------------------TABLE FUNCTIONS---------------------

    //-----------getter functions----------
    public static Input getById(Integer id) {
            ArrayList<Input> map=select(" where id = '"+id.toString()+"'");
            for(Input item:map)return item;
            return null;
    }
    public static Input getByContent(String content)
    {
            ArrayList<Input> items=select(" where content = '"+content+"'");
            for(Input item:items)return item;
            return null;
    }	
    public static ArrayList<Input> selectByContent(String content)
    {
            return select(" where content = '"+content+"'");
    }	
    public static Input getByDateCreated(Date date_created)
    {
            ArrayList<Input> items=select(" where date_created = '"+date_created.toString()+"'");
            for(Input item:items)return item;
            return null;
    }	
    public static ArrayList<Input> selectByDateCreated(Date date_created)
    {
            return select(" where date_created = '"+date_created.toString()+"'");
    }	
    public static Input getByDateModified(Date date_modified)
    {
            ArrayList<Input> items=select(" where date_modified = '"+date_modified.toString()+"'");
            for(Input item:items)return item;
            return null;
    }	
    public static ArrayList<Input> selectByDateModified(Date date_modified)
    {
            return select(" where date_modified = '"+date_modified.toString()+"'");
    }	
    public static Input getByIsAnalyzed(Integer is_analyzed)
    {
            ArrayList<Input> items=select(" where is_analyzed = '"+is_analyzed.toString()+"'");
            for(Input item:items)return item;
            return null;
    }	
    public static ArrayList<Input> selectByIsAnalyzed(Integer is_analyzed)
    {
            return select(" where is_analyzed = '"+is_analyzed.toString()+"'");
    }	
    public static Input getByCategoryId(Integer category_id)
    {
            ArrayList<Input> items=select(" where category_id = '"+category_id.toString()+"'");
            for(Input item:items)return item;
            return null;
    }	
    public static ArrayList<Input> selectByCategoryId(Integer category_id)
    {
            return select(" where category_id = '"+category_id.toString()+"'");
    }	
    //-----------database functions--------------

	public static void delete(Input item)
	{
		SQLiteDatabase db = MyDatabaseHelper.getInstance().getWritableDatabase();
		db.execSQL("delete from "+tablename+" where id = '"+item.getId()+"';");
		db.close();
	}
	public static void delete(Integer id)
	{
		SQLiteDatabase db = MyDatabaseHelper.getInstance().getWritableDatabase();
		db.execSQL("delete from "+tablename+" where id = '"+id+"';");
		db.close();
	}
	public static Integer insert(Input item)
	{
		SQLiteDatabase db = MyDatabaseHelper.getInstance().getWritableDatabase();
		
		if(fieldtypes[0].contains("int"))
		{
			db.execSQL("INSERT INTO "+tablename+" ("+implodeFields(false)+")VALUES (" 
					+implodeValues(item, false)
					+");");
		}
		else
		if(fieldtypes[0].contains("varchar"))
		{
			db.execSQL("INSERT INTO "+tablename+" ("+implodeFields(true)+")VALUES (" 
					+implodeValues(item, true)
					+");");
		}

            //fetch last insert id
		Cursor cursor = db.rawQuery("SELECT last_insert_rowid() FROM "+tablename+" ", null);
		cursor.moveToFirst();
		Integer result=cursor.getInt(0);
		cursor.close();

		db.close();
		return result;
	}
	public static void update(Input item)
	{
		SQLiteDatabase db = MyDatabaseHelper.getInstance().getWritableDatabase();

		db.execSQL(
		"update "+tablename+" set "+implodeFieldsWithValues(item,false)+" where id = '"+item.getId()
		+"';");
		db.close();
	}
	public static ArrayList<Input> select(String criteria) {
		ArrayList<Input> items = new ArrayList<Input>();
		SQLiteDatabase db = MyDatabaseHelper.getInstance()
				.getWritableDatabase();

		Cursor cursor = db.rawQuery("SELECT * FROM "+tablename+" "+criteria, null);
		items = new ArrayList<Input>();
		while (cursor.moveToNext()) {
			items.add(new Input(cursor));
		}
		cursor.close();
		db.close();
		return items;
	}
	public static Integer count(String criteria) {
		SQLiteDatabase db = MyDatabaseHelper.getInstance().getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT count(*) FROM "+tablename+" "+criteria, null);
		cursor.moveToFirst();
		Integer result=cursor.getInt(0);
		cursor.close();
		db.close();
		return result;
	}
	public static Integer getLastInsertId() {
		SQLiteDatabase db = MyDatabaseHelper.getInstance().getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT last_insert_rowid() FROM "+tablename+" ", null);
		cursor.moveToFirst();
		Integer result=cursor.getInt(0);
		cursor.close();
		db.close();
		return result;
	}

    //-----------database helper functions--------------
    public static String implodeValues(Input item,boolean withId)
    {
            ArrayList<String> values=item.implodeFieldValuesHelper(withId);
            String output="";
            for(String value:values)
            {
                    if(!output.isEmpty())
                            output+=",";
                    output+=(value!=null?"'"+value+"'":"null");
            }
            return output;
    }
    public static String implodeFields(boolean withId)
    {
            String output="";
            for(String field:fields)
            {
                    if(!withId && field.contentEquals("id"))continue;
                    if(!output.isEmpty())
                            output+=",";
                    output+=field;
            }
            return output;
    }
    public static String implodeFieldsWithValues(Input item,boolean withId)
    {
            ArrayList<String> values=item.implodeFieldValuesHelper(true);//get entire list of values; whether the id is included will be dealt with later.

            if(values.size()!=fields.length)
            {
                    System.err.println("Inputs:implodeFieldsWithValues(): ERROR: values length does not match fields length");
            }

            String output="";
            for(int i=0;i<fields.length;i++)
            {
                    if(!withId && fields[i].contentEquals("id"))continue;
                    if(!output.isEmpty())
                            output+=",";
                    output+=fields[i]+"="+(values.get(i)!=null?"'"+values.get(i)+"'":"null");
            }
            return output;
    }	
    public static String implodeFieldsWithTypes()
    {
            String output="";
            for(int i=0;i<fields.length;i++)
            {
                    if(fields[i].contentEquals(fields[0]))//fields[0] being the primary key
                            output+=fields[i]+" "+fieldtypes[i]+" PRIMARY KEY";
                    else
                            output+=","+fields[i]+" "+fieldtypes[i];
            }
            return output;
    }	
    public static String createTable()
    {
            return "CREATE TABLE IF NOT EXISTS "+tablename+" ("+implodeFieldsWithTypes()+" );";
    }
    public static String deleteTable()
    {
            return "DROP TABLE IF EXISTS "+tablename;
    }
}
