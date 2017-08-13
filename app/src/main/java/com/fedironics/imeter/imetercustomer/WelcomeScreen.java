package com.fedironics.imeter.imetercustomer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by madunaguekenedavid on 08/08/2017.
 */

public class WelcomeScreen extends Activity {
    private ViewPager viewPager;
    private IntroManager introManager;
    private int[] layouts;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private ViewPagerAdapter viewPagerAdapter;
    Button next,skip;
    private Context context;
    public int[] backgrounds;
    public Typeface custom_font;
    private RelativeLayout mylayout;
    private String[] titles;
    private String[] desc;
    TextView tx;
    TextView desc_string;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        if(Build.VERSION.SDK_INT>=21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
        setContentView(R.layout.main_welcome);
        viewPager = (ViewPager)findViewById(R.id.viewpager_welcome);
        dotsLayout = (LinearLayout)findViewById(R.id.layout_dots);
        skip = (Button)findViewById(R.id.button_skip);
        next = (Button)findViewById(R.id.button_next);


        mylayout = (RelativeLayout) findViewById(R.id.relative_welcome);

        layouts = new int[]{R.layout.welcome1,R.layout.welcome2,R.layout.welcome3,R.layout.welcome4};
        backgrounds =new int[]{R.drawable.background2,R.drawable.background3,R.drawable.background4,R.drawable.background5};
        titles= new String[]{"Welcome to EEDC Utility", "Pay bills Easily","title string 3","title string 4"};
        desc= new String[]{
                "Unique, Usablity, Utility",
                "Pay bills Easily",
                "desc string 3",
                "desc string 4"
        };
        changeStatusBarColor();

        custom_font = Typeface.createFromAsset(getAssets(), "raleway_thin.ttf");

        tx= (TextView)findViewById(R.id.title_text);
        desc_string = (TextView)findViewById(R.id.text_desc_welcome);
        tx.setTypeface(custom_font);
        desc_string.setTypeface(custom_font);

        viewPagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(viewListener);

        addButtonDots(0);
        blurView(0);


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeScreen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current = getItem(1);
                if(current < layouts.length){
                    viewPager.setCurrentItem(current);
                }
                else{
                    Intent intent = new Intent(WelcomeScreen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    private void  addButtonDots(int position){
        dots = new TextView[layouts.length];
        int[] colorActive = getResources().getIntArray(R.array.dot_active);
        int[] colorInactive = getResources().getIntArray(R.array.dot_inactive);
        dotsLayout.removeAllViews();
        for(int i = 0; i<dots.length; i++){
            dots[i]= new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(45);
            dots[i].setTextColor(colorInactive[position]);
            dotsLayout.addView(dots[i]);
        }
        if(dots.length>0){
            dots[position].setTextColor(colorActive[position]);
        }

    }
    private void blurView(int position){
      //  tx.setText(titles[position]);
   //     desc_string.setText(desc[position]);

        Bitmap resultBmp = BlurBuilder.blur(context, BitmapFactory.decodeResource(getResources(),backgrounds[position]));
        mylayout.setBackground( new BitmapDrawable( getResources(), resultBmp ) );


    }
    private int getItem(int i)
    {
        return  viewPager.getCurrentItem() +i;
    }

    private void changeStatusBarColor(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            blurView(position);
            addButtonDots(position);

            if(position==layouts.length-1){
                next.setText("PROCEED");
                skip.setVisibility(View.GONE);

            }else{
                next.setText("NEXT");
                skip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public class ViewPagerAdapter extends PagerAdapter{
        private LayoutInflater layoutInflater;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(layouts[position],container,false);
            container.addView(v);
            return v;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v = (View)object;
            container.removeView(v);
        }
    }
}
