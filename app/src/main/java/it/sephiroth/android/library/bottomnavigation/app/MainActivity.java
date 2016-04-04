package it.sephiroth.android.library.bottomnavigation.app;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

@TargetApi (Build.VERSION_CODES.KITKAT_WATCH)
public class MainActivity extends AppCompatActivity implements View.OnLayoutChangeListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private AppBarLayout mAppBarLayout;
    private CoordinatorLayout mCoordinatorLayout;
    private boolean mTranslucentStatus;
    private boolean mTranslucentStatusSet;
    private SystemBarTintManager mSystemBarTint;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            }
        });

        mAppBarLayout = (AppBarLayout) findViewById(R.id.AppBarLayout01);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.CoordinatorLayout01);
        mCoordinatorLayout.addOnLayoutChangeListener(this);

        if (hasTranslucentStatusBar()) {
            int statusbarHeight = getStatusBarHeight();
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mCoordinatorLayout.getLayoutParams();
            params.topMargin = -statusbarHeight;
            params = (ViewGroup.MarginLayoutParams) mToolbar.getLayoutParams();
            params.topMargin = statusbarHeight;
        }
    }

    public SystemBarTintManager getSystemBarTint() {
        if (null == mSystemBarTint) {
            mSystemBarTint = new SystemBarTintManager(this);
        }
        return mSystemBarTint;
    }

    public int getStatusBarHeight() {
        return getSystemBarTint().getConfig().getStatusBarHeight();
    }

    public boolean hasTranslucentStatusBar() {
        if (!mTranslucentStatusSet) {
            if (Build.VERSION.SDK_INT >= 19) {
                mTranslucentStatus =
                    ((getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                        == WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            } else {
                mTranslucentStatus = false;
            }
            mTranslucentStatusSet = true;
        }
        return mTranslucentStatus;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLayoutChange(
        final View v, final int left, final int top, final int right, final int bottom, final int oldLeft, final int oldTop,
        final int oldRight, final int oldBottom) {

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mAppBarLayout.getLayoutParams();

        final int topInset = 63;

        //        params.height = 224 + topInset;
        //        params.topMargin = topInset;
        //        mAppBarLayout.setLayoutParams(params);
        //        mAppBarLayout.setPadding(0, topInset, 0, 0);

        mCoordinatorLayout.removeOnLayoutChangeListener(this);
    }
}
