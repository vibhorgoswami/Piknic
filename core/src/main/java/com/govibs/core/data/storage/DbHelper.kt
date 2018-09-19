package com.govibs.core.data.storage

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception
import androidx.annotation.NonNull
import com.govibs.core.data.model.ContactModel
import com.govibs.core.data.model.GroupModel



class DbHelper private constructor(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {

        private val DB_VERSION = 1
        private val DB_NAME = "db_picnic"
        private var mInstance: DbHelper? = null

        fun getInstance(context: Context): DbHelper {
            if (null == mInstance) {
                mInstance = DbHelper(context)
            }
            return mInstance as DbHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(ContactTable.CREATE_TABLE)
        db.execSQL(GroupTable.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    fun getGroupList(): ArrayList<GroupModel> {
        var groupModelList = ArrayList<GroupModel>(1)
        var cursor: Cursor? = null
        try {
            cursor = readableDatabase.query(GroupTable.tableName, null, null,
                    null, null, null, null)
            if (null == cursor || !cursor.moveToNext()) {
                return groupModelList
            }
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                groupModelList.add(getGroupModelFromCursor(cursor))
                cursor.moveToNext()
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        } finally {
            if (null != cursor && !cursor.isClosed) {
                cursor.close()
            }
        }
        return groupModelList
    }

    /**
     * Get group model from cursor
     * @param cursor data
     * @return GroupModel
     */
    private fun getGroupModelFromCursor(@NonNull cursor: Cursor): GroupModel {
        val groupModel = GroupModel(cursor.getString(cursor.getColumnIndex(GroupTable.columnName)))
        groupModel.setGroupId(cursor.getInt(cursor.getColumnIndex(GroupTable.columnId)))
        // TODO groupModel.setContactModelList(getContacts(groupModel.getGroupId()))
        groupModel.setLunchTime(cursor.getLong(cursor.getColumnIndex(GroupTable.columnLunchTime)))
        groupModel.setGroupLeader(cursor.getInt(cursor.getColumnIndex(GroupTable.columnGroupLeader)))
        return groupModel
    }

    /**
     * Get Contact model from cursor
     * @param cursor data
     * @return ContactModel
     */
    private fun getContactModel(@NonNull cursor: Cursor): ContactModel {
        val contactModel = ContactModel(cursor.getString(cursor.getColumnIndex(ContactTable.columnName)))
        contactModel.setGroupId(cursor.getInt(cursor.getColumnIndex(ContactTable.columnGroupId)))
        contactModel.setEmail(cursor.getString(cursor.getColumnIndex(ContactTable.columnEmail)))
        contactModel.setPhoneNo(cursor.getString(cursor.getColumnIndex(ContactTable.columnPhone)))
        contactModel.setAvatarUri(cursor.getString(cursor.getColumnIndex(ContactTable.columnUri)))
        contactModel.setId(cursor.getInt(cursor.getColumnIndex(ContactTable.columnId)))
        return contactModel
    }
}