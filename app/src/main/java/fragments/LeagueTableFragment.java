package fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ameya.fwpredictorleague.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeagueTableFragment extends Fragment {


    public LeagueTableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_league_table, container, false);
    }

}
