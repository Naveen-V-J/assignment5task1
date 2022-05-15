package com.example.assignment5task1

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*

class DBHelper(context:Context):SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    companion object{
        private val DATABASE_NAME="ITEM_REPOSITORY"
        private val DATABASE_VERSION=1
        private val TABLE_NAME="Items"
        private val ID="id"
        private val NAME="name"
        private val DETAILS="details"
        private val QTY="qty"
        private val SIZE="size"
        private val URGENT="urgent"
        private val BOUGHT="bought"
        private val DATE_BOUGHT="date_bought"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query="CREATE TABLE $TABLE_NAME($ID INTEGER PRIMARY KEY, $NAME TEXT, $DETAILS TEXT, $QTY INTEGER, $SIZE TEXT, $URGENT INTEGER, $BOUGHT INTEGER, $DATE_BOUGHT TEXT)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertItem(item:Item){
        val db=this.writableDatabase
        val contentValues=ContentValues()
        contentValues.put(NAME,item.name)
        contentValues.put(DETAILS,item.details)
        contentValues.put(QTY,item.qty)
        contentValues.put(SIZE,item.size)
        contentValues.put(URGENT,item.urgent)
        contentValues.put(BOUGHT,item.bought)

        db.insert(TABLE_NAME,null,contentValues)
        db.close()
    }

    fun updateItem(item: Item){
        val db=this.writableDatabase
        val contentValues=ContentValues()
        contentValues.put(NAME,item.name)
        contentValues.put(DETAILS,item.details)
        contentValues.put(QTY,item.qty)
        contentValues.put(SIZE,item.size)
        contentValues.put(URGENT,item.urgent)
        contentValues.put(BOUGHT,item.bought)

        db.update(TABLE_NAME,contentValues,"$ID=?", arrayOf(item.id.toString()))
        db.close()
    }

    fun updateItemBought(item: Item){
        val db=this.writableDatabase
        val contentValues=ContentValues()
        contentValues.put(BOUGHT,item.bought)
        contentValues.put(DATE_BOUGHT,item.dateBought)
        db.update(TABLE_NAME,contentValues,"$ID=?", arrayOf(item.id.toString()))
        db.close()
    }

    fun getAllItems():MutableList<Item>{
        val db=this.readableDatabase
        val itemList= mutableListOf<Item>()
        val query="SELECT * FROM $TABLE_NAME"
        val result=db.rawQuery(query,null)

        if (result.moveToFirst()){
            do{
                val id=result.getInt(result.getColumnIndex(ID).toInt())
                val name=result.getString(result.getColumnIndex(NAME).toInt())
                val details=result.getString(result.getColumnIndex(ID).toInt())
                val qty=result.getInt(result.getColumnIndex(ID).toInt())
                val size=result.getString(result.getColumnIndex(ID).toInt())
                val urgent=result.getInt(result.getColumnIndex(ID).toInt())
                val bought=result.getInt(result.getColumnIndex(ID).toInt())
                itemList.add(Item(id,name,details,qty,size, urgent,bought))
            }while (result.moveToNext())
        }
        result.close()
        db.close()
        return itemList
    }

    fun getItem(itemID:Int):Item?{
        val db=this.readableDatabase
        val query="SELECT * FROM $TABLE_NAME WHERE $ID=$itemID"
        val result=db.rawQuery(query,null)

        if (result.moveToFirst()){
            val id=result.getInt(result.getColumnIndex(ID).toInt())
            val name=result.getString(result.getColumnIndex(NAME).toInt())
            val details=result.getString(result.getColumnIndex(ID).toInt())
            val qty=result.getInt(result.getColumnIndex(ID).toInt())
            val size=result.getString(result.getColumnIndex(ID).toInt())
            val urgent=result.getInt(result.getColumnIndex(ID).toInt())
            val bought=result.getInt(result.getColumnIndex(ID).toInt())
            result.close()
            db.close()
            return Item(
                id,name,details,qty,size,urgent,bought
            )
        }else{
            db.close()
            result.close()
            return null
        }
    }

    fun deleteItem(id:Int){
        val db=this.writableDatabase
        db.delete(TABLE_NAME,"$ID=?", arrayOf(id.toString()))
        db.close()
    }


    fun getItemsWhereBought(isBought:Boolean):MutableList<Item>{
        val db=this.readableDatabase
        val itemList= mutableListOf<Item>()
        val query="SELECT * FROM $TABLE_NAME WHERE $BOUGHT=$isBought"
        val result=db.rawQuery(query,null)

        if (result.moveToFirst()){
            do{
                val id=result.getInt(result.getColumnIndex(ID).toInt())
                val name=result.getString(result.getColumnIndex(NAME).toInt())
                val details=result.getString(result.getColumnIndex(ID).toInt())
                val qty=result.getInt(result.getColumnIndex(ID).toInt())
                val size=result.getString(result.getColumnIndex(ID).toInt())
                val urgent=result.getInt(result.getColumnIndex(ID).toInt())
                val bought=result.getInt(result.getColumnIndex(ID).toInt())
                itemList.add(Item(id,name,details,qty,size, urgent,bought))
            }while (result.moveToNext())
        }
        result.close()
        db.close()
        return itemList
    }

    fun getItemsWhereUrgent(isUrgent:Boolean):MutableList<Item>{
        val db=this.readableDatabase
        val itemList= mutableListOf<Item>()
        val query="SELECT * FROM $TABLE_NAME WHERE $URGENT=$isUrgent"
        val result=db.rawQuery(query,null)

        if (result.moveToFirst()){
            do{
                val id=result.getInt(result.getColumnIndex(ID).toInt())
                val name=result.getString(result.getColumnIndex(NAME).toInt())
                val details=result.getString(result.getColumnIndex(ID).toInt())
                val qty=result.getInt(result.getColumnIndex(ID).toInt())
                val size=result.getString(result.getColumnIndex(ID).toInt())
                val urgent=result.getInt(result.getColumnIndex(ID).toInt())
                val bought=result.getInt(result.getColumnIndex(ID).toInt())
                itemList.add(Item(id,name,details,qty,size, urgent,bought))
            }while (result.moveToNext())
        }
        result.close()
        db.close()
        return itemList
    }
}