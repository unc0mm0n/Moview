package yuval.practice.moview;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class to hold Movie Info from The Movie Database (themoviedb.org)
 */
public class TmdbMovie implements Parcelable {
    private static final String TMDB_SMALL_POSTER_URL= "http://image.tmdb.org/3/t/p/w185/";
    private static final String TMDB_LARGE_POSTER_URL= "http://image.tmdb.org/3/t/p/w342/";
    private final String LOG_TAG = TmdbMovie.class.getSimpleName();

    private int id;
    private String posterUrl;
    private String title;
    private String releaseDate;
    private double rating;
    private String synopsis;

    /**
     *
     * @param movieJson A movie JSON object from TMDb API
     */
    public TmdbMovie (JSONObject movieJson) {
        try {
            posterUrl = movieJson.getString("poster_path");
            id = movieJson.getInt("id");
            title = movieJson.getString("original_title");
            releaseDate = movieJson.getString("release_date");
            rating = movieJson.getDouble("vote_average");
            synopsis = movieJson.getString("overview");
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error parsing JSON", e);
            posterUrl = null;
            id = 0;
        }
    }

    protected TmdbMovie(Parcel in) {
        id = in.readInt();
        posterUrl = in.readString();
        title = in.readString();
        releaseDate = in.readString();
        rating = in.readDouble();
        synopsis = in.readString();
    }

    public static final Creator<TmdbMovie> CREATOR = new Creator<TmdbMovie>() {
        @Override
        public TmdbMovie createFromParcel(Parcel in) {
            return new TmdbMovie(in);
        }

        @Override
        public TmdbMovie[] newArray(int size) {
            return new TmdbMovie[size];
        }
    };

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
        return Uri.parse(TmdbMovie.TMDB_SMALL_POSTER_URL + posterUrl).toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(posterUrl);
        dest.writeString(title);
        dest.writeString(releaseDate);
        dest.writeDouble(rating);
        dest.writeString(synopsis);
    }

    /**
     *
     * @return Movie title.
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return url to large version of the poster.
     */
    public String getLargePosterUrl() {
        return Uri.parse(TmdbMovie.TMDB_LARGE_POSTER_URL + posterUrl).toString();
    }

    /**
     *
     * @return synopsis of movie.
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     *
     * @return rating of movie.
     */
    public double getRating() {
        return rating;
    }

    /**
     *
     * @return release date of movie in format: YYYY-MM-DD.
     */
    public String getReleaseDate() {
        return releaseDate;
    }
}
