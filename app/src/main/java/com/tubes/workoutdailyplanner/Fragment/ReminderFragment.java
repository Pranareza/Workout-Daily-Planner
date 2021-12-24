package com.tubes.workoutdailyplanner.Fragment;



import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tubes.workoutdailyplanner.R;
import com.tubes.workoutdailyplanner.SetReminder;


public class ReminderFragment extends Fragment{

    Button button;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.reminder_fragment, container, false);
        button = v.findViewById(R.id.btnLetsGo);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SetReminder.class);
                startActivity(intent);

            }
        });


        return v;
    }
}
