package com.sergeyfitis.contactsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

public class AddContactActivity extends AppCompatActivity {

    private final static int REQUEST_CODE = 22;

    private TextInputLayout tilName;
    private TextInputLayout tilPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        tilName = (TextInputLayout) findViewById(R.id.tilContactName);
        tilPhone = (TextInputLayout) findViewById(R.id.tilPhone);

        tilPhone.getEditText().addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }

    private void createContact() {
        String name = tilName.getEditText().getText().toString();
        String phone = tilPhone.getEditText().getText().toString();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone)) {
            Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
            intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
            intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
            intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME);
            intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
            intent.putExtra("finishActivityOnSaveCompleted", true);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_contact_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            createContact();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
