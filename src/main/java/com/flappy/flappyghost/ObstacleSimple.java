package com.flappy.flappyghost;

/**
 * Obstacle qui se deplace en 'x' uniquement.
 */
public class ObstacleSimple extends Obstacle{

    /**
     * Deplace l'obstacle en 'x' seulement selon la vitesse du jeux.
     *
     * @param deltaTime difference de temps.
     */
    @Override
    public void move(double deltaTime)
    {
        setX(this.getX() - deltaTime * Modele.VITESSEX);
    }

}
