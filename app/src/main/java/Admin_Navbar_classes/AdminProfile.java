package Admin_Navbar_classes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a1200134_nsralla_hassan_finalproject.R;

import Database.DataBaseHelper;
import FragmentsManager.Home_layout_user;
import Hashing.Hash;
import ObjectClasses.Admin;
import ObjectClasses.AdminConfiguration;
import ObjectClasses.Client;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminProfile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button saveButton;

    public AdminProfile() {
    }

    // TODO: Rename and change types and number of parameters
    public static AdminProfile newInstance(String param1, String param2) {
        AdminProfile fragment = new AdminProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin_profile, container, false);
        View profileCard = rootView.findViewById(R.id.profile_card);
        Animation fallDownAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fall_down);
        profileCard.startAnimation(fallDownAnimation);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        String loggedInEmail = sharedPreferences.getString("currentLoggedInUserEmail", null);
        AdminConfiguration adminConfiguration = new AdminConfiguration(loggedInEmail, rootView, getContext());
        adminConfiguration.defineTheElements(rootView);
        adminConfiguration.getAdminFromDB();
        saveButton  = rootView.findViewById(R.id.buttonSaveChanges);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminConfiguration.validateInputs();
            }
        });
        return rootView;
    }






}