package com.flappy.flappyghost;

/**
 * Obstacle qui se deplace selon une fonction sinusoidale en 'y'
 * a partir de son point central.
 */
public class ObstacleSinus extends Obstacle {

    double timeLife = 0;
    double startY = 0;

    /**
     * Constructeur ObstacleSinus.
     * Assign l'attribut startY qui represent le point en y
     * a partir du quel l'obstacle va osciller.
     */
    ObstacleSinus()
    {
        startY = this.getY();
    }

    /**
     * Deplace l'obstacle en 'x' selon la vitesse du jeux
     * Deplace l'obstacle en 'y' selon une function sinus
     * a partir d'une valeur y initial. ('startY' attribut)
     *
     * @param deltaTime difference de temps.
     */
    @Override
    public void move(double deltaTime)
    {

        setX(this.getX() - deltaTime * Modele.VITESSEX);
        setY(startY + Math.sin(timeLife*4)*50);
        timeLife += deltaTime;
    }

}
