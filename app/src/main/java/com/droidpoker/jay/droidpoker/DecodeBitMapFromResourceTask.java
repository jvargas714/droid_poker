package com.droidpoker.jay.droidpoker;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by jay on 2/2/16.
 * Class to efficiently decode Bitmaps in a separate thread to ensure performance
 * example usage -->
 * DecodeBitMapFromResourceTask decodeTask = new DecodeBitMapFromResourceTask(ivToBeSet, res);
 * decodeTask.execute(resid, reqWidth, reqHeight);
 * testing
 */
//                                                          resid,   void, imageView to get bitmap
public class DecodeBitMapFromResourceTask extends AsyncTask<Integer, Void, Bitmap>
{
    private final WeakReference<ImageView> imageViewReference;
    private int data = 0;
    Resources res;

    public DecodeBitMapFromResourceTask(ImageView imageView, Resources res)
    {
        // ensures that imageView can be garbage collected
        imageViewReference = new WeakReference<>(imageView);
        this.res = res;
    }

    //Decode image in background thread
    @Override
    protected Bitmap doInBackground(Integer... params)
    {
        data = params[0];
        return decodeSampledBitmapFromResource(this.res, data, params[1], params[2]);
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null)
        {
            final ImageView imageView = imageViewReference.get();
            if (imageView != null)
            {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth)
        {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth)
            {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                                        int reqWidth, int reqHeight)
    {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}