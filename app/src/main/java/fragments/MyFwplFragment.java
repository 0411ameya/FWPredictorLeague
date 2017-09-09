package fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ameya.fwpredictorleague.Profile;
import com.ameya.fwpredictorleague.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFwplFragment extends Fragment {

    TextView username;
    TextView team_name;
    TextView team_rank;
    TextView opp_team_rank;
    TextView opp_team_name;

    public Profile profile = Profile.getInstance();

    public MyFwplFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_my_fwpl, container, false);
        populateViews(v);
        setUpProfileDetails(profile);
        return v;
    }

    private void setUpProfileDetails(Profile profile) {
        username.setText(profile.getName());
        team_name.setText(profile.getTeam_name());
        team_rank.setText(String.valueOf(profile.getTeam_rank()));
        opp_team_name.setText(profile.getOpp_team_name());
        opp_team_rank.setText(String.valueOf(profile.getOpp_team_rank()));

    }

    private void populateViews(View view) {
        username = (TextView) view.findViewById(R.id.tvUsername);
        team_name = (TextView) view.findViewById(R.id.tvTeamname);
        team_rank = (TextView) view.findViewById(R.id.tvTeamRank);
        opp_team_rank = (TextView) view.findViewById(R.id.tvOpponentTeamRank);
        opp_team_name = (TextView) view.findViewById(R.id.tvOpponentTeamName);
    }

}
