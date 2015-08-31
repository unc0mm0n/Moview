package yuval.practice.moview;

/**
 * Created by Yuval on 31/08/2015.
 */
public class TmdbMovie {
    public int id;
    public String posterUrl;

    public TmdbMovie(int id, String posterUrl) {
        this.id = id;
        this.posterUrl = posterUrl;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return posterUrl;
    }
}
