package com.example.robert.ormlitetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robert.ormlitetest.data.DatabaseHelper;
import com.example.robert.ormlitetest.data.Email;
import com.example.robert.ormlitetest.data.SimpleData;
import com.example.robert.ormlitetest.data.User;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.android.apptools.OrmLiteBaseListActivity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ItemSelect;
import org.androidannotations.annotations.ViewById;
import org.w3c.dom.Text;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@EActivity
public class MainActivity extends OrmLiteBaseActivity<DatabaseHelper> {
    private List<SimpleData> dataItems;

    @ViewById(R.id.listView_items)
    ListView lv;

    @Click({R.id.floatingActionButton})
    void buttonClick() {
        // get our dao
        RuntimeExceptionDao<SimpleData, Integer> simpleDao = getHelper().getSimpleDataDao();

        final String test = "test";
        long millis = System.currentTimeMillis();
        SimpleData simple = new SimpleData(millis);

        // store it in the database
        simpleDao.create(simple);
        CreateNewUser();

        RefreshContext();
    }

    @ItemClick(R.id.listView_items)
    void ItemClicked(SimpleData data) {

        // get our dao
        RuntimeExceptionDao<SimpleData, Integer> simpleDao = getHelper().getSimpleDataDao();
        simpleDao.delete(data);

        RefreshContext();
    }

    private void CreateNewUser(){
        Dao<User, Integer> userDao = null;
        try {
            userDao = getHelper().getUserDao();
            User user = new User().setName("Clause");
            userDao.create(user);

            final Dao<Email, Integer> emailsDao = getHelper().getEmailDao();
            emailsDao.create(new Email(user).setEmail("my@example.com"));
            emailsDao.create(new Email(user).setEmail("my2@example.com"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RefreshContext();

//
//        doSampleDatabaseStuff("onCreate", tv);
//        setContentView(tv);
    }

    private List<SimpleData> getDatabaseItems() {
        // get our dao
        RuntimeExceptionDao<SimpleData, Integer> simpleDao = getHelper().getSimpleDataDao();

        // query for all of the data objects in the database
        List<SimpleData> list = simpleDao.queryForAll();

        Dao<User, Integer> userDao = null;
        try {
            userDao = getHelper().getUserDao();
            final List<User> userList = userDao.queryForAll();
            for (User user : userList) {
                System.out.println("user = " + user);
                final ForeignCollection<Email> emailList = user.getEmails();
                for (Email email : emailList) {
                    System.out.println("email = " + email);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return list;
    }


    private void RefreshContext() {
        dataItems = getDatabaseItems();

        List<SimpleData> data = new ArrayList<SimpleData>();
        SimpleAdapter adapter = new SimpleAdapter(this, dataItems);
        lv.setAdapter(adapter);
    }
    /**
     * Do our sample database stuff.
     */
//    private void doSampleDatabaseStuff(String action, TextView tv) {
//        // get our dao
//        RuntimeExceptionDao<SimpleData, Integer> simpleDao = getHelper().getSimpleDataDao();
//        // query for all of the data objects in the database
//        List<SimpleData> list = simpleDao.queryForAll();
//        // our string builder for building the content-view
//        StringBuilder sb = new StringBuilder();
//        sb.append("Found ").append(list.size()).append(" entries in DB in ").append(action).append("()\n");
//
//        // if we already have items in the database
//        int simpleC = 1;
//        for (SimpleData simple : list) {
//            sb.append('#').append(simpleC).append(": ").append(simple).append('\n');
//            simpleC++;
//        }
//        sb.append("------------------------------------------\n");
//        sb.append("Deleted ids:");
//        for (SimpleData simple : list) {
//            simpleDao.delete(simple);
//            sb.append(' ').append(simple.id);
//            Log.i(LOG_TAG, "deleting simple(" + simple.id + ")");
//            simpleC++;
//        }
//        sb.append('\n');
//        sb.append("------------------------------------------\n");
//
//        int createNum;
//        do {
//            createNum = new Random().nextInt(MAX_NUM_TO_CREATE) + 1;
//        } while (createNum == list.size());
//        sb.append("Creating ").append(createNum).append(" new entries:\n");
//        for (int i = 0; i < createNum; i++) {
//            // create a new simple object
//            long millis = System.currentTimeMillis();
//            SimpleData simple = new SimpleData(millis);
//            // store it in the database
//            simpleDao.create(simple);
//            Log.i(LOG_TAG, "created simple(" + millis + ")");
//            // output it
//            sb.append('#').append(i + 1).append(": ");
//            sb.append(simple).append('\n');
//            try {
//                Thread.sleep(5);
//            } catch (InterruptedException e) {
//                // ignore
//            }
//        }
//
//        tv.setText(sb.toString());
//        Log.i(LOG_TAG, "Done with page at " + System.currentTimeMillis());
//    }
}