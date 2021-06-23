package com.newsbuzz.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.newsbuzz.R;
import com.newsbuzz.entity.News;
import com.newsbuzz.util.NewsScrapper;

import com.newsbuzz.news.NewsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NewsFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    NewsAdapter adapter = null;
    TextToSpeech t1;
    Button b1;
    List<News> topNews;
    boolean playing = false;
    public NewsFragment() {
    }

    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
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
        t1=new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.ENGLISH);
                }
            }
        });

        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton btnPlay = getActivity().findViewById(R.id.imageButton);
        btnPlay.setOnClickListener(this);
        btnPlay.setEnabled(true);
        btnPlay.setVisibility(View.VISIBLE);

        final RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerView);
        adapter = new NewsAdapter(new ArrayList<News>(),getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    NewsScrapper ns = new NewsScrapper();
                    topNews = ns.getTopNews(CategoryFragment.categories);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.setListdata(topNews);
                            adapter.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @Override
    public void onClick(View v) {
        if(!playing) {
            playing=true;
            Handler handler = new Handler();
            for(final News n:topNews) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(playing) {
                            t1.speak(n.getTitle(), TextToSpeech.QUEUE_ADD, null,null);
                        }
                    }
                });
            }
        } else {
            playing=false;
            t1.stop();
        }

    }
}