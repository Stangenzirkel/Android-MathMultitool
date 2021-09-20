package stangenzirkel.mathmultitool;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import stangenzirkel.mathmultitool.converter.Converter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ConverterFragment.Callback {
    NavController navController;
    private String tag = "MainActivityTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, drawer);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, new AppBarConfiguration.Builder(
                R.id.calculatorFragment, R.id.converterFragment)
                .setDrawerLayout(findViewById(R.id.drawer_layout))
                .build())
                || super.onSupportNavigateUp();
    }


    @Override
    public void onClick(View v) {
        Log.d(tag, "Click");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(tag, "Key clicked. Code = ".concat(String.valueOf(keyCode)));

        androidx.navigation.fragment.NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if  (navHostFragment != null) {
            Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

            if (fragment instanceof ConverterFragment) {
                Log.d(tag, "fragment instanceof ConverterFragment");
                ((ConverterFragment) fragment).onKeyboardKey(keyCode, event);
            } else if (fragment != null) {
                Log.d(tag, "fragment instanceof ".concat(fragment.getClass().getName()));
            } else {
                Log.d(tag, "fr == null");
            }
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onConverterFragmentSolutionButtonClick() {
        navController.navigate(R.id.solutionFragment);
    }
}