package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;
/**
 * Dummy mock for the Api
 */
//classe reliee a NeighbourApiService

public class DummyNeighbourApiService implements NeighbourApiService {
    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }
    /**
     * {@inheritDoc}
     * @return
     */
    //on a rajouté cet arraylist et les methodes a la fin de ce fichier pour les favoris

    public List<Neighbour> getFavoriteNeighbours() {

        List<Neighbour> favoriteList = new ArrayList<>();

        for (Neighbour neighbour : neighbours){
            if(neighbour.isFavorite()){
                favoriteList.add(neighbour);
            }
        }
        return favoriteList;
    }

    /**
     * Add or Remove a favorite neighbour
     * {@param neighbour}
     */
    @Override
    public void addOrRemoveFavoriteNeighbour(Neighbour neighbour) {
        neighbours.get(neighbours.indexOf(neighbour)).setFavorite(!neighbour.isFavorite());
    }

}
