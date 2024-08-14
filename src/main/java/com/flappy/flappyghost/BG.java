package com.flappy.flappyghost;

import javafx.scene.image.ImageView;

/**
 * Backgrounds afficher dans la zone de jeux.
 * (2 BG qui defilent en permanence)
 *
 */
public class BG extends Entity {

    /**
     * Constructor
     *
     * @param posx Position en x.
     * @param posy Position en Y.
     */
    public BG(int posx, int posy)
    {
        this.setX(posx);
        this.setY(posy);
        this.setImage(new ImageView("/fichiersFH/bg.png"));
    }

    /**
     * Deplace le background selon la vitesse. (difficulte du jeu)
     * Deplacement en X seulement.
     *
     * @param deltaTime difference de temps depuis le dernier "moment".
     */
    @Override
    public void move(double deltaTime)
    {
        // Replace BG a 640 Lorsqu'il sort du cadre.
        if (this.getX() <= -640)
        {
            this.setX(640 - Modele.VITESSEX*deltaTime + (this.getX()+640));
        }else {
            this.setX(this.getX() - Modele.VITESSEX*deltaTime);
        }
    }

}
