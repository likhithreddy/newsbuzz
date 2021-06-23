package com.newsbuzz.news;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.newsbuzz.R;
import com.newsbuzz.entity.News;
import com.newsbuzz.util.DownloadImageTask;

import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{
    private List<News> listdata;
    private Context context;

   // RecyclerView recyclerView;  
    public NewsAdapter(List<News> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.news_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;  
    }  
  
    @Override  
    public void onBindViewHolder(ViewHolder holder, int position) {  
        final News news = listdata.get(position);
        holder.textView.setText(news.getTitle());
        new DownloadImageTask(holder.imageView).execute(news.getImageUrl());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override  
            public void onClick(View view) {
//                Toast.makeText(view.getContext(),"click on item: "+news.getTitle(),Toast.LENGTH_LONG).show();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getReadMoreUrl()));
                context.startActivity(browserIntent);
            }
        });  
    }  
  
  
    @Override  
    public int getItemCount() {  
        return listdata.size();
    }  
  
    public static class ViewHolder extends RecyclerView.ViewHolder {  
        public ImageView imageView;
        public TextView textView;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {  
            super(itemView);  
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);  
            this.textView = (TextView) itemView.findViewById(R.id.textView);  
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);  
        }  
    }
    public List<News> getListdata() {
        return listdata;
    }

    public void setListdata(List<News> listdata) {
        this.listdata = listdata;
    }
}