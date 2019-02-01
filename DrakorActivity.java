package nton.is.best.apps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DrakorActivity extends AppCompatActivity {


    private RecyclerView mEpisodeRV,mPamainRV;

    private FirebaseRecyclerAdapter<dbMovieAdapter, DrakorActivity.ListEpisode> mListEpisodeAdapter;
    private FirebaseRecyclerAdapter<dbMovieAdapter, MainActivity.LiveTV> mLiveAdapter;
    private FirebaseRecyclerAdapter<dbMovieAdapter, MainActivity.Drakor> mDrakorAdapter;
    String judul,sinopsis,genre,tglMulai,akhirepi,updateepi,thumbnail;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drakor);
        Bundle b = getIntent().getExtras();

        judul= b.getString("judul");
        sinopsis= b.getString("sinopsis");
        genre= b.getString("genre");
        tglMulai= b.getString("tglmulai");
        akhirepi= b.getString("akhirepisod");
        updateepi= b.getString("updateepisod");
        thumbnail = b.getString("thumbnail");
        ImageView drakorThumbnail = (ImageView) findViewById(R.id.drakorThumbnail);
        Picasso.get().load(thumbnail).into(drakorThumbnail);


        TextView judule = (TextView) findViewById(R.id.drakorJudul);
        TextView sinopsise = (TextView) findViewById(R.id.DrakorSinopsis);
        TextView tglepicat = (TextView) findViewById(R.id.DrakorTglEpiCat);

        judule.setText(judul);
        sinopsise.setText(sinopsis);
        tglepicat.setText(tglMulai + "  | " + akhirepi + " Episode | " + genre);



        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Kode Episode
        DatabaseReference Episode = database.getReference().child("SerialDrakor/").child("Episode");
        Query episodeQuery = Episode.orderByKey();
        mEpisodeRV = (RecyclerView) findViewById(R.id.cRecylerDrakorEpisode);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mEpisodeRV);
        mEpisodeRV.hasFixedSize();
        mEpisodeRV.setLayoutManager(new LinearLayoutManager(this));



        //Episode.child("Episode");
        FirebaseRecyclerOptions episodeOptions = new FirebaseRecyclerOptions.Builder<dbMovieAdapter>().setQuery(episodeQuery, dbMovieAdapter.class).build();

        mListEpisodeAdapter = new FirebaseRecyclerAdapter<dbMovieAdapter, DrakorActivity.ListEpisode>(episodeOptions) {

            @Override
            protected void onBindViewHolder(DrakorActivity.ListEpisode holder, int position, final dbMovieAdapter model) {
                holder.setList(model.getList());
                holder.mView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        final String url = model.getVideo();
                        Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
                        intent.putExtra("id", url);
                        startActivity(intent);
                    }
                });
            }


            @Override
            public DrakorActivity.ListEpisode onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_drakor_episode, parent, false);

                return new DrakorActivity.ListEpisode(view);


            }



        };
        LinearLayoutManager layoutManager_Drakor = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        mEpisodeRV.setLayoutManager(layoutManager_Drakor);

        mEpisodeRV.setAdapter(mListEpisodeAdapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        mListEpisodeAdapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        mListEpisodeAdapter.stopListening();


    }

    //Iklan
    public static class ListEpisode extends RecyclerView.ViewHolder{
        View mView;
        public ListEpisode(View itemView){
            super(itemView);
            mView = itemView;
        }
        public void setList (String list){
            TextView post_title = (TextView)mView.findViewById(R.id.TxtlistEpisode);
            post_title.setText(list);
        }
    }

}
