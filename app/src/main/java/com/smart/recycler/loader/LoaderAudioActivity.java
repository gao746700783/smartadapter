package com.smart.recycler.loader;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.smart.recycler.R;

/**
 * Description: LoaderAudioActivity
 * <p>
 * User: qiangzhang <br/>
 * Date: 2017/6/27 上午10:25 <br/>
 */
public class LoaderAudioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader_audio);

        FragmentManager fm = getFragmentManager();
        CursorLoaderListFragment list = new CursorLoaderListFragment();
        fm.beginTransaction().replace(R.id.root, list).commit();

    }

    /**
     * Description: CursorLoaderListFragment
     * <p>
     * User: qiangzhang <br/>
     * Date: 2017/6/27 上午10:25 <br/>
     */
    public static class CursorLoaderListFragment extends ListFragment
            implements LoaderManager.LoaderCallbacks<Cursor> {

        // This is the Adapter being used to display the list's data.
        SimpleCursorAdapter mAdapter;

        // If non-null, this is the current filter the user has provided.
        String mCurFilter;

        int LOADER_ID = 0;

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {

            mAdapter = new SimpleCursorAdapter(this.getActivity(),
                    R.layout.layout_list_item_audio, null,
                    new String[]{MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST},
                    new int[]{R.id.tv_audio_title, R.id.tv_audio_artist}, 0);
            setListAdapter(mAdapter);

            setEmptyText("No Audios");
            setListShown(false);

            if (getLoaderManager().getLoader(LOADER_ID) == null) {
                Log.i("TAG", "Initializing the new Loader...");
            } else {
                Log.i("TAG", "Reconnecting with existing Loader (id '1')...");
            }
            getLoaderManager().initLoader(LOADER_ID, null, this);

            super.onActivityCreated(savedInstanceState);
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            // Insert desired behavior here.
            Log.i("FragmentComplexList", "Item clicked: " + id);
        }

        //只会调用一次
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            // This is called when a new Loader needs to be created.  This
            // sample only has one Loader, so we don't care about the ID.
            // First, pick the base URI to use depending on whether we are
            // currently filtering.
            return AudioLoader.instance(this.getActivity());
        }

        //每次数据源都有更新的时候，就会回调这个方法，然后update 我们的ui了。
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

            // Swap the new cursor in.  (The framework will take care of closing the
            // old cursor once we return.)
            mAdapter.swapCursor(data);

            // The list should now be shown.
            if (isResumed()) {
                setListShown(true);
            } else {
                setListShownNoAnimation(true);
            }
        }

        public void onLoaderReset(Loader<Cursor> loader) {
            // This is called when the last Cursor provided to onLoadFinished()
            // above is about to be closed.  We need to make sure we are no
            // longer using it.
            mAdapter.swapCursor(null);
        }
    }

}
