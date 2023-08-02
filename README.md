Title: Leach Peach: A Comprehensive Mobile Workout Tracker Application

Abstract:

Maintaining physical fitness is a pivotal element in achieving a healthy lifestyle. It requires dedication, regular practice, and consistent tracking. This paper introduces Leach Peach, an innovative mobile application designed to support users in managing their exercise routines, thus providing a systematic and effective way to attain fitness goals.

Leach Peach is a versatile workout tracker application that implements Create, Read, Update, and Delete (CRUD) operations, ensuring a smooth workout management experience for its users. The application facilitates users to create custom workout routines by aggregating a variety of exercises into a single, manageable list. This list can be modified or entirely revised based on the users' evolving fitness needs or preferences, making Leach Peach a flexible tool in maintaining a diversified exercise schedule.

In addition, Leach Peach comes with a feature that allows users to mark their workouts as completed, along with recording the date of completion. This not only allows users to track their progression over time, but also serves as a motivational instrument by providing a sense of accomplishment after each completed workout.

In conclusion, Leach Peach aspires to transform the fitness journey of its users by offering an intuitive platform to effectively manage and monitor workouts. By maintaining a log of completed workouts, it gives users a measurable view of their progress, making fitness goals more transparent and achievable. Future enhancements could potentially include features like goal setting, fitness reminders, and interoperability with other health and wellness applications, thereby expanding Leach Peach's functionality and reach within the digital fitness domain.

SOURCES:
zybook textbook:

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

