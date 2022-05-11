package com.openclassrooms.entrevoisins.ui.neighbour_list;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;

public class FavoriteNeighbourFragment extends MyNeighbourFragment {

    protected List<Neighbour> getMyNeighbours() {
        return mApiService.getFavoriteNeighbours();
    }

    /**
     * Create and return a new instance
     * @return @{@link FavoriteNeighbourFragment}
     */
    public static MyNeighbourFragment newInstance() {
        return new FavoriteNeighbourFragment();
    }


}
