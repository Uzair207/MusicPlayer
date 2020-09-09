package com.example.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyViewHolder> {
private Context mcontext;
private ArrayList<MusicFiles> mfiles;
MusicAdapter(Context context, ArrayList<MusicFiles> musicFiles){
    this.mcontext = context;
    this.mfiles = musicFiles;
}

    public class MyViewHolder extends RecyclerView.ViewHolder{
TextView file_name;
ImageView image;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        file_name = itemView.findViewById(R.id.music_file_name);
        image = itemView.findViewById(R.id.music_img);
    }

}
private byte[] getAlbumImg(String uri){
    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
    retriever.setDataSource(uri);
    byte[] art = retriever.getEmbeddedPicture();
    retriever.release();
    return art;
}
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(mcontext).inflate(R.layout.music_items,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
holder.file_name.setText(mfiles.get(position).getTitle());
byte[] img = getAlbumImg(mfiles.get(position).getPath());
if(img!=null){
    Glide.with(mcontext).asBitmap()
            .load(img)
            .into(holder.image);
}
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(mcontext, PlayerActivity.class);
        intent.putExtra("position", position);
        mcontext.startActivity(intent);
    }
});
    }



    @Override
    public int getItemCount() {
        return mfiles.size();
    }
}
