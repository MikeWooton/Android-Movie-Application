package movies.txstate.edu.moviesskeleton;

import java.util.ArrayList;

public class PopularMovies {

    private static PopularMovies popularMovies = null;

    ArrayList <Movie> movies = new ArrayList <Movie> ();

    private PopularMovies(){}

    public static PopularMovies getInstance(){
        if(popularMovies == null){
            popularMovies = new PopularMovies();
        }
        return popularMovies;
    }

    public void add(Movie movie) {
        movies.add(movie);
    }

    public void addAll(ArrayList<Movie> movie) {
        movies.addAll(movie);
    }

    public ArrayList <Movie> getMovies() {
        return movies;
    }

    public Movie getMovie(int index){
        return movies.get(index);
    }

    public void clear(){
        movies.clear();
    }

    public int size(){
        return movies.size();
    }
}
