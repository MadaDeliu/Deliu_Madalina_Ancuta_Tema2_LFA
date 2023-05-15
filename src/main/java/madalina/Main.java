package madalina;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;

import java.util.ArrayList;


public class Main extends Application {

    ArrayList<Circle> circles = new ArrayList<>();

    ArrayList<Text> circleText = new ArrayList<>();

    ArrayList<Text> lineText = new ArrayList<>();
    ArrayList<Line> lines = new ArrayList<Line>();
    Circle previousCircle = null;
    int id = 0;
    TextField  value = new TextField();
    TextField key = new TextField();

    @Override
    public void start(Stage stage) {
        Group root = new Group();


        EventHandler<MouseEvent> createHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.getButton() == MouseButton.PRIMARY) {
                    boolean found;
                    if (e.getClickCount() == 1) {
                        found = false;

                        for (Circle circle : circles) {
                            if ((e.getX() < circle.getCenterX() + 100.0f) && (e.getX() > circle.getCenterX() - 100.0f)) {
                                if ((e.getY() < circle.getCenterY() + 100.0f) && (e.getY() > circle.getCenterY() - 100.0f)) {
                                    found = true;

                                    if (previousCircle == null) {
                                        previousCircle = circle;
                                    } else if(circle!=previousCircle){
                                        boolean isFound = false;
                                        Line line = new Line(previousCircle.getCenterX(), previousCircle.getCenterY(), circle.getCenterX(), circle.getCenterY());
                                        for(Line currentLine: lines){
                                            if(lines.size()==0)
                                            {
                                                break;
                                            }
                                            if(currentLine.getStartX()==line.getStartX()&&currentLine.getEndX()==line.getEndX() && currentLine.getStartY()==line.getStartY()&&currentLine.getEndY()==line.getEndY()
                                            || (currentLine.getEndX()==line.getStartX()&&currentLine.getStartX()==line.getEndX() && currentLine.getEndY()==line.getStartY()&&currentLine.getStartY()==line.getEndY())){
                                                lineText.get(lines.indexOf(currentLine)).setText("Value: " + value.getText()+"\n"+ "Key: " + key.getText());
                                                isFound=true;
                                                previousCircle = null;
                                                break;
                                            }
                                        }
                                            if(isFound==false){
                                                Text text = new Text();
                                                text.setX((previousCircle.getCenterX() + circle.getCenterX()) / 2);
                                                text.setY((previousCircle.getCenterY() + circle.getCenterY()) / 2);
                                                text.setText("Value: " + value.getText()+"\n"+ "Key: " + key.getText());
                                                lineText.add(text);
                                                line.setStroke(Color.PALEGREEN);
                                                lines.add(line);
                                                root.getChildren().add(lineText.get(lineText.lastIndexOf(text)));
                                                root.getChildren().add(lines.get(lines.lastIndexOf(line)));
                                                previousCircle = null;
                                            }
                                      }
                                    else {
                                        circleText.get(circles.indexOf(circle)).setText("Value: " + value.getText()+"\n"+ "Key: " + key.getText());
                                        previousCircle = null;
                                    }
                                }
                            }
                        }
                        if (found == false && e.getY()>200) {
                            Circle circle1 = new Circle();
                            circle1.setCenterX(e.getX());
                            circle1.setCenterY(e.getY());
                            circle1.setRadius(100.0f);
                            circle1.setFill(Color.PALEGREEN);
                            circle1.setStroke(Color.BLUE);
                            circles.add(circle1);
                            root.getChildren().add(circles.get(circles.lastIndexOf(circle1)));
                            Text text = new Text();
                            text.setX(e.getX()-14);
                            text.setY(e.getY()-5);
                            text.setText("Value: " + value.getText()+"\n"+ "Key: " + key.getText());
                            circleText.add(text);
                            root.getChildren().add(circleText.get(circleText.lastIndexOf(text)));
                        }
                    }
                } else if (e.getButton() == MouseButton.SECONDARY) {

                    for (int i = 0; i<lines.size(); i++) {
                        Line line = lines.get(i);
                        double m =  (line.getStartY()-line.getEndY())/(line.getStartX()-line.getEndX());
                        if(((m*(e.getX()-line.getEndX())-20) < (e.getY()-line.getEndY()) && (e.getY()-line.getEndY()) < (m*(e.getX()-line.getEndX())+20)) &&
                                Math.min(line.getStartX()-20, line.getEndX()+20) < e.getX() && e.getX() < Math.max(line.getStartX()-20, line.getEndX()+20) &&
                                Math.min(line.getStartY()-20, line.getEndY()+20) < e.getY() && e.getY() < Math.max(line.getStartY()-20, line.getEndY()+20)){
                            root.getChildren().remove(lineText.get(lines.indexOf(line)));
                            lineText.remove(lines.indexOf(line));
                            root.getChildren().remove(line);
                            lines.remove(line);
                            i--;
                        }
                    }
                    for (int i = 0; i < circles.size(); i++) {
                        Circle circle = circles.get(i);
                        if ((e.getX() < circle.getCenterX() + 100.0f) && (e.getX() > circle.getCenterX() - 100.0f)) {
                            if ((e.getY() < circle.getCenterY() + 100.0f) && (e.getY() > circle.getCenterY() - 100.0f)) {
                                for (int j = 0; j < lines.size(); j++) {
                                    Line line = lines.get(j);
                                    if (line.getStartX() == circle.getCenterX()) {
                                        if (line.getStartY() == circle.getCenterY()) {
                                            root.getChildren().remove(lineText.get(lines.indexOf(line)));
                                            lineText.remove(lines.indexOf(line));
                                            root.getChildren().remove(line);
                                            lines.remove(line);
                                            j--;
                                        }
                                    } else if (line.getEndX() == circle.getCenterX()) {
                                        if (line.getEndY() == circle.getCenterY()) {
                                            root.getChildren().remove(lineText.get(lines.indexOf(line)));
                                            lineText.remove(lines.indexOf(line));
                                            root.getChildren().remove(line);
                                            lines.remove(line);
                                            j--;
                                        }
                                    }
                                }
                                root.getChildren().remove(circleText.get(circles.indexOf(circle)));
                                circleText.remove(circles.indexOf(circle));
                                root.getChildren().remove(circle);
                                circles.remove(circle);
                                i--;
                            }
                        }
                    }
                }
            }
        };


        EventHandler<MouseEvent> dragHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if ((e.getX() < circles.get(id).getCenterX() + 100.0f) && (e.getX() > circles.get(id).getCenterX() - 100.0f)) {
                    if ((e.getY() < circles.get(id).getCenterY() + 100.0f) && (e.getY() > circles.get(id).getCenterY() - 100.0f)) {
                        for (Line line : lines) {
                            if (line.getStartX() == circles.get(id).getCenterX()) {
                                if (line.getStartY() == circles.get(id).getCenterY()) {
                                    line.setStartX(e.getX());
                                    line.setStartY(e.getY());
                                    lineText.get(lines.indexOf(line)).setX((line.getStartX()+line.getEndX())/2);
                                    lineText.get(lines.indexOf(line)).setY((line.getStartY()+line.getEndY())/2);
                                }
                            } else if (line.getEndX() == circles.get(id).getCenterX()) {
                                if (line.getEndY() == circles.get(id).getCenterY()) {
                                    line.setEndX(e.getX());
                                    line.setEndY(e.getY());
                                    lineText.get(lines.indexOf(line)).setX((line.getStartX()+line.getEndX())/2);
                                    lineText.get(lines.indexOf(line)).setY((line.getStartY()+line.getEndY())/2);
                                }
                            }
                        }
                        circles.get(id).setCenterY(e.getY());
                        circles.get(id).setCenterX(e.getX());
                        circleText.get(id).setX(e.getX()-14);
                        circleText.get(id).setY(e.getY()-5);
                    }
                }else {
                    for (Circle circle : circles
                    ) {
                        if ((e.getX() < circle.getCenterX() + 100.0f) && (e.getX() > circle.getCenterX() - 100.0f)) {
                            if ((e.getY() < circle.getCenterY() + 100.0f) && (e.getY() > circle.getCenterY() - 100.0f)) {
                                for (Line line : lines) {
                                    if (line.getStartX() == circle.getCenterX()) {
                                        if (line.getStartY() == circle.getCenterY()) {
                                            line.setStartX(e.getX());
                                            line.setStartY(e.getY());
                                            lineText.get(lines.indexOf(line)).setX((line.getStartX()+line.getEndX())/2);
                                            lineText.get(lines.indexOf(line)).setY((line.getStartY()+line.getEndY())/2);
                                        }
                                    } else if (line.getEndX() == circle.getCenterX()) {
                                        if (line.getEndY() == circle.getCenterY()) {
                                            line.setEndX(e.getX());
                                            line.setEndY(e.getY());
                                            lineText.get(lines.indexOf(line)).setX((line.getStartX()+line.getEndX())/2);
                                            lineText.get(lines.indexOf(line)).setY((line.getStartY()+line.getEndY())/2);
                                        }
                                    }
                                }
                                circle.setCenterY(e.getY());
                                circle.setCenterX(e.getX());
                                id = circles.indexOf(circle);
                            }
                        }
                    }
                }
            }
        };



        value.setLayoutX(50);
        value.setLayoutY(0);
        Label valueLabel = new Label("Value");
        valueLabel.setLayoutX(2);
        valueLabel.setLayoutY(0);


        key.setLayoutX(50);
        key.setLayoutY(50);
        Label keyLabel = new Label("Key");
        keyLabel.setLayoutX(2);
        keyLabel.setLayoutY(50);


        root.getChildren().add(key);
        root.getChildren().add(value);
        root.getChildren().add(keyLabel);
        root.getChildren().add(valueLabel);


        Scene scene = new Scene(root, 1000, 900);
        //Setting title to the Stage
        stage.setTitle("Drawing a Circle");
        //Adding scene to the stage
        stage.setScene(scene);


        //Displaying the contents of the stage
        stage.show();

        stage.addEventFilter(MouseEvent.MOUSE_CLICKED, createHandler);
        stage.addEventFilter(MouseEvent.MOUSE_DRAGGED, dragHandler);

    }

    public static void main(String args[]) {
        launch(args);
    }
}
