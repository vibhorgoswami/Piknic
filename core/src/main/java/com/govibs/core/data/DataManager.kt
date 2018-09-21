package com.govibs.core.data

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import androidx.annotation.WorkerThread
import com.govibs.core.data.model.ContactModel
import com.govibs.core.data.model.GroupModel
import com.govibs.core.data.storage.DbHelper
import io.reactivex.Single


class DataManager private constructor() {

    companion object {

        private var mInstance : DataManager? = null

        /**
         * Singleton
         */
        fun getInstance(): DataManager {
            if (null == mInstance) {
                mInstance = DataManager()
            }
            return mInstance as DataManager
        }

    }


    fun getGroupList(context: Context): Single<ArrayList<GroupModel>> {
        return Single.fromCallable { DbHelper.getInstance(context).getGroupList() }
    }


    @WorkerThread
    fun getContactList(context: Context): ArrayList<ContactModel> {
        val contactModelList = ArrayList<ContactModel>()
        var cursor: Cursor? = null
        try {
            cursor = context.contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
            // loop over all contacts
            cursor?.let { phones ->
                while (phones.moveToNext()) {
                    // get contact info
                    var phoneNumber: String? = null
                    val id = phones.getString(phones.getColumnIndex(ContactsContract.Contacts._ID))
                    val name = phones.getString(phones.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val avatarUriString = phones.getString(phones.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI))

                    // get phone number
                    if (Integer.parseInt(phones.getString(phones.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        var pCur: Cursor? = null
                        try {
                            pCur = context.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id), null)
                            while (pCur != null && pCur.moveToNext()) {
                                phoneNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                            }
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                        } finally {
                            if (null != pCur && !pCur.isClosed) {
                                pCur.close()
                            }
                        }

                        pCur!!.close()

                    }

                    val contactChip = ContactModel(name)
                    contactChip.setId(Integer.parseInt(id))
                    contactChip.setPhoneNo("$phoneNumber")
                    contactChip.setAvatarUri(avatarUriString ?: "")
                    // add contact to the list
                    contactModelList.add(contactChip)
                }
            }
            cursor?.close()
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            if (cursor != null && !cursor.isClosed) {
                cursor.close()
            }
        }
        return contactModelList
    }

}