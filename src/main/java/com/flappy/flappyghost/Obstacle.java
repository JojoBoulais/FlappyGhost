package com.flappy.flappyghost;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

/**
 * Class abstraite des obstacles.
 */
public abstract class Obstacle extends Entity{

    private boolean depasser = false;
    Random random = new Random();

    /**
     * Constructeur de Obstacle.
     * Assigne aleatoirement une image, un size et une position en 'y' de depart.
     *
     */
    Obstacle(){
        // Set Random size, starting ypos and image.
        this.setImage(getRandomImage());
        int randomSize = (random.nextInt(36) +10) * 2;
        this.setSize(randomSize);
        this.setX(FlappyGhost.GAME_WIDTH + this.getSize()/2);
        // Assign random first Y pos
        this.setY(random.nextInt(FlappyGhost.GAME_HEIGHT - (int) this.getSize()+1));

    }

    /**
     * Getter attribut 'depasser'.
     *
     * @return depasser attribut 'depasser'.
     */
    public boolean isDepasser() {
        return depasser;
    }

    /**
     * Setter attribut 'depasser'.
     *
     * @param depasser valeur pour attribut 'depasser'.
     */
    public void setDepasser(boolean depasser) {
        this.depasser = depasser;
    }

    /**
     * Return a Random obstacle Image object.
     *
     * @return Image from obstacles files.
     */
    private ImageView getRandomImage(){

        Random random = new Random();
        int rint = random.nextInt(27);
        Image image = new Image("/fichiersFH/obstacles/" + Integer.toString(rint) + ".png");
        return new ImageView(image);

    }

}
