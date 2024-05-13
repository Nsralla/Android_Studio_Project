package Admin_Navbar_classes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a1200134_nsralla_hassan_finalproject.R;

import java.util.ArrayList;

import Database.DataBaseHelper;
import Hashing.Hash;
import ObjectClasses.Admin;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddAdmin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddAdmin extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText phone;
    private EditText passwordText;
    private EditText confirmPasswordText;
    private Spinner gender;
    private Button addButton;


    public AddAdmin() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AddAdmin newInstance(String param1, String param2) {
        AddAdmin fragment = new AddAdmin();
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
        View view = inflater.inflate(R.layout.fragment_add_admin, container, false);
        View adminCard = view.findViewById(R.id.admin_card);
        Animation fallDownAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fall_down);
        adminCard.startAnimation(fallDownAnimation);
        initUI(view);


        return view;
    }

    private void initUI(View rootView) {
        // Initialize your EditText and Button
        firstName = rootView.findViewById(R.id.firstName);
        lastName = rootView.findViewById(R.id.lastName);
        email = rootView.findViewById(R.id.email);
        phone = rootView.findViewById(R.id.phone);
        passwordText = rootView.findViewById(R.id.pass);
        confirmPasswordText = rootView.findViewById(R.id.confirmPasswordText);
        gender = rootView.findViewById(R.id.gender);
        addButton = rootView.findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInputs();
            }
        });
    }


    public void validateInputs(){
        boolean isValid = true;
        boolean exists = false;
        StringBuilder errors = new StringBuilder();

        String first = firstName.getText().toString();
        String last = lastName.getText().toString();
        String mail = email.getText().toString();
        String phoneNumber = phone.getText().toString();
        String password = passwordText.getText().toString();
        String selectedGender = gender.getSelectedItem().toString();
        System.out.println("password: "+password);

        DataBaseHelper dataBaseHelper =new DataBaseHelper(getContext(),"1200134_nsralla_hassan_finalProject",null,1);
        //VALIDATE THAT THE EMAIL IS NOT USED BEFORE
        // get all the admin emails from db, if repeated, make isValid false
        ArrayList<String> emails;
        emails = dataBaseHelper.getAllAdminEmails();
        for(int i = 0 ; i < emails.size(); i++) {
            if(emails.get(i).equals(mail)){
                exists = true;
                break;
            }

        }

        if(exists){
            isValid = false;
            errors.append("Email already used.\n");
        }


        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            errors.append("Invalid email format.\n");
            isValid = false;
        }
        // then check if the email is valid

        //TODO:VALIDATE THE PHONE NUMBER
        if(!phoneNumber.matches("05\\d{8}"))
        {
            errors.append("Phone number must start with '05' and be 10 digits long.\n");
            isValid = false;
        }

        //TODO:VALIDATE THE FIRST NAME
        if (first.length() < 3 ||last.length() < 3) {
            errors.append("First name and Last name must be at least 3 characters long.\n");
            isValid = false;
        }

        if (password.length() < 8 || !password.matches(".*[a-zA-Z]+.*") || !password.matches(".*\\d+.*")) {
            errors.append("Password must be at least 8 characters long and include at least one letter and one number.\n");
            isValid = false;
        }
        //TODO: VALIDATE CONFIRM PASSWORD
        if (!confirmPasswordText.getText().toString().equals(password)) {
            errors.append("Confirm password does not match the password.\n");
            isValid = false;
        }

        if(!isValid){
            Toast.makeText(getContext(), errors.toString(), Toast.LENGTH_LONG).show();
        }else{
            Admin admin = new Admin();
            admin.setEmail(mail);
            admin.setFirstName(first);
            admin.setLastName(last);
            admin.setPhone(phoneNumber);
            admin.setHashedPassword(Hash.hashPassword(password));
            admin.setGender(selectedGender);
            dataBaseHelper.insertAdmin(admin, getContext());
            Toast.makeText(getContext(), "Admin has been inserted successfully", Toast.LENGTH_SHORT).show();
        }
    }
}