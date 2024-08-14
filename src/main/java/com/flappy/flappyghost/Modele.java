package com.flappy.flappyghost;

import java.util.ArrayList;
import java.util.Random;

/**
 * Contient les donnes essentiel au jeux et calcul les collisions
 */
public class Modele {

    public static boolean DEBUGMODE = false;
    public static boolean INPAUSE = false;
    private boolean increaseDifficulty = false;
    private int score;
    private int obstaclesDepasser; // Afin d'augmenter la difficulte.
    private Entity[] entities = new Entity[]{};
    private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
    private BG[] bgs = new BG[]{new BG(0,0), new BG(640, 0)};
    private Ghost ghost = new Ghost();
    public static double GRAVITY = 500;
    public static double MAX_VY = 300;
    public static double VITESSEX = 120;
    public Random random = new Random();

    // GETTER
    //

    /**
     * Getter attribut 'point'.
     *
     * @return attribut 'point'.
     */
    public int getScore() {
        return score;
    }

    /**
     * Getter attribut 'obstacleDepasser'.
     * (Nomber d'obstacles depasser)
     *
     * @return attribut 'obstacleDepasser'.
     */
    public int getObstacleDepasser() {
        return obstaclesDepasser;
    }

    /**
     * Getter attribut 'entities'.
     *
     * @return attribut 'entities'.
     */
    public Entity[] getEntities() {
        return entities;
    }

    /**
     * Getter attribut 'obstacles'.
     *
     * @return attribut 'obstacles'.
     */
    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    /**
     * Getter attribut 'bgs'.
     *
     * @return attribut 'bgs'.
     */
    public BG[] getBgs() {
        return bgs;
    }

    /**
     * Getter attribut 'ghost'.
     *
     * @return attribut 'ghost'.
     */
    public Ghost getGhost() {
        return ghost;
    }

    // SETTER
    //

    /**
     * Setter attribut 'points'.
     *
     * @param score value pour attribut 'points'.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Setter attribut 'ObstacleDepasser'.
     *
     * @param obstacleDepasser value pour attribut 'ObstacleDepasser'.
     */
    public void setObstaclesDepasser(int obstacleDepasser) {
        this.obstaclesDepasser = obstacleDepasser;
    }

    /**
     * Setter attribut 'entities'.
     *
     * @param entities value pour attribut 'entities'.
     */
    public void setEntities(Entity[] entities) {
        this.entities = entities;
    }

    /**
     * Setter attribut 'obstacles'.
     *
     * @param obstacles value pour attribut 'obstacles'.
     */
    public void setObstacles(ArrayList<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    /**
     * Setter attribut 'bgs'.
     *
     * @param bgs value pour attribut 'bgs'.
     */
    public void setBgs(BG[] bgs) {
        this.bgs = bgs;
    }

    /**
     * Setter attribut 'ghost'.
     *
     * @param ghost value pour attribut 'ghost'.
     */
    public void setGhost(Ghost ghost) {
        this.ghost = ghost;
    }


    /**
     * Genere aleatoirement un nouvel obstacle parmis
     * ObstacleSimple, ObstacleSinus et ObstacleQuantique.
     * Nouvel obstacle est ajouter a l'attribut 'obstacles'.
     */
    public void generateNewObstacle()
    {
        int randomObstacle = random.nextInt(3);
        Obstacle obstacle = null;
        switch (randomObstacle) {
            case 0:
                obstacle = new ObstacleSimple();
                break;

            case 1:
                obstacle = new ObstacleSinus();
                break;

            case 2:
                obstacle = new ObstacleQuantique();
        }

        this.obstacles.add(obstacle);

    }

    /**
     * Pour chaque obstacle n'ayant pas l'attribut 'isDepasser'
     * a 'true', on regarde si son extremite de droite a depasser
     * l'extremite gauche de Ghost.
     *
     */
    public boolean calulateObstacleDepasse(){

        for (Obstacle obstacle : this.obstacles)
        {
            if (obstacle.isDepasser()){
                continue;
            }

            if (obstacle.getX() + obstacle.getSize() < this.ghost.getX())
            {
                obstacle.setDepasser(true);;
                return true;

            }

        }

        return false;
    }

    /**
     * Verifie si il y a collision entre Ghost et les Obstacles.
     *
     * @return collision null|Obstacle
     */
    public Obstacle collision()
    {
        Obstacle collision = null;

        for (Obstacle obstacle : this.obstacles)
        {
            collision = calculateCollision(obstacle);
            if (collision != null)
            {
                return collision;
            }
        }

        return collision;

    }

    /**
     * Regarde pour collision entre Ghost et un Obstacle.
     *
     * @param obstacle Obstacle avec lequel verifier si il y a colision.
     */
    public Obstacle calculateCollision(Obstacle obstacle){

        Obstacle collision = null;

        double distance = Math.sqrt( Math.pow(ghost.getX() - obstacle.getX() ,2) + Math.pow(ghost.getY() - obstacle.getY() ,2) );

        double sommeRayon = ghost.getSize()/2 + obstacle.getSize()/2;

        if (distance < sommeRayon)
        {
            collision = obstacle;
        }

        return collision;
    }

}
