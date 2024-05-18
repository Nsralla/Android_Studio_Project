package User_Navbar_classes;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.a1200134_nsralla_hassan_finalproject.ChangePasswordActivity;
import com.example.a1200134_nsralla_hassan_finalproject.R;

import java.io.IOException;

import Database.DataBaseHelper;
import FragmentsManager.Home_layout_user;
import ObjectClasses.Client;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class Nav_Profile extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_STORAGE_PERMISSION = 101;

    Button saveButton, changePasswordButton, change_picture_button;
    Spinner genderSpinner;
    EditText emailText, fNameText, lNameText, phoneText;
    String email, fName, lName, phone, gender, hashedPassword;
    ImageView profile_picture;
    private Uri imageUri;

    private String mParam1, mParam2;

    public Nav_Profile() {
        // Required empty public constructor
    }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nav__profile, container, false);

        setupViews(rootView);
        setupListeners();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadSavedProfilePicture();
    }

    private void setupViews(View rootView) {
        changePasswordButton = rootView.findViewById(R.id.buttonChangePassword);
        Spinner spinnerGender = rootView.findViewById(R.id.spinnerGender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);
        saveButton = rootView.findViewById(R.id.buttonSaveChanges);
        change_picture_button = rootView.findViewById(R.id.change_picture_button);
        profile_picture = rootView.findViewById(R.id.profile_picture);
    }

    private void setupListeners() {
        saveButton.setOnClickListener(v -> {
            getTextPlains(getView());
            getNewInfo();
            validateInputs();
        });
        changePasswordButton.setOnClickListener(v -> startActivity(new Intent(getActivity(), ChangePasswordActivity.class)));
        change_picture_button.setOnClickListener(v -> checkPermissionsAndOpenImageChooser());
    }

    private void checkPermissionsAndOpenImageChooser() {
        if (ContextCompat.checkSelfPermission(getActivity(), READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{READ_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
        } else {
            openImageChooser();
        }
    }

    public void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            loadProfilePictureFromUri(imageUri);
        }
    }

    private void loadProfilePictureFromUri(Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
            profile_picture.setImageBitmap(bitmap);
            saveProfilePicture(uri);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Failed to load image", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadSavedProfilePicture() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        String uriString = sharedPreferences.getString("profilePictureUri", null);
        if (uriString != null) {
            Uri savedUri = Uri.parse(uriString);
            loadProfilePictureFromUri(savedUri);
        }
    }

    private void saveProfilePicture(Uri imageUri) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("profilePictureUri", imageUri.toString());
        editor.apply();
        Toast.makeText(getActivity(), "Profile picture updated", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openImageChooser();
        } else {
            Toast.makeText(getActivity(), "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
        }
    }

    public void validateInputs() {
        boolean isValid = true;
        StringBuilder errors = new StringBuilder();
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errors.append("Invalid email format.\n");
            isValid = false;
        }

        if (!phone.matches("05\\d{8}")) {
            errors.append("Phone number must start with '05' and be 10 digits long.\n");
            isValid = false;
        }

        if (fName.length() < 3 || lName.length() < 3) {
            errors.append("First name and Last name must be at least 3 characters long.\n");
            isValid = false;
        }

        if (!isValid) {
            Toast.makeText(getContext(), errors.toString(), Toast.LENGTH_LONG).show();
        } else {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity(), "1200134_nsralla_hassan_finalProject", null, 1);
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
            Toast.makeText(getContext(), "Data updated successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void getNewInfo() {
        email = emailText.getText().toString();
        fName = fNameText.getText().toString();
        lName = lNameText.getText().toString();
        phone = phoneText.getText().toString();
        gender = genderSpinner.getSelectedItem().toString();
    }

    public void getTextPlains(View rootView) {
        emailText = rootView.findViewById(R.id.editTextEmail);
        fNameText = rootView.findViewById(R.id.editTextFirstName);
        lNameText = rootView.findViewById(R.id.editTextLastName);
        phoneText = rootView.findViewById(R.id.editTextPhone);
        genderSpinner = rootView.findViewById(R.id.spinnerGender);
    }

    public void displayClientInfo(Cursor cursor, String loggedInEmail, View rootView, Spinner spinnerGender, ArrayAdapter<CharSequence> adapter) {
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

        ((EditText) rootView.findViewById(R.id.editTextEmail)).setText(client.getEmail());
        ((EditText) rootView.findViewById(R.id.editTextPhone)).setText(client.getPhone());
        ((EditText) rootView.findViewById(R.id.editTextFirstName)).setText(client.getFirstName());
        ((EditText) rootView.findViewById(R.id.editTextLastName)).setText(client.getLastName());
        String currentGender = client.getGender();
        if (currentGender != null) {
            int spinnerPosition = adapter.getPosition(currentGender);
            spinnerGender.setSelection(spinnerPosition);
        }
    }

    public void displayAllCustomers(String loggedInEmail, View rootView, Spinner spinnerGender, ArrayAdapter<CharSequence> adapter) {
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
                cursor.close();
            }
        }
    }
}
