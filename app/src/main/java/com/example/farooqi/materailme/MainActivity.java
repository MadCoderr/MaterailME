package com.example.farooqi.materailme;

import android.content.res.TypedArray;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private static final String LIST_STATE_KEY = "KEY";

    //Member variables
    private RecyclerView mRecyclerView;
    private ArrayList<Sport> mSportsData;
    private SportsAdapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;
    private Parcelable mListState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MainActivity", "onCreate()");

        //Initialize the RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //Set the Layout Manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //Initialize the ArrayLIst that will contain the data
        mSportsData = new ArrayList<>();

        //Initialize the adapter and set it ot the RecyclerView
        mAdapter = new SportsAdapter(this, mSportsData);
        mRecyclerView.setAdapter(mAdapter);

        initializeData();

        if (savedInstanceState != null) {
            Log.d("MainActivity", "savedInstance is not null!");
            mListState = savedInstanceState.getParcelable(LIST_STATE_KEY);
        } else {
            Log.d("MainActivity", "savedInstance is not null!");
        }


        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback
                        (ItemTouchHelper.UP | ItemTouchHelper.DOWN
                                | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
                                , ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        // get the original and target index from the 2nd and 3rd argument passed
                        final int fromPosition = viewHolder.getAdapterPosition();
                        final int toPosition = target.getAdapterPosition();

                        Collections.swap(mSportsData, fromPosition, toPosition);
                        mAdapter.notifyItemMoved(fromPosition, toPosition);

                        return true;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        mSportsData.remove(viewHolder.getAdapterPosition());
                        mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    }
                });

        helper.attachToRecyclerView(mRecyclerView);
    }

    /**
     * Method for initializing the sports data from resources.
     */
    private void initializeData() {
        Log.d("MainActivity", "initailizeData()");
        //Get the resources from the XML file
        String[] sportsList = getResources().getStringArray(R.array.sports_titles);
        String[] sportsInfo = getResources().getStringArray(R.array.sports_info);
        String[] sportsDesc = getResources().getStringArray(R.array.sports_description);

        //Clear the existing data (to avoid duplication)
        mSportsData.clear();

        TypedArray sportImageResources =
                getResources().obtainTypedArray(R.array.sports_image);

        //Create the ArrayList of Sports objects with the titles and information about each sport
        for (int i = 0; i < sportsList.length; i++) {
            mSportsData.add(new
                    Sport(sportsList[i], sportsInfo[i],
                    sportsDesc[i], sportImageResources.getResourceId(i, 0)));
        }

        sportImageResources.recycle();

        //Notify the adapter of the change
        mAdapter.notifyDataSetChanged();
    }

    public void resetSports(View view) {
        initializeData();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("MainActivity", "onSavedInstanceState");
        mListState = mLayoutManager.onSaveInstanceState();
        outState.putParcelable(LIST_STATE_KEY, mListState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        Log.d("MainActivity", "onRestoreInstanceState");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("MainActivity", "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity", "onResume");
        if (mListState != null) {
            mLayoutManager.onRestoreInstanceState(mListState);
            mAdapter.notifyDataSetChanged();
        } else {
            Log.d("MainActivity", "mListState is null");
        }
    }
}
