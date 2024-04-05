package com.example.my_friend_app.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.my_friend_app.databinding.FragmentNotificationsBinding;
import com.example.my_friend_app.room.AppDatabase;
import com.example.my_friend_app.room.StudentDao;
import com.example.my_friend_app.ui.dashboard.StudentAdapter;


public class NotificationsFragment extends Fragment {
    private FragmentNotificationsBinding binding;
    StudentAdapter studentAdapter;

    StudentDao studentDao;
    AppDatabase appDatabase;

    RecyclerView rv_main_catalog;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        rv_main_catalog = binding.rvMain;
        studentAdapter = new StudentAdapter();
        rv_main_catalog.setAdapter(studentAdapter);
        appDatabase = Room.databaseBuilder(binding.getRoot().getContext(), AppDatabase.class,"database")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        studentDao = (StudentDao) appDatabase.studentDao();
        studentAdapter.setList(studentDao.getAll());
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}