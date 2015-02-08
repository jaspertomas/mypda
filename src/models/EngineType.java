package models;

import java.util.ArrayList;
import java.util.Date;

import utils.MyDatabaseHelper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class EngineType {
    //------------FIELDS-----------
    public static final String tablename=EngineType.tablename;
    public static String[] fields=EngineType.fields;
    public static String[] fieldtypes=EngineType.fieldtypes;
    //-----------------------
    //-------------------------TABLE FUNCTIONS---------------------

    //-----------getter functions----------
    public static EngineType getById(Integer id) {
            ArrayList<EngineType> map=select(" where id = '"+id.toString()+"'");
            for(EngineType item:map)return item;
            return null;
    }
    public static EngineType getByName(String name)
    {
            ArrayList<EngineType> items=select(" where name = '"+name+"'");
            for(EngineType item:items)return item;
            return null;
    }	
    public static ArrayList<EngineType> selectByName(String name)
    {
            return select(" where name = '"+name+"'");
    }	
    public static EngineType getByDescription(String description)
    {
            ArrayList<EngineType> items=select(" where description = '"+description+"'");
            for(EngineType item:items)return item;
            return null;
    }	
    public static ArrayList<EngineType> selectByDescription(String description)
    {
            return select(" where description = '"+description+"'");
    }	
    public static EngineType getByParentId(Integer parent_id)
    {
            ArrayList<EngineType> items=select(" where parent_id = '"+parent_id.toString()+"'");
            for(EngineType item:items)return item;
            return null;
    }	
    public static ArrayList<EngineType> selectByParentId(Integer parent_id)
    {
            return select(" where parent_id = '"+parent_id.toString()+"'");
    }	
    //-----------database functions--------------

	public static void delete(EngineType item)
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
	public static Integer insert(EngineType item)
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
	public static void update(EngineType item)
	{
		SQLiteDatabase db = MyDatabaseHelper.getInstance().getWritableDatabase();

		db.execSQL(
		"update "+tablename+" set "+implodeFieldsWithValues(item,false)+" where id = '"+item.getId()
		+"';");
		db.close();
	}
	public static ArrayList<EngineType> select(String criteria) {
		ArrayList<EngineType> items = new ArrayList<EngineType>();
		SQLiteDatabase db = MyDatabaseHelper.getInstance()
				.getWritableDatabase();

		Cursor cursor = db.rawQuery("SELECT * FROM "+tablename+" "+criteria, null);
		items = new ArrayList<EngineType>();
		while (cursor.moveToNext()) {
			items.add(new EngineType(cursor));
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
    public static String implodeValues(EngineType item,boolean withId)
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
    public static String implodeFieldsWithValues(EngineType item,boolean withId)
    {
            ArrayList<String> values=item.implodeFieldValuesHelper(true);//get entire list of values; whether the id is included will be dealt with later.

            if(values.size()!=fields.length)
            {
                    System.err.println("EngineType:implodeFieldsWithValues(): ERROR: values length does not match fields length");
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
