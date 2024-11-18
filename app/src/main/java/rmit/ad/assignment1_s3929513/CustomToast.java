package rmit.ad.assignment1_s3929513;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CustomToast extends Toast {

    public static final int LONG = 7000;
    public static final int SHORT = 5000;
    public static int SUCCESS = 1;
    public static int ERROR = 3;

    public CustomToast(Context context) {
        super(context);
    }

    public static Toast makeText(Context context, String message, int duration, int type, boolean androidicon) {
        Toast toast = new Toast(context);
        toast.setDuration(duration);
        View layout = LayoutInflater.from(context).inflate(R.layout.custom_toast, null, false);
        TextView l1 = (TextView) layout.findViewById(R.id.toast_text);
        LinearLayout linearLayout = (LinearLayout) layout.findViewById(R.id.toast_type);
        ImageView img = (ImageView) layout.findViewById(R.id.toast_icon);
        ImageView img1 = (ImageView) layout.findViewById(R.id.imageView4);
        l1.setText(message);
        if (androidicon == true)
            img1.setVisibility(View.VISIBLE);
        else if (androidicon == false)
            img1.setVisibility(View.GONE);
        if (type == 1) {
            linearLayout.setBackgroundResource(R.drawable.success_shape);
            img.setImageResource(R.drawable.checked);
        } else if (type == 3) {
            linearLayout.setBackgroundResource(R.drawable.error_shape);
            img.setImageResource(R.drawable.close);
        }
        toast.setView(layout);
        return toast;
    }

    public static Toast makeText(Context context, String message, int duration, int type, int ImageResource) {
        Toast toast = new Toast(context);
        View layout = LayoutInflater.from(context).inflate(R.layout.custom_toast, null, false);
        TextView l1 = (TextView) layout.findViewById(R.id.toast_text);
        LinearLayout linearLayout = (LinearLayout) layout.findViewById(R.id.toast_type);
        ImageView img = (ImageView) layout.findViewById(R.id.toast_icon);
        l1.setText(message);
        if (type == 1) {
            linearLayout.setBackgroundResource(R.drawable.success_shape);
            img.setImageResource(ImageResource);
        } else if (type == 3) {
            linearLayout.setBackgroundResource(R.drawable.error_shape);
            img.setImageResource(ImageResource);
        }
        toast.setView(layout);
        return toast;
    }
}

