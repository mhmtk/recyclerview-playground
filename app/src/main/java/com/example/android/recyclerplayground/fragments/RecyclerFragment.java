package com.example.android.recyclerplayground.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.android.recyclerplayground.NumberPickerDialog;
import com.example.android.recyclerplayground.R;
import com.example.android.recyclerplayground.adapters.SimpleAdapter;


public abstract class RecyclerFragment extends Fragment implements AdapterView.OnItemClickListener {

  private RecyclerView mList;
  private SimpleAdapter mAdapter;

  /**
   * Required Overrides for Sample Fragments
   */

  protected abstract RecyclerView.LayoutManager getLayoutManager();

  protected abstract RecyclerView.ItemDecoration getItemDecoration();

  protected abstract int getDefaultItemCount();

  protected abstract SimpleAdapter getAdapter();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_recycler, container, false);

    mList = (RecyclerView) rootView.findViewById(R.id.section_list);
    mList.setLayoutManager(getLayoutManager());
    mList.addItemDecoration(getItemDecoration());

    mList.getItemAnimator().setAddDuration(1000);
    mList.getItemAnimator().setChangeDuration(1000);
    mList.getItemAnimator().setMoveDuration(1000);
    mList.getItemAnimator().setRemoveDuration(1000);

    mAdapter = getAdapter();
    mAdapter.setItemCount(getDefaultItemCount());
    mAdapter.setOnItemClickListener(this);
    mList.setAdapter(mAdapter);

    return rootView;
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.grid_options, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    NumberPickerDialog dialog;
    int itemId = item.getItemId();
    if (itemId == R.id.action_add) {
      dialog = new NumberPickerDialog(getActivity());
      dialog.setTitle("Position to Add");
      dialog.setPickerRange(0, mAdapter.getItemCount());
      dialog.setOnNumberSelectedListener(new NumberPickerDialog.OnNumberSelectedListener() {
        @Override
        public void onNumberSelected(int value) {
          mAdapter.addItem(value);
        }
      });
      dialog.show();
    } else if (itemId == R.id.action_remove) {
      dialog = new NumberPickerDialog(getActivity());
      dialog.setTitle("Position to Remove");
      dialog.setPickerRange(0, mAdapter.getItemCount() - 1);
      dialog.setOnNumberSelectedListener(new NumberPickerDialog.OnNumberSelectedListener() {
        @Override
        public void onNumberSelected(int value) {
          mAdapter.removeItem(value);
        }
      });
      dialog.show();
    } else if (itemId == R.id.action_empty) {
      mAdapter.setItemCount(0);
    } else if (itemId == R.id.action_small) {
      mAdapter.setItemCount(5);
    } else if (itemId == R.id.action_medium) {
      mAdapter.setItemCount(25);
    } else if (itemId == R.id.action_large) {
      mAdapter.setItemCount(196);
    } else if (itemId == R.id.action_scroll_zero) {
      mList.scrollToPosition(0);
    } else if (itemId == R.id.action_smooth_zero) {
      mList.smoothScrollToPosition(0);
    } else {
      return super.onOptionsItemSelected(item);
    }
    return true;
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    Toast.makeText(getActivity(),
                   "Clicked: " + position + ", index " + mList.indexOfChild(view),
                   Toast.LENGTH_SHORT).show();
  }
}
