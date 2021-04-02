package com.internship.spacexcrew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    RecyclerView recyclerView;
    CrewMemberAdapter memberAdapter;
    ArrayList<CrewMember> members;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String url = "https://api.spacexdata.com/v4/crew";
        try {
            makeNetworkCall(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        initRecyclerView();
        Button save = findViewById(R.id.btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
                load();
            }
        });
        Button delete = findViewById(R.id.btn2);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
    }

    private void delete(){
        CrewDatabase db = CrewDatabase.getInstance(this.getApplicationContext());
        db.clearAllTables();
        load();
    }

    private void saveCrewMember(CrewMember crewMember){
        CrewDatabase db = CrewDatabase.getInstance(this.getApplicationContext());
        db.crewDao().insert(crewMember);
    }

    private void refresh(){
        try{
        for(CrewMember member : members){
                saveCrewMember(member);
        }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initRecyclerView(){
        CrewDatabase db = CrewDatabase.getInstance(this.getApplicationContext());
        List<CrewMember> members = db.crewDao().getAllCrew();
        memberAdapter = new CrewMemberAdapter((ArrayList<CrewMember>) members);
        recyclerView = findViewById(R.id.rvUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                1);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(memberAdapter);
    }

    private void load(){
        CrewDatabase db = CrewDatabase.getInstance(this.getApplicationContext());
        List<CrewMember> members = db.crewDao().getAllCrew();
        memberAdapter.updateData((ArrayList<CrewMember>) members);
    }

    void makeNetworkCall(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();
                members = parseJSON(result);
                for(CrewMember member : members){
                        if(!members.contains(member))
                        saveCrewMember(member);
                }
            }
        });
    }


    ArrayList<CrewMember> parseJSON(String s){
        ArrayList<CrewMember> crewMembers = new ArrayList<>();

        try {
            JSONArray items = new JSONArray(s);
            for(int i =0; i<items.length(); i++){
                JSONObject object = items.getJSONObject(i);
                String id = object.getString("id");
                String name = object.getString("name");
                String agency = object.getString("agency");
                String image = object.getString("image");
                String wikipedia = object.getString("wikipedia");
                String status = object.getString("status");
                CrewMember crewMember = new CrewMember(id, name, agency, image, wikipedia, status);
                crewMembers.add(crewMember);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return crewMembers;
    }
}