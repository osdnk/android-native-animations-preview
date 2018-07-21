package osdnk.previewanimations;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.support.animation.FloatValueHolder;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.duktape.Duktape;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

  DrawerLayout mDrawer;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    final Duktape duktape = Duktape.create();
    final Locale locale = new Locale("en");


    FloatingActionButton fab = findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Spring started", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        final View img = findViewById(R.id.sample);

        FloatValueHolder floatValueHolder = new FloatValueHolder(20f);
        SpringForce spring = new SpringForce(1000)
                .setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY)
                .setStiffness(SpringForce.STIFFNESS_VERY_LOW);

        SpringAnimation anim = new SpringAnimation(floatValueHolder)
                .setSpring(spring);


        anim.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
          @Override
          public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {

            String express = String.format(locale, "%f/2" ,value);
            Double y = (Double) duktape.evaluate(express);
            String express2 = String.format(locale, "%f/3" ,value);
            Double x = (Double) duktape.evaluate(express2);

            img.setTranslationY(y.floatValue());
            img.setTranslationX(x.floatValue());
          }
        });
        anim.start();
      }
    });

    mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    mDrawer.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }


  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    item.setChecked(true);
    int id = item.getItemId();

    if (id == R.id.nav_spring) {
        //no-op
    } else if (id == R.id.nav_fling) {
    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }
}
