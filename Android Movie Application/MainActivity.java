package movies.txstate.edu.moviesskeleton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    private Button nextButton;
    private Button backButton;
    private ImageView movieImage;
    private TextView movieTitle;
    private TextView releaseYear;
    private TextView rating;
    private TextView description;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FetchMoviesTask fetchMoviesTask = new FetchMoviesTask();
        fetchMoviesTask.execute();
        Utils.addFirstMovie();

        movieTitle = (TextView)findViewById(R.id.movie_title);
        releaseYear = (TextView)findViewById(R.id.release_year);
        rating = (TextView)findViewById(R.id.rating);
        description = (TextView)findViewById(R.id.movie_description);
        movieImage = (ImageView)findViewById(R.id.movie_image);

        updateMovie(currentIndex);

        nextButton = (Button)findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentIndex == Utils.MOVIE_PAGE_SIZE)
                    currentIndex = 0;
                else
                    currentIndex = (currentIndex + 1) % Utils.MOVIE_PAGE_SIZE;
                updateMovie(currentIndex);
            }
        });

        backButton = (Button)findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentIndex == 0)
                    currentIndex = Utils.MOVIE_PAGE_SIZE;
                else
                    currentIndex = (currentIndex - 1) % Utils.MOVIE_PAGE_SIZE;
                updateMovie(currentIndex);
            }
        });

    }

    private void updateMovie (int currentIndex) {
        if (currentIndex == -1)
            currentIndex = Utils.MOVIE_PAGE_SIZE;

        Movie movie = PopularMovies.getInstance().getMovie(currentIndex);
        movieTitle.setText(movie.getTitle());
        releaseYear.setText(movie.getReleaseYear());
        rating.setText(movie.getRating() + "/" + Utils.MAXIMUM_MOVIE_RATING);
        description.setText(movie.getOverview());
        loadMovieImage(movie, movieImage);
    }

    private void loadMovieImage (Movie movie, ImageView view) {
        try {
            URL url = Utils.buildMovieImageURL(movie.getPosterPath());
            Log.v(LOG_TAG, "Movie poster url " + url);
            //Load the image from the URL into imageView
            Picasso.with(this)
                    .load(url.toString())
                    .resize(Utils.IMAGE_SIZE_WIDTH, Utils.IMAGE_SIZE_HEIGHT)
                    .into(view);
        }
        catch (MalformedURLException e) {
            Log.e(LOG_TAG, e.toString());
        }
    }
}
