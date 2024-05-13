package Admin_Navbar_classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.a1200134_nsralla_hassan_finalproject.R;

import Database.DataBaseHelper;
import ObjectClasses.Admin;
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
    Button changePasswordButton;
    EditText emailText;
    EditText fNameText;
    EditText lNameText;
    EditText phoneText;
    EditText genderText;
    EditText passwordText;
    String email, fName, lName, phone, gender, hashedPassword;

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
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        String loggedInEmail = sharedPreferences.getString("currentLoggedInUserEmail", null);
        defineTheElements(rootView);
        getAdminFromDB(loggedInEmail,rootView);

        // Inflate the layout for this fragment
        return rootView;
    }

    private void defineTheElements(View rootView){
        emailText = rootView.findViewById(R.id.editTextEmail);
        fNameText = rootView.findViewById(R.id.editTextFirstName);
        lNameText = rootView.findViewById(R.id.editTextLastName);
        phoneText = rootView.findViewById(R.id.editTextPhone);
        genderText = rootView.findViewById(R.id.editTextGender);
        passwordText = rootView.findViewById(R.id.editTextPassword);
        changePasswordButton = rootView.findViewById(R.id.buttonChangePassword);
        saveButton  = rootView.findViewById(R.id.buttonSaveChanges);
    }

    public void displayClientInfo(Cursor cursor, String loggedInEmail){
        int phoneColIndex = cursor.getColumnIndex("PHONE");
        int firstNameColIndex = cursor.getColumnIndex("FIRSTNAME");
        int lastNameColIndex = cursor.getColumnIndex("LASTNAME");
        int genderColIndex = cursor.getColumnIndex("GENDER");
        System.out.println("LOGGED EMAIL: "+ loggedInEmail);


        // Now set these values to EditTexts using rootView
        emailText.setText(loggedInEmail);
        phoneText.setText(cursor.getString(phoneColIndex));
        fNameText.setText(cursor.getString(firstNameColIndex));
        lNameText.setText(cursor.getString(lastNameColIndex));
        genderText.setText(cursor.getString(genderColIndex));// Assuming you retrieved this from the database

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("PasswordPrefs", Context.MODE_PRIVATE);
        String loggedInPassword = sharedPreferences.getString("currentLoggedInUserPassword", null);
        passwordText.setText(loggedInPassword);
        System.out.println("PASSWORD: "+ loggedInPassword);

    }

    private void getAdminFromDB(String loggedInEmail, View rootView){
        DataBaseHelper db = new DataBaseHelper(getActivity(), "1200134_nsralla_hassan_finalProject", null, 1);
        Cursor cursor = db.getAllAdmins();
        if (cursor != null) {
            System.out.println("CURSOR IS NOT NULL");
            try {
                while (cursor.moveToNext()) {
                    int emailColIndex = cursor.getColumnIndex("EMAIL");
                    System.out.println("LOGGED IN EMAIL: "+loggedInEmail);
                    System.out.println("email index : "+ emailColIndex);
                    if (emailColIndex != -1 && cursor.getString(emailColIndex).equals(loggedInEmail)) {
                        System.out.println("ADMIN FOUND");
                        displayClientInfo(cursor, loggedInEmail);
                    }
                }
            } finally {
                cursor.close(); // Close the cursor to avoid memory leaks

            }
        }
        else{
            System.out.println("CURSOR IS NULL");
        }
    }
}