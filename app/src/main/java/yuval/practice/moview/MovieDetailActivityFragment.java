package yuval.practice.moview;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment {
    private final String LOG_TAG = MovieDetailActivityFragment.class.getSimpleName();

    public MovieDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        Intent movieIntent = getActivity().getIntent();
        ImageView posterImageView = (ImageView) rootView.findViewById(R.id.detail_poster_image);

        TextView titleTextView = (TextView) rootView.findViewById(R.id.detail_title_text);
        TextView releaseDateTextView = (TextView) rootView.findViewById(R.id.detail_release_date_text);
        TextView ratingTextView = (TextView) rootView.findViewById(R.id.detail_rating_text);
        TextView mainTextView = (TextView) rootView.findViewById(R.id.detail_main_text);

        if (!movieIntent.hasExtra(Intent.EXTRA_SUBJECT)) {
            Log.w(LOG_TAG, getString(R.string.warning_no_item));
            titleTextView.setText("");
            mainTextView.setText(getString(R.string.warning_no_item));
            return rootView;
        }

        TmdbMovie movie = movieIntent.getParcelableExtra(Intent.EXTRA_SUBJECT);

        Picasso.with(getActivity()).load(movie.getLargePosterUrl()).into(posterImageView);
        titleTextView.setText(movie.getTitle());
        releaseDateTextView.append(movie.getReleaseDate());
        ratingTextView.append(String.valueOf(movie.getRating()));
        mainTextView.append(movie.getSynopsis());

        return rootView;
    }
}
