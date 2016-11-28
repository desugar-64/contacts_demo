package com.sergeyfitis.contactsdemo.data;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergeyfitis on 28.11.16.
 */

public class ContactsDataLoader extends AsyncTaskLoader<List<User>> {

    private static final Uri CONTACTS = Contacts.CONTENT_URI;

    private static final String[] CONTACTS_PROJECTION = {
            Contacts._ID,
            Contacts.PHOTO_THUMBNAIL_URI,
            Contacts.DISPLAY_NAME_PRIMARY,
    };

    private static final String CONTACTS_SORT = Contacts.DISPLAY_NAME_PRIMARY + " ASC";

    private static final String CONTACTS_SELECTION = ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER + " = ?";


    private static final String[] SELECTION_ARGS = {"1"};


    private static final String[] PHONES_PROJECTION = {
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };

    private static final String PHONE_SELECTION = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?";


    public ContactsDataLoader(Context context) {
        super(context);
    }

    @Override
    public List<User> loadInBackground() {
        Context context = getContext();
        List<User> users = new ArrayList<>();
        final ContentResolver cr = context.getContentResolver();
        final Cursor cursor = cr.query(CONTACTS, CONTACTS_PROJECTION, CONTACTS_SELECTION, SELECTION_ARGS, CONTACTS_SORT, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(Contacts.DISPLAY_NAME_PRIMARY));
                String photoThumb = cursor.getString(cursor.getColumnIndex(Contacts.PHOTO_THUMBNAIL_URI));
                long contactId = cursor.getLong(cursor.getColumnIndex(Contacts._ID));
                final Cursor cursorPhone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION, PHONE_SELECTION, new String[]{String.valueOf(contactId)}, null);
                String phone = null;
                if (cursorPhone != null) {
                    if (cursorPhone.moveToFirst()) {
                        phone = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                    cursorPhone.close();

                }
                User user = new User();
                user.setName(name);
                if (photoThumb != null) {
                    final Uri photo = Uri.parse(photoThumb);
                    user.setPhoto(photo);
                }
                user.setPhone(phone);
                users.add(user);
            }

            cursor.close();
        }

        return users;
    }

}
