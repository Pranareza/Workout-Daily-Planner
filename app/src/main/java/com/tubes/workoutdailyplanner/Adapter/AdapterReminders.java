package com.tubes.workoutdailyplanner.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Delete;
import androidx.room.Query;

import com.tubes.workoutdailyplanner.AppDatabase;
import com.tubes.workoutdailyplanner.R;
import com.tubes.workoutdailyplanner.Reminders;
import com.tubes.workoutdailyplanner.SetReminder;
import com.tubes.workoutdailyplanner.User.User;

import java.util.List;

public class AdapterReminders extends RecyclerView.Adapter<AdapterReminders.MyViewHolder>{

    private final List<Reminders> allReminders;
    private TextView message,time;
    private Context context;
    ItemClicked itemClicked;
    List<Reminders> reminders;

    public AdapterReminders(List<Reminders> allReminders, ItemClicked itemClicked) {
        this.allReminders = allReminders;
        this.itemClicked = itemClicked;
    }

    public AdapterReminders(List<Reminders> temp) {
        this.allReminders = temp;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        context = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reminder_item,viewGroup,false);
        return new AdapterReminders.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.reminder_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        myViewHolder.imageOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //popmenu
                showPopup(view);

            }
        });


        Reminders reminders = allReminders.get(position);
        if(!reminders.getMessage().equals(""))
            message.setText(reminders.getMessage());
        else
            message.setHint("No Message");
        time.setText(reminders.getRemindDate().toString());

    }

    public void showPopup(View view){
        PopupMenu popupMenu = new PopupMenu(context,view);
        popupMenu.inflate(R.menu.menu_options);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                int id = menuItem.getItemId();
                switch (id){
                    case R.id.hapus:
                        Toast.makeText(context, "Hapus Klik", Toast.LENGTH_SHORT).show();
                        deleteData();
                        break;

                    case R.id.update:
                        Toast.makeText(context, "Update klik", Toast.LENGTH_SHORT).show();
                        break;
                }

                return false;
            }

            private void deleteData() {
            }
        });

        popupMenu.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Reminders> reminders) {
        this.reminders = reminders;
        notifyDataSetChanged();
    }

    public interface ItemClicked{
        void updateClicked(Reminders reminders);
        void  deleteClicked(Reminders reminders);
    }


    @Override
    public int getItemCount() {
        return allReminders.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageOptions;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageOptions = itemView.findViewById(R.id.menuOption);
            message = itemView.findViewById(R.id.textView1);
            time = itemView.findViewById(R.id.textView2);
        }

    }

}
