package User_Navbar_classes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.a1200134_nsralla_hassan_finalproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Nav_About#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Nav_About extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Nav_About() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Nav_About.
     */
    // TODO: Rename and change types and number of parameters
    public static Nav_About newInstance(String param1, String param2) {
        Nav_About fragment = new Nav_About();
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

        View rootView = inflater.inflate(R.layout.fragment_nav__about, container, false);
        // find the buttons
        Button callButton = rootView.findViewById(R.id.buttonCall);
        Button mapsButton = rootView.findViewById(R.id.buttonMaps);
        Button emailButton = rootView.findViewById(R.id.buttonEmail);

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:0599000000"));
                startActivity(callIntent);
            }
        });

        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:31.961013,35.190483?q=31.961013,35.190483(Label+Name)");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:AdvancePizza@Pizza.com"));
                startActivity(Intent.createChooser(emailIntent, "Send email via..."));
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }
}