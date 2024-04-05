package com.example.my_friend_app.ui.dashboard;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.my_friend_app.R;
import com.example.my_friend_app.databinding.ItemCardBinding;
import com.example.my_friend_app.models.Student;
import com.example.my_friend_app.room.AppDatabase;
import com.example.my_friend_app.room.StudentDao;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    List<Student> list = new ArrayList<>();
    StudentDao studentDao;
    Context context;
    NavController navController;
    Student newStudent;
    EditText my_message;

    public void setList(List<Student> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCardBinding itemCardBinding = ItemCardBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        ViewHolder viewHolder =new ViewHolder(itemCardBinding);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.ViewHolder holder, int position) {

        studentDao  = (StudentDao) Room.databaseBuilder(
                        holder.binding.getRoot().getContext(),
                        AppDatabase.class,"database")
                .allowMainThreadQueries()
                .build().studentDao();
        Student student = list.get(position);
        holder.binding.nameSurnameCard.setText(student.getName_surname());
        holder.binding.telNumberCard.setText(student.getTel_number());
        holder.binding.imageCard.setImageBitmap(BitmapFactory.decodeByteArray(student.getImage(),0,student.getImage().length));
        newStudent = student;
        holder.binding.dropdownMenu.setOnClickListener(v1 ->{
            PopupMenu popup = new PopupMenu(holder.binding.getRoot().getContext(),
                    holder.binding.dropdownMenu);
            popup.getMenuInflater().inflate(R.menu.card_menu,popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    switch (item.getTitle().toString()){
                        case "call":
                            if(ContextCompat.checkSelfPermission(
                                    holder.binding.getRoot().getContext(), android.Manifest.permission.CALL_PHONE)

                                    != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions((Activity) holder.binding.getRoot().getContext(), new String[]{Manifest.permission.CALL_PHONE}, 0);

                            } else{ String telephone_student = holder.binding.telNumberCard.getText().toString().trim();
                                Uri call = Uri.parse("tel:"+telephone_student);
                                Intent intent = new Intent(Intent.ACTION_DIAL,call);
                                holder.binding.getRoot().getContext().startActivity(intent);
                            }
                            break;


                        case "message":
                            holder.binding.myMessage.setVisibility(View.VISIBLE);

                            Intent emailIntent = new Intent(Intent.ACTION_SEND);
                            emailIntent.setData(Uri.parse("mailto:"));
                            emailIntent.setType("text/plain");
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT,"My message");
                            emailIntent.putExtra(Intent.EXTRA_TEXT,holder.binding.myMessage.getText().toString());
                            try{
                                holder.itemView.getContext()
                                        .startActivity(Intent.createChooser(emailIntent,"Send mail..."));
                            }catch (ActivityNotFoundException ex){
                                Toast.makeText(holder.itemView.getContext(), "There is no client installed.", Toast.LENGTH_SHORT).show();
                            }
                            break;

                        case "delete":
                            AlertDialog alertDialog = new AlertDialog
                                    .Builder(holder.binding.getRoot().getContext()).create();
                            alertDialog.setTitle("Are you sure?");
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES,delete",
                                    new DialogInterface.OnClickListener() {
                                        @SuppressLint("NotifyDataSetChanged")
                                        public void onClick(DialogInterface dialog, int which) {
                                            studentDao.delete(student);
                                            list.remove(holder.getAdapterPosition());
                                            notifyDataSetChanged();
                                            dialog.dismiss();

                                        }
                                    });
                            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Warning,dont delete",
                                    new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int i) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                            break;
                        default:
                            Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
                    }

                    return true;
                }
            });
            popup.show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemCardBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        public  ViewHolder(@NonNull ItemCardBinding itemView){
            super(itemView.getRoot());
            this.binding =itemView;
        }
    }
}

