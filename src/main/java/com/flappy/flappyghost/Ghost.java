package com.flappy.flappyghost;

import javafx.scene.image.ImageView;

/**
 *  Personnage/Joueur
 */
public class Ghost extends Entity{


    /**
     * Constructor de Ghost
     * Assign les attributs de base.
     * Position Ghost au centre de la zone de jeux.
     * Assign l'image de Ghost.
     */
    public Ghost()
    {
        // INIT GHOST POSITION
        this.setX(FlappyGhost.GAME_WIDTH/2 - 30);
        this.setY(FlappyGhost.GAME_HEIGHT/2 - 30);
        this.setImage(new ImageView("/fichiersFH/ghost.png"));
        this.getImage().setPreserveRatio(true);
        this.getImage().setFitWidth(60);
        this.setVy(0);
        this.setSize(60);
    }

    /**
     * Deplace Ghost en Y seulement selon sa vitesse et la gravite.
     * Lorsque que Ghost touche le haut ou bas de la zone de jeux
     * il rebondit.
     *
     * @param deltaTime difference de temps.
     */
    @Override
    public void move(double deltaTime)
    {

        this.setVy( this.getVy() + (float) deltaTime * Modele.GRAVITY);
        double newY = (float) (this.getY() + deltaTime * this.getVy());


        if (newY > FlappyGhost.GAME_HEIGHT - this.getSize())
        {
            newY = FlappyGhost.GAME_HEIGHT - this.getSize();
            this.setVy(this.getVy()*-1);
        } else if (newY < 0) {
            this.setVy(this.getVy()*-1);
            newY = 0;
        }

        if (this.getVy() > Modele.MAX_VY) {
            this.setVy(Modele.MAX_VY);
        } else if (this.getVy() < - Modele.MAX_VY) {
            this.setVy(- Modele.MAX_VY);
        }

        this.setY(newY);

    }

}
