package User_Navbar_classes;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a1200134_nsralla_hassan_finalproject.ChangePasswordActivity;
import com.example.a1200134_nsralla_hassan_finalproject.R;
import Database.DataBaseHelper;
import FragmentsManager.Home_layout_user;
import ObjectClasses.Client;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Nav_Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Nav_Profile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final int REQUEST_CODE_PICK_IMAGE = 1;
    private static final int REQUEST_CODE_READ_STORAGE_PERMISSION = 2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button saveButton;
    Button changePasswordButton;
    Spinner genderSpinner;
    EditText emailText;
    EditText fNameText;
    EditText lNameText;
    EditText phoneText;
    String email, fName, lName, phone, gender, hashedPassword;
    Button changePictureButton;
    ImageView profilePicture;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Nav_Profile() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Nav_Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Nav_Profile newInstance(String param1, String param2) {
        Nav_Profile fragment = new Nav_Profile();
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
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        String loggedInEmail = sharedPreferences.getString("currentLoggedInUserEmail", null);
        View rootView = inflater.inflate(R.layout.fragment_nav__profile, container, false);
        changePasswordButton = rootView.findViewById(R.id.buttonChangePassword);
        changePictureButton = rootView.findViewById(R.id.change_picture_button);
        profilePicture = rootView.findViewById(R.id.profile_picture);

        Spinner spinnerGender = rootView.findViewById(R.id.spinnerGender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);

        displayAllCustomers(loggedInEmail, rootView, spinnerGender, adapter);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity(), "1200134_nsralla_hassan_finalProject", null, 1);

        String profileImagePath = dataBaseHelper.getProfilePicture(loggedInEmail);
        if (profileImagePath != null) {
            profilePicture.setImageURI(Uri.parse(profileImagePath));
        }

        saveButton = rootView.findViewById(R.id.buttonSaveChanges);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTextPlains(rootView);
                getNewInfo();
                validateInputs();
            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        changePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_READ_STORAGE_PERMISSION);
                } else {
                    pickImageFromGallery();
                }
            }
        });
        return rootView;
    }
    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_READ_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImageFromGallery();
            } else {
                Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                String imagePath = getPathFromUri(selectedImageUri);
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
                String loggedInEmail = sharedPreferences.getString("currentLoggedInUserEmail", null);
                // Save imagePath to the database
                updateProfilePicture(loggedInEmail, imagePath);
                // Update the ImageView with the new image URI
                profilePicture.setImageURI(selectedImageUri);
            }
        }
    }

    private String getPathFromUri(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = requireActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String path = cursor.getString(columnIndex);
            cursor.close();
            return path;
        }
        return null;
    }

    public void updateProfilePicture(String email, String imagePath) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity(), "1200134_nsralla_hassan_finalProject", null, 1);
        dataBaseHelper.updateProfilePicture(email, imagePath);
    }



    public void validateInputs(){
        boolean isValid = true;
        StringBuilder errors = new StringBuilder();
        //TODO: Validate the email
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errors.append("Invalid email format.\n");
            isValid = false;
        }

        //TODO:VALIDATE THE PHONE NUMBER
        if(!phone.matches("05\\d{8}"))
        {
            errors.append("Phone number must start with '05' and be 10 digits long.\n");
            isValid = false;
        }

        //TODO:VALIDATE THE FIRST NAME
        if (fName.length() < 3 || lName.length() < 3) {
            errors.append("First name and Last name must be at least 3 characters long.\n");
            isValid = false;
        }

        if(!isValid){
            Toast.makeText(getContext(), errors.toString(), Toast.LENGTH_LONG).show();
        }else{
            //TODO:SAVE THE USER INFO INTO DB
            DataBaseHelper dataBaseHelper =new DataBaseHelper(getActivity(),"1200134_nsralla_hassan_finalProject",null,1);
            Client client = new Client();
            client.setEmail(email);
            client.setFirstName(fName);
            client.setLastName(lName);
            client.setPhone(phone);
            client.setHashedPassword(hashedPassword);
            client.setGender(gender);

            dataBaseHelper.updateClient(client);
            Intent intent = new Intent(getContext(), Home_layout_user.class);
            startActivity(intent);
            //TODO:NAVIGATE TO
            Toast.makeText(getContext(), "Data updated sucssesfully", Toast.LENGTH_SHORT).show();
        }

    }

    public void getNewInfo(){
        email = emailText.getText().toString();
        fName = fNameText.getText().toString();
        lName = lNameText.getText().toString();
        phone = phoneText.getText().toString();
        gender = genderSpinner.getSelectedItem().toString();
    }

    public void getTextPlains(View rootView){
        emailText = rootView.findViewById(R.id.editTextEmail);
        fNameText = rootView.findViewById(R.id.editTextFirstName);
        lNameText = rootView.findViewById(R.id.editTextLastName);
        phoneText = rootView.findViewById(R.id.editTextPhone);
        genderSpinner = rootView.findViewById(R.id.spinnerGender);
    }
    public void displayClientInfo(Cursor cursor, String loggedInEmail, View rootView,Spinner spinnerGender, ArrayAdapter<CharSequence> adapter){
        int phoneColIndex = cursor.getColumnIndex("PHONE");
        int firstNameColIndex = cursor.getColumnIndex("FIRSTNAME");
        int lastNameColIndex = cursor.getColumnIndex("LASTNAME");
        int genderColIndex = cursor.getColumnIndex("GENDER");
        int encryptedPasswordIndex = cursor.getColumnIndex("HASHEDPASSWORD");

        Client client = new Client();
        client.setEmail(loggedInEmail);
        client.setPhone(cursor.getString(phoneColIndex));
        client.setGender(cursor.getString(genderColIndex));
        client.setFirstName(cursor.getString(firstNameColIndex));
        client.setLastName(cursor.getString(lastNameColIndex));
        hashedPassword = cursor.getString(encryptedPasswordIndex);


        // Now set these values to EditTexts using rootView
        ((EditText) rootView.findViewById(R.id.editTextEmail)).setText(client.getEmail());
        ((EditText) rootView.findViewById(R.id.editTextPhone)).setText(client.getPhone());
        ((EditText) rootView.findViewById(R.id.editTextFirstName)).setText(client.getFirstName());
        ((EditText) rootView.findViewById(R.id.editTextLastName)).setText(client.getLastName());
        String currentGender = client.getGender(); // Assuming you retrieved this from the database
        if (currentGender != null) {
            int spinnerPosition = adapter.getPosition(currentGender);
            spinnerGender.setSelection(spinnerPosition);
        }

    }

    public void displayAllCustomers(String loggedInEmail, View rootView, Spinner spinnerGender, ArrayAdapter<CharSequence> adapter) {
        // TODO: GET ALL CUSTOMERS.
        DataBaseHelper db = new DataBaseHelper(getActivity(), "1200134_nsralla_hassan_finalProject", null, 1);
        Cursor cursor = db.getAllClients();
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    int emailColIndex = cursor.getColumnIndex("EMAIL");
                    if (emailColIndex != -1 && cursor.getString(emailColIndex).equals(loggedInEmail)) {
                        displayClientInfo(cursor, loggedInEmail, rootView, spinnerGender, adapter);
                    }
                }
            } finally {
                cursor.close(); // Close the cursor to avoid memory leaks
            }
        }
    }
}