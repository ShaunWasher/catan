import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class CatanGame extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int TILE_SIZE = 40;
    private static final int BOARD_SIZE = 19;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Rectangle tile = new Rectangle(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                tile.setFill(Color.LIGHTGRAY);
                tile.setStroke(Color.BLACK);
                root.getChildren().add(tile);
            }
        }

        stage.setTitle("Catan Board");
        stage.setScene(scene);
        stage.show();
    }
}
