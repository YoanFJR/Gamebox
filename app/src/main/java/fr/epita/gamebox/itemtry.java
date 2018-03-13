package fr.epita.gamebox;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class itemtry extends RelativeLayout
{
    private TextView trynumber;
    private ImageView c1;
    private ImageView c2;
    private ImageView c3;
    private ImageView c4;
    private TextView okcount;
    private TextView apcount;


    public itemtry(Context context) {
        super(context);
        init();
    }

    public itemtry(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public itemtry(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public itemtry(Context context, String turn, int c1, int c2, int c3, int c4,
                   String okcount, String apcount) {
        super(context);
        init();
        this.trynumber.setText(turn);
        this.c1.setImageResource(c1);
        this.c2.setImageResource(c2);
        this.c3.setImageResource(c3);
        this.c4.setImageResource(c4);
        this.okcount.setText(okcount);
        this.apcount.setText(apcount);
    }

    private void init() {
        inflate(getContext(), R.layout.itemtry, this);
        this.trynumber = (TextView)findViewById(R.id.mm_t_trynumber);
        this.c1 = (ImageView)findViewById(R.id.mm_img_t1);
        this.c2 = (ImageView)findViewById(R.id.mm_img_t2);
        this.c3 = (ImageView)findViewById(R.id.mm_img_t3);
        this.c4 = (ImageView)findViewById(R.id.mm_img_t4);
        this.okcount = (TextView)findViewById(R.id.mm_t_ok);
        this.apcount = (TextView)findViewById(R.id.mm_t_ap);
    }
}
