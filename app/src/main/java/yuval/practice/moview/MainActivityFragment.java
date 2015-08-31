package yuval.practice.moview;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    // Adapter to manage the grid view of the posters.
    protected PicassoImageAdapter<TmdbMovie> mMoviesAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        GridView moviesGridView = (GridView) rootView.findViewById(R.id.main_poster_grid);

        mMoviesAdapter = new PicassoImageAdapter<>(getActivity());
        moviesGridView.setAdapter(mMoviesAdapter);
        moviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
            }
        });

        MovieFetchTask movieFetchTask = new MovieFetchTask();
        movieFetchTask.execute();
        return rootView;
    }

    /**
     * AsyncTask class to Fetch poster images through TMDb
     * onPostExecute updates mMovieAdapter to populate the screen.
     */
    public class MovieFetchTask extends AsyncTask<Void, Void, TmdbMovie[]> {

        private final String LOG_TAG = MovieFetchTask.class.getSimpleName();

        @Override
        protected void onPostExecute(TmdbMovie[] tmdbMovies) {
            mMoviesAdapter.addAll(tmdbMovies);
        }

        @Override
        protected TmdbMovie[] doInBackground(Void... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr = null;

            Uri.Builder urlBuilder = Uri.parse(getString(R.string.tmbd_discover_url))
                    .buildUpon()
                    .appendQueryParameter("api_key", getString(R.string.tmdb_api_key));

            String urlStr = urlBuilder.build().toString();

            try {

                URL url = new URL(urlStr);

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieJsonStr = buffer.toString();

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getTmdbMoviesUrlsFromJson(movieJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage());
            }
            return null;
        }

        private TmdbMovie[] getTmdbMoviesUrlsFromJson(String jsonStr)
                throws JSONException {


            JSONObject jsonData = new JSONObject(jsonStr);
            JSONArray jsonMovieArray = jsonData.getJSONArray("results");

            TmdbMovie[] tmdbMovies = new TmdbMovie[jsonMovieArray.length()];
            Uri url;
            for (int i = 0; i < jsonMovieArray.length(); i++) {
                JSONObject movie = jsonMovieArray.getJSONObject(i);
                url = Uri.parse(getString(R.string.tmbd_poster_url)
                        + movie.getString("poster_path"));

                tmdbMovies[i] = new TmdbMovie( Integer.valueOf(movie.getString("id")),
                        url.toString());
            }
            return tmdbMovies;
        }
    }
}




