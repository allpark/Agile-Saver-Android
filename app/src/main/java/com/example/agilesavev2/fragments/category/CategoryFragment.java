package com.example.agilesavev2.fragments.category;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.agilesavev2.R;
import com.example.agilesavev2.models.transactions.Category;
import com.example.agilesavev2.views.category.CategoryActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {
    private String categoryName;
    public CategoryFragment() {
        // Required empty public constructor
    }

    public CategoryFragment(String categoryName){
        this.categoryName= categoryName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CardView cardView = view.findViewById(R.id.category);
        TextView category_name = view.findViewById(R.id.category_name);

        try {
            category_name.setText(categoryName);

        }catch (Exception e){
            System.out.println("Something went wrong "+e.getMessage());
        }


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CategoryActivity)getActivity()).returnSelectedCategory(category_name.getText().toString());
            }
        });



    }
}