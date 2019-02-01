package nton.is.best.apps;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

//Firebase Library
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ImageView myImageView,myImageViewPlay,myLogoView;
    private RecyclerView mIklanRV,mLiveRV,mDrakorRV;

    private FirebaseRecyclerAdapter<dbMovieAdapter, MainActivity.IklanGeser> mIklanAdapter;
    private FirebaseRecyclerAdapter<dbMovieAdapter, MainActivity.LiveTV> mLiveAdapter;
    private FirebaseRecyclerAdapter<dbMovieAdapter, MainActivity.Drakor> mDrakorAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        // Check if we need to display our OnboardingFragment
        if (!sharedPreferences.getBoolean(
                IntroActivity.COMPLETED_ONBOARDING_PREF_NAME, false)) {
            // The user hasn't seen the OnboardingFragment yet, so show it
            startActivity(new Intent(this, IntroActivity.class));
        }

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }// private DatabaseReference mDatabase;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        /// MULAI
        myImageView = (ImageView) findViewById(R.id.myImageView);

        //Kode Iklan Diatas
        DatabaseReference IklanGeser = database.getInstance().getReference("ListTV");
        Query personsQuery = IklanGeser.orderByKey();
        mIklanRV = (RecyclerView) findViewById(R.id.cRecyler);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mIklanRV);
        mIklanRV.hasFixedSize();
        mIklanRV.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions personsOptions = new FirebaseRecyclerOptions.Builder<dbMovieAdapter>().setQuery(personsQuery, dbMovieAdapter.class).build();

        mIklanAdapter = new FirebaseRecyclerAdapter<dbMovieAdapter, MainActivity.IklanGeser>(personsOptions) {

            @Override
            protected void onBindViewHolder(MainActivity.IklanGeser holder, int position, final dbMovieAdapter model) {
                holder.setTitle(model.getJudul());
                holder.setSinopsis(model.getSinopsis());
                holder.setImage(getBaseContext(), model.getMedia());
                holder.mView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this,"Ini Hanya Iklan , Tidak Dapat Digunakan",Toast.LENGTH_LONG).show();
                    }
                });
            }


            @Override
            public MainActivity.IklanGeser onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_iklan, parent, false);

                return new MainActivity.IklanGeser(view);


            }



        };


        //LiveTV
        DatabaseReference aLiveTV = database.getInstance().getReference("LiveTV");
        Query baruQuery = aLiveTV.orderByKey();
        mLiveRV = (RecyclerView) findViewById(R.id.cRecylerLiveTV);
        mLiveRV.hasFixedSize();
        mLiveRV.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions aLiveTVOption = new FirebaseRecyclerOptions.Builder<dbMovieAdapter>().setQuery(baruQuery, dbMovieAdapter.class).build();
        mLiveAdapter = new FirebaseRecyclerAdapter<dbMovieAdapter, LiveTV>(aLiveTVOption) {
            @Override
            protected void onBindViewHolder(MainActivity.LiveTV holder, int position, final dbMovieAdapter model) {
               // holder.setTitle(model.getJudul());
                holder.setImage(getBaseContext(), model.getMedia());
                holder.mView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        final String url = model.getVideo();
                        Intent intent = new Intent(getApplicationContext(), HlsPlayerActivity.class);
                        intent.putExtra("id", url);
                        startActivity(intent);
                    }
                });

            }
            @Override
            public MainActivity.LiveTV onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_tv, parent, false);

                return new MainActivity.LiveTV(view);
            }
        };

        //Drakor
        DatabaseReference aDrakor = database.getInstance().getReference("SerialDrakor");
        Query DrakorQuery = aDrakor.orderByKey();
        mDrakorRV = (RecyclerView) findViewById(R.id.cRecylerDrakor);
        mDrakorRV.hasFixedSize();
        mDrakorRV.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions aDrakorOption = new FirebaseRecyclerOptions.Builder<dbMovieAdapter>().setQuery(DrakorQuery, dbMovieAdapter.class).build();
        mDrakorAdapter = new FirebaseRecyclerAdapter<dbMovieAdapter, Drakor>(aDrakorOption) {
            @Override
            protected void onBindViewHolder(MainActivity.Drakor holder, int position, final dbMovieAdapter model) {
                // holder.setTitle(model.getJudul());
                holder.setTitle(model.getJudul());
                holder.setImage(getBaseContext(), model.getMedia());
                holder.mView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        String judul = model.getJudul();
                        String sinopsis = model.getSinopsis();
                        String genre = model.getGenre();
                        String tglMulai = model.getTglTayang();
                        String akhirepisod = model.getAkhirEpisode();
                        String updateepisode = model.getTglNewEpisode();
                        final String thumbnail = model.getThumbnail();
                       // final String url = model.getThumbnail();
                        Intent intent = new Intent(getApplicationContext(), DrakorActivity.class);
                        intent.putExtra("judul",judul);
                        intent.putExtra("sinopsis",sinopsis);
                        intent.putExtra("genre", genre);
                        intent.putExtra("tglmulai",tglMulai);
                        intent.putExtra("akhirepisod",akhirepisod);
                        intent.putExtra("updateepisod", updateepisode);
                        intent.putExtra("thumbnail", thumbnail);
                        startActivity(intent);
                    }
                });

            }
            @Override
            public MainActivity.Drakor onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_drakor, parent, false);

                return new MainActivity.Drakor(view);
            }
        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mIklanRV.setLayoutManager(layoutManager);
        mIklanRV.setAdapter(mIklanAdapter);
        LinearLayoutManager layoutManager_upload = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        mLiveRV.setLayoutManager(layoutManager_upload);
        mLiveRV.setAdapter(mLiveAdapter);
        LinearLayoutManager layoutManager_Drakor = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        mDrakorRV.setLayoutManager(layoutManager_Drakor);
        mDrakorRV.setAdapter(mDrakorAdapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        mIklanAdapter.startListening();
        mLiveAdapter.startListening();
        mDrakorAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mIklanAdapter.stopListening();
        mLiveAdapter.stopListening();
        mDrakorAdapter.stopListening();

    }
    //Iklan
    public static class IklanGeser extends RecyclerView.ViewHolder{
        View mView;
        public IklanGeser(View itemView){
            super(itemView);
            mView = itemView;
        }
        public void setTitle(String title){
            TextView post_title = (TextView)mView.findViewById(R.id.juduliklan);
            post_title.setText(title);
        }
       public void setSinopsis(String sinopsis){
           TextView post_sinopsis = (TextView)mView.findViewById(R.id.desciklan);
           post_sinopsis.setText(sinopsis);
        }
        public void setImage(Context ctx, String image){
            ImageView post_image = (ImageView) mView.findViewById(R.id.gcGeser);
            Picasso.get().load(image).into(post_image);
        }
    }

    //LiveTV
    public static class LiveTV extends RecyclerView.ViewHolder{
        View mView;
        public LiveTV(View itemView){
            super(itemView);
            mView = itemView;
        }
        public void setImage(Context ctx, String image){
            ImageView post_image = (ImageView) mView.findViewById(R.id.mediaLiveTV);
            Picasso.get().load(image).into(post_image);
        }
    }
    //SerialDrakor
    public static class Drakor extends RecyclerView.ViewHolder{
        View mView;
        public Drakor(View itemView){
            super(itemView);
            mView = itemView;
        }
        public void setImage(Context ctx, String image){
            ImageView post_image = (ImageView) mView.findViewById(R.id.mediaDrakor);
            Picasso.get().load(image).into(post_image);
        }
        public void setTitle(String title) {
            TextView post_title = (TextView)mView.findViewById(R.id.juduldrakor);
            post_title.setText(title);
        }
    }
}


