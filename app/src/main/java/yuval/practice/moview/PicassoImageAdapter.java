package yuval.practice.moview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PicassoImageAdapter<T> extends ArrayAdapter<T> {

    protected Context mContext;
    public PicassoImageAdapter(Context context) {
        super(context, R.layout.picasso_adapter_image_view);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
        } else {
            imageView = (ImageView) convertView;
        }

        String url = getItem(position).toString();
        Picasso.with(mContext).load(url).into(imageView);

        return imageView;
    }
}
