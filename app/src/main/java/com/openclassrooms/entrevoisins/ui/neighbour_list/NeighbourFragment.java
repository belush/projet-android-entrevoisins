package com.openclassrooms.entrevoisins.ui.neighbour_list;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;

    public class NeighbourFragment extends MyNeighbourFragment {

        protected List<Neighbour> getMyNeighbours() {
            return mApiService.getNeighbours();
        }

        /**
         * Create and return a new instance
         * @return @{@link NeighbourFragment}
         */

        public static MyNeighbourFragment newInstance() {
            return new NeighbourFragment();
        }

    }