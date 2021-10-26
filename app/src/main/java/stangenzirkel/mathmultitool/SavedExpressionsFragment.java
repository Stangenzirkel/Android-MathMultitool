package stangenzirkel.mathmultitool;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

import stangenzirkel.mathmultitool.converter.Converter;

public class SavedExpressionsFragment extends Fragment {
    RecyclerView recyclerView;

    public SavedExpressionsFragment() {
    }

    @SuppressWarnings("unused")
    public static SavedExpressionsFragment newInstance(int columnCount) {
        SavedExpressionsFragment fragment = new SavedExpressionsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(CalculatorDB.tag, "1");
        View view = inflater.inflate(R.layout.fragment_saved_expressions_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            initializeAdapter();
        }
        return view;
    }

    protected void initializeAdapter(){
        recyclerView.setAdapter(new ExpressionRecyclerViewAdapter(Arrays.asList(CalculatorDB.getInstance().getExpressions().clone()), this));
    }


}