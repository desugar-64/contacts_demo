package com.sergeyfitis.contactsdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sergeyfitis.contactsdemo.data.ContactsDataLoader;
import com.sergeyfitis.contactsdemo.data.User;

import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<User>> {

    private static final int READ_CONTACTS_REQUEST = 1;

    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rvContacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (ActivityCompat.checkSelfPermission(this, READ_CONTACTS) == PERMISSION_GRANTED) {
            getSupportLoaderManager().initLoader(1, null, this).forceLoad();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, READ_CONTACTS)) {
                showExplanationDialog();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{READ_CONTACTS}, READ_CONTACTS_REQUEST);
            }
        }

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 29.11.16 Implement it
                Toast.makeText(MainActivity.this, "Не встиг зробити :)", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
        return new ContactsDataLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> data) {
        updateUI(data);
    }

    @Override
    public void onLoaderReset(Loader<List<User>> loader) {
        updateUI(null);
    }

    public void updateUI(List<User> users) {
        progressBar.setVisibility(View.GONE);
        if (recyclerView.getAdapter() == null) {
            recyclerView.setAdapter(new ContactsAdapter(users));
        } else {
            ContactsAdapter adapter = (ContactsAdapter) recyclerView.getAdapter();
            adapter.updateAdapter(users);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == READ_CONTACTS_REQUEST) {
            if (grantResults[0] == PERMISSION_GRANTED) {
                getSupportLoaderManager().initLoader(1, null, this).forceLoad();
            } else {
                showExplanationDialog();
            }
        }

    }

    private void showExplanationDialog() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.read_contacts_err)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{READ_CONTACTS}, READ_CONTACTS_REQUEST);
                    }
                })
                .setNegativeButton(R.string.go_to_settings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openPermissionSettings();
                    }
                })
                .create()
                .show();
    }

    private void openPermissionSettings() {
        final Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivity(intent);
    }
}
