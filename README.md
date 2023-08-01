CRUD operations for workouts and exercises:

    ExerciseDao and WorkoutDao are Data Access Object (DAO) interfaces that define methods for accessing the Exercise and Workout data in the SQLite database.

    Exercise and Workout are Entity classes that represent the data structure for a workout and an exercise respectively in your app.

    WorkoutRepository is a repository class that acts as an abstraction layer between the different data sources (e.g., SQLite database) and the rest of the app. It uses the DAOs to access the data.

    DataConverter and DateConverter are utility classes used to convert complex data types (like lists and dates) into a format that can be stored in SQLite.

    WorkoutViewModel is a ViewModel that provides data for the UI and survives configuration changes (like rotations). It uses WorkoutRepository to get and save data, ensuring that data operations are done off the main thread.

    CreateWorkoutFragment is a Fragment where users can create a new workout. It includes a form for entering the workout's details and a list (RecyclerView) of exercises.

    ExerciseAdapter and ExerciseDetailAdapter are Adapter classes used for populating RecyclerViews in CreateWorkoutFragment and WorkoutDetailFragment respectively. Each adapter is associated with a list of exercises.

    MainActivity is the main activity that hosts all the fragments.

    MainFragment is the first screen that users see. It displays a list of all workouts.

    MyViewModelFactory is a factory class needed to pass your WorkoutRepository to your WorkoutViewModel.

    WorkoutAdapter and WorkoutDetailAdapter are Adapter classes used for populating RecyclerViews in MainFragment and WorkoutDetailFragment respectively. Each adapter is associated with a list of workouts.

    WorkoutDetailFragment is a Fragment that displays the details of a workout, including its associated exercises.

For the layout files:

    activity_main.xml: This layout file is for the MainActivity and typically hosts the Fragment(s).

    exercise_detail_item.xml: This layout file defines the look of each item in the exercise detail list.

    exercise_item.xml: This layout file defines the look of each item in the exercise list when creating a workout.

    fragment_create_workout.xml: This layout file is for the CreateWorkoutFragment and contains fields for entering workout details and a RecyclerView for the list of exercises.

    fragment_main.xml: This layout file is for the MainFragment and contains a RecyclerView for the list of workouts.

    workout_detail_fragment.xml: This layout file is for the WorkoutDetailFragment and contains fields for displaying workout details and a RecyclerView for the list of exercises.

    workout_item.xml: This layout file defines the look of each item in the workout list.

In summary, the app is based on the MVVM (Model-View-ViewModel) architecture. The model consists of the Exercise and Workout entities, the WorkoutRepository, and the DAOs. The views are the various Fragments and their corresponding layout files, and the viewmodels are WorkoutViewModel. The adapters handle displaying lists of entities, and the converter classes ensure that complex data types can be stored in the SQLite database.

Sources: 
zybook textbook assigned to this class:


Figure 5.6.2: list_item_band.xml.

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
   xmlns:tools="http://schemas.android.com/tools"
   android:orientation="vertical"
   android:layout_width="match_parent"
   android:layout_height="wrap_content">

   <TextView
      android:layout_width="match_parent"
      android:layout_height="wrap_content"
      android:id="@+id/band_name"
      tools:text="band"
      style="@style/bandListName">
   </TextView>



Figure 5.6.4: Updated ListFragment.java.

package com.zybooks.thebanddatabase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ListFragment extends Fragment {

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_list, container, false);

      // Click listener for the RecyclerView
      View.OnClickListener onClickListener = itemView -> {

         // Create fragment arguments containing the selected band ID
         int selectedBandId = (int) itemView.getTag();
         Bundle args = new Bundle();
         args.putInt(DetailFragment.ARG_BAND_ID, selectedBandId);

         // Replace list with details
         Navigation.findNavController(itemView).navigate(R.id.show_item_detail, args);
      };

      // Send bands to RecyclerView
      RecyclerView recyclerView = rootView.findViewById(R.id.band_list);
      List<Band> bands = BandRepository.getInstance(requireContext()).getBands();
      recyclerView.setAdapter(new BandAdapter(bands, onClickListener));

      return rootView;
   }

   private class BandAdapter extends RecyclerView.Adapter<BandHolder> {

      private final List<Band> mBands;
      private final View.OnClickListener mOnClickListener;

      public BandAdapter(List<Band> bands, View.OnClickListener onClickListener) {
         mBands = bands;
         mOnClickListener = onClickListener;
      }

      @NonNull
      @Override
      public BandHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
         return new BandHolder(layoutInflater, parent);
      }

      @Override
      public void onBindViewHolder(BandHolder holder, int position) {
         Band band = mBands.get(position);
         holder.bind(band);
         holder.itemView.setTag(band.getId());
         holder.itemView.setOnClickListener(mOnClickListener);
      }

      @Override
      public int getItemCount() {
         return mBands.size();
      }
   }

   private static class BandHolder extends RecyclerView.ViewHolder {

      private final TextView mNameTextView;

      public BandHolder(LayoutInflater inflater, ViewGroup parent) {
         super(inflater.inflate(R.layout.list_item_band, parent, false));
         mNameTextView = itemView.findViewById(R.id.band_name);
      }

      public void bind(Band band) {
         mNameTextView.setText(band.getName());
      }
   }
}



Figure 7.1.2: Correct way to update the UI from the background thread.

public void fibonacciClick(View view) {
   // Display progress bar
   mProgressBar.setVisibility(View.VISIBLE);

   // Get number from UI
   int num = Integer.parseInt(mNumberEditText.getText().toString());

   // Clear previous result
   mResultTextView.setText("");

   // Create a background thread
   Thread thread = new Thread(() -> {
      // Find the Fibonacci number
      int fibNumber = fibonacci(num);

      // UI should only be updated by main thread 
      MainActivity.this.runOnUiThread(() -> {
         mResultTextView.setText("Result: " +
            NumberFormat.getNumberInstance(Locale.US).format(fibNumber));

         // Hide progress bar
         mProgressBar.setVisibility(View.INVISIBLE);
      });
   });

   thread.start();
}

android studio docs: https://developer.android.com/guide/navigation/use-graph/programmatic

<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/nav_graph"
    app:startDestination="@id/home">
    <fragment
        android:id="@+id/home"
        android:name="com.example.android.navigation.HomeFragment"
        android:label="fragment_home"
        tools:layout="@layout/fragment_home" />
    <fragment
        android:id="@+id/location"
        android:name="com.example.android.navigation.LocationFragment"
        android:label="fragment_location"
        tools:layout="@layout/fragment_location" />
    <fragment
        android:id="@+id/shop"
        android:name="com.example.android.navigation.ShopFragment"
        android:label="fragment_shop"
        tools:layout="@layout/fragment_shop" />
    <fragment
        android:id="@+id/settings"
        android:name="com.example.android.navigation.SettingsFragment"
        android:label="fragment_settings"
        tools:layout="@layout/fragment_settings" />
</navigation>

