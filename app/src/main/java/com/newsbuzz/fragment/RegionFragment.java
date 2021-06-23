package com.newsbuzz.fragment;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.newsbuzz.R;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegionFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //public static List<String> region = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextToSpeech t1;

    public RegionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegionFragment newInstance(String param1, String param2) {
        RegionFragment fragment = new RegionFragment();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton btnPlay = getActivity().findViewById(R.id.imageButton);
        btnPlay.setVisibility(View.INVISIBLE);

        Button next = getActivity().findViewById(R.id.btDone);



        next.setOnClickListener(this);
        getActivity().findViewById(R.id.cbIndia).setOnClickListener(new View.OnClickListener() { public void onClick(View view) { CategoryFragment.categories.add("national");}});
        getActivity().findViewById(R.id.cbWorld).setOnClickListener(new View.OnClickListener() { public void onClick(View view) { CategoryFragment.categories.add("world");}});
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        t1=new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.ENGLISH);
                    t1.speak("Select Regions", TextToSpeech.QUEUE_FLUSH, null,null);
                }
            }
        });
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_region, container, false);
    }

    @Override
    public void onClick(View v) {
        t1.speak("Here are your selected headlines", TextToSpeech.QUEUE_FLUSH, null,null);
        NewsFragment nfrag = new NewsFragment();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flFragment,nfrag);
        ft.commit();
    }
}