package com.example.android.homechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.location.Location;

import com.example.android.homechat.ServerCommunication.Database;
import com.example.android.homechat.ServerCommunication.GroupEventListener;

import java.util.ArrayList;

import static com.example.android.homechat.ServerCommunication.Database.attachDatabaseReadListener;

public class JoinActivity extends AppCompatActivity {
    private static final String TAG = "JoinActivity";

    ArrayList<Group> groupList = new ArrayList<Group>();
    Group selectedGroup = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        final ListView availableGroupsLV = (ListView) findViewById(R.id.availableGroupsLV);

        availableGroupsLV.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return groupList.size();
            }

            @Override
            public Object getItem(int i) {
                return groupList.get(i);
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                Group curGroup = groupList.get(i);
                if(view == null)
                    view = getLayoutInflater().inflate(R.layout.group_layout, viewGroup, false);
                TextView groupNameTV = view.findViewById(R.id.groupNameTV);
                groupNameTV.setText(curGroup.getName());
                TextView groupLoctionTV = view.findViewById(R.id.groupLoctionTV);
                groupLoctionTV.setText("N: "+curGroup.getLat()+" E: "+curGroup.getLon());
                return view;
            }
        });

        availableGroupsLV.setOnItemClickListener(new OnItemCL());

        GroupEventListener gev = new GroupEventListener() {
            @Override
            public void onGroupAdded(@NonNull Group g) {
                groupList.add(g);
                Log.d(TAG, "msg is: "+g);
                ((BaseAdapter)availableGroupsLV.getAdapter()).notifyDataSetChanged();
            }
        };
        Database.attachDatabaseReadListener(gev);
    }
    protected class OnItemCL implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        }
    };
}
