package yuval.practice.moview;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class to hold Movie Info from The Movie Database (themoviedb.org)
 */
public class TmdbMovie {
    private static final String TMDB_POSTER_URL= "http://image.tmdb.org/3/t/p/w185/";
    private final String LOG_TAG = TmdbMovie.class.getSimpleName();
    private int id;
    private String posterUrl;

    /**
     *
     * @param movieJson A movie JSON object from TMDb API
     */
    public TmdbMovie (JSONObject movieJson) {
        try {
            posterUrl = Uri.parse(TmdbMovie.TMDB_POSTER_URL + movieJson.getString("poster_path")).toString();
            id = movieJson.getInt("id");
        } catch (JSONException e) {
            Log.v(LOG_TAG, "Error parsing JSON", e);
            posterUrl = null;
            id = 0;
        }
    }

    /**
     *
     * @param posterUrl posterUrl to poster url.
     * @param id movie id.
     */
    public TmdbMovie (String posterUrl, int id) {
        this.posterUrl = posterUrl;
        this.id = id;
    }

    /**
     *
     * @return Movie ID.
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return URL to movie poster.
     */
    @Override
    public String toString() {
        return posterUrl;
    }
}
