package com.flappy.flappyghost;// Jordan Boulais-Richard

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Fenetre de jeux et elements graphics/animation.
 *
 */
public class FlappyGhost extends Application {

    Controleur controleur = new Controleur(this);
    public static int WINDOW_WIDTH = 840;
    public static int WINDOW_HEIGHT = 440;
    public static int GAME_WIDTH = 640;
    public static int GAME_HEIGHT = 400;
    private Scene scene;
    private Canvas zoneDeJeux;
    private Timer timer;
    private HBox barDuBas;
    private GraphicsContext context;
    private Label score;
    private Button pause;
    private CheckBox debugCheck;

    /**
     * Getter attribut 'timer'.
     *
     * @return attribut 'timer'.
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     * Getter attribut 'context'.
     *
     * @return attribut 'context'.
     */
    public GraphicsContext getContext() {
        return context;
    }

    /**
     * Getter attribut 'score'.
     *
     * @return attribut 'score'.
     */
    public Label getScore() {
        return score;
    }

    /**
     *
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set. The primary stage will be embedded in
     * the browser if the application was launched as an applet.
     * Applications may create other stages, if needed, but they will not be
     * primary stages and will not be embedded in the browser.
     *
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        // ----------- SET UP UI -----------

        VBox root = new VBox();
        root.setAlignment(Pos.BASELINE_CENTER);
        scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Zone de Jeux
        zoneDeJeux = new Canvas(GAME_WIDTH, GAME_HEIGHT);
        context = zoneDeJeux.getGraphicsContext2D();

        root.getChildren().add(zoneDeJeux);

        // Bar du bas
        barDuBas = new HBox();
        barDuBas.setAlignment(Pos.BASELINE_CENTER);

        pause = new Button("Pause");
        Separator pauseSep = new Separator(Orientation.VERTICAL);
        debugCheck = new CheckBox("Mode debug");
        Separator debugSep = new Separator(Orientation.VERTICAL);
        score = new Label("Score: 0");
        barDuBas.getChildren().add(pause);
        barDuBas.getChildren().add(pauseSep);
        barDuBas.getChildren().add(debugCheck);
        barDuBas.getChildren().add(debugSep);
        barDuBas.getChildren().add(score);
        root.getChildren().add(barDuBas);

        timer = new Timer(context, score);
        setUpEvents();

        // Debut Animation ICI
        timer.start();

        // Stage PARAM and SHOW
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("/fichiersFH/ghost.png"));
        primaryStage.setTitle("FLAPPY GHOST");
        primaryStage.show();
    }

    /**
     * Ajout des events aux nodes de l'applications.
     * Tel que appuie sur space pour faire sauter le fantome,
     * appuie sur les boutons pauses et debug.
     *
     */
    private void setUpEvents()
    {

        scene.setOnKeyPressed((value) -> {
            if (value.getCode() == KeyCode.SPACE) {
                controleur.getModele().getGhost().setVy(-300);
            }
        });

        EventHandler<ActionEvent> pressPauseButton = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (Modele.INPAUSE) {
                    Modele.INPAUSE = false;
                } else {

                    Modele.INPAUSE = true;
                    zoneDeJeux.requestFocus();
                }
            }
        };

        EventHandler<ActionEvent> pressDebugCheck = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (Modele.DEBUGMODE) {
                    Modele.DEBUGMODE = false;
                    controleur.resetPartie();
                } else {
                    Modele.DEBUGMODE = true;
                }

                zoneDeJeux.requestFocus();
            }
        };

        pause.setOnAction(pressPauseButton);
        debugCheck.setOnAction(pressDebugCheck);

        Platform.runLater(() -> {
            zoneDeJeux.requestFocus();
        });

        scene.setOnMouseClicked((event) -> {
            zoneDeJeux.requestFocus();
        });

        }


    // ANIMATION TIMER HERE
    public class Timer extends AnimationTimer {

        GraphicsContext context;
        Canvas canvas;
        double WIDTH;
        double HEIGHT;
        private double lastTime = 0;
        private double timeSinceLastObject;
        private Label score;
        private long nowTime;

        /**
         * Getter attribut 'nowTime'.
         * Afin de rendre accessible le timestamp au autres classes.
         *
         * @return attribut 'nowTime'.
         */
        public long getNowTime() {
            return nowTime;
        }

        /**
         * Getter attribut 'timeSinceLastObject'.
         *
         * @return attribut 'timeSinceLastObject'.
         */
        public double getTimeSinceLastObject() {
            return timeSinceLastObject;
        }


        /**
         * Setter attribut 'timeSinceLastObject'.
         *
         * @param timeSinceLastObject valeur pour 'timeSinceLastObject'.
         */
        public void setTimeSinceLastObject(double timeSinceLastObject) {
            this.timeSinceLastObject = timeSinceLastObject;
        }

        /**
         * Constructeur de Timer.
         * Assign valeurs de depart a ses attributs.
         *
         * @param context GraphicalContext du canvas.
         * @param score Label du score.
         */
        // CONSTRUCTEUR
        public Timer(GraphicsContext context, Label score) {
            this.context = context;
            this.score = score;
            this.canvas = context.getCanvas();
            this.WIDTH = canvas.getWidth();
            this.HEIGHT = canvas.getHeight();
        }

        /**
         * The timestamp of the current frame given in nanoseconds. This
         *  value will be the same for all {@code AnimationTimers} called
         *  during one frame.
         *
         * @param now now in nanoseconds
         *
         */
        @Override
        public void handle(long now) {

            // SKIP PREMIERE SECONDE
            if (lastTime == 0) {
                lastTime = now;
                timeSinceLastObject = now;
                return;
            }

            double deltaTime = (now - lastTime) * 1e-9; // In seconds
            nowTime = now;

            // Handle ne s'arrete pas lorqu'on appuie sur timer.stop()
            // (Maniere simple de vraiment stopper le jeux)
            if (Modele.INPAUSE)
            {
                lastTime = now;
                return;
            }

            controleur.updateVueModele(deltaTime);

            lastTime = now;
        }

        }; // FIN ANIMATION TIMER

    public static  void main(String[] args) {launch();}

    }

