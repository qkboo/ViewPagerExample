package androidbee.viewpager.sample;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ViewPagerSampleActivity extends Activity {

    private ViewPager mPager;
    public static final int[] mData1 = { R.raw.dokdo_1_13, R.raw.dokdo_1_16,
            R.raw.dokdo_1_17, R.raw.dokdo_1_18, R.raw.dokdo_1_19,
            R.raw.dokdo_1_20, R.raw.dokdo_2_01, R.raw.dokdo_2_02
    };


    private View.OnClickListener mPagerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = ((Button) v).getText().toString();
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT)
                    .show();
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final ActionBar bar = getActionBar();
        
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new PagerAdapterClass( ViewPagerSampleActivity.this,
                mData1));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
   public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()) {
          case android.R.id.home:
              Toast.makeText(this, "Tapped home", Toast.LENGTH_SHORT).show();
              break;

          case R.id.menu_1:   // 페이저그룹1
             Toast.makeText(this, "Data Group1", Toast.LENGTH_SHORT).show();
             mPager.setAdapter(new PagerAdapterClass(getApplicationContext(), DataCollections.mData1));
              break;

          case R.id.menu_2:   // 페이저그룹2
              Toast.makeText(this, "Data Group2", Toast.LENGTH_SHORT).show();
              mPager.setAdapter(new PagerAdapterClass(getApplicationContext(), DataCollections.mData2));
              break;

      }
       mPager.getAdapter().notifyDataSetChanged();
       
      return super.onOptionsItemSelected(item);
   }


    /**
     * PagerAdapter
     */
    private class PagerAdapterClass extends PagerAdapter {

        private LayoutInflater mInflater;
        private int[] mImage;
        
        public PagerAdapterClass(Context c, int[] datas) {
            super();
            mInflater = LayoutInflater.from(c);
            this.mImage = datas;
        }

        @Override
        public int getCount() {
            return mImage.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = null;
            v = mInflater.inflate(R.layout.page1, null);
            
            /** Options 에서 샘플링을 조절하지 않으면 Bitmap에 의한 Out Of Memory 발생 */
            Options opts = new BitmapFactory.Options();
            opts.inSampleSize = 2;
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mImage[position], opts);
            ((ImageView) v.findViewById(R.id.imageview1)).setImageBitmap(bitmap);
//                    .setImageResource(mImage[position])
            v.findViewById(R.id.btn_click).setOnClickListener(mPagerListener);
            
            ((ViewPager)container).addView(v, 0);

            return v;
        }

        @Override
        public void destroyItem(View pager, int position, Object view) {
            ((ViewPager) pager).removeView((View) view);
        }

        @Override
        public boolean isViewFromObject(View pager, Object obj) {
            return pager == obj;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }

        @Override
        public void finishUpdate(View arg0) {
        }
    }

}
