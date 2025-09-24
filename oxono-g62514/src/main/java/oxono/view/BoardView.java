package oxono.view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import oxono.controller.Controller;

import java.util.ArrayList;
import java.util.List;

public class BoardView extends GridPane {
    public ImageView[][] imageView;
    private String totemSelected;
    private int posXTotemX;
    private int posYTotemX;
    private int posXTotemO;
    private int posYTotemO;
    private boolean isMoveMode;
    private boolean isAIPlayer;

    private Controller controller;

    public BoardView(int size, Controller controller) {
        totemSelected = " ";
        imageView = new ImageView[size][size];
        Image image = Images.getImage(0);
        this.posXTotemX = 0;
        this.posYTotemX = 0;
        this.posXTotemO = 0;
        this.posYTotemO = 0;
        this.isMoveMode = true;
        this.controller = controller;

       for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                ImageView imageViewTmp = new ImageView();
                imageViewTmp.setImage(image);
                imageView[i][j] = imageViewTmp;
                this.add(imageView[i][j], j, i, 1, 1);
                setDragDetected(imageViewTmp);
                setDragOver(imageViewTmp);
                setDragDropped(imageViewTmp, controller);
                setDragExited(imageViewTmp);
                setDragEntered(imageViewTmp);
                setMouseEntered(imageViewTmp);
                setMouseExited(imageViewTmp);
                setMouseClicked(imageViewTmp);
            }
       }
       this.setAlignment(Pos.CENTER);
    }

    public String getTotemSelected() {
        return totemSelected;
    }

    public void setImageView(Image image, int i, int j) {
        imageView[i][j].setImage(image);
    }

    public List<Integer> getReference(ImageView imageView) {
        List<Integer> references = new ArrayList<>();
        for (int i = 0; i < this.imageView.length; i++) {
            for (int j = 0; j < this.imageView.length; j++) {
                if (this.imageView[i][j] == imageView) {
                    references.add(i);
                    references.add(j);
                    return references;
                }
            }
        }
        return null;
    }

    public void setMoveMode(boolean moveMode) {
        isMoveMode = moveMode;
    }

    public String getSymbol(ImageView imageView) {
        List<Integer> references = getReference(imageView);
        if (references.get(0) == posXTotemX && references.get(1) == posYTotemX)  {
            return "X";
        } else if (references.get(0) == posXTotemO && references.get(1) == posYTotemO) {
            return "O";
        }
        return " ";
    }

    private boolean isValidTotemPosSource(ImageView imageView) {
        List<Integer> references = getReference(imageView);
        if ((references.get(0) == posXTotemX && references.get(1) == posYTotemX) || (references.get(0) == posXTotemO && references.get(1) == posYTotemO)) {
            return true;
        }
        return false;
    }

    public void setMouseEntered(ImageView current) {
        current.setOnMouseEntered((MouseEvent event) -> {
            if (!isMoveMode) {
                if (controller.isActionInsertValid(getReference(current).get(0), getReference(current).get(1))) {
                    current.setImage(Images.getImage(7));
                } else if (!isValidTotemPosSource(current)) {
                    current.setImage(Images.getImage(8));
                }
            } else {
                if (isValidTotemPosSource(current)) {
                    ((Node) event.getSource()).setCursor(Cursor.HAND);
                }
            }
        });
    }
    public void setMouseExited(ImageView current) {
        current.setOnMouseExited((MouseEvent event) -> {
            if (!isMoveMode) {
                controller.setRefreshBoard();
            }
        });
    }
    public void setMouseClicked(ImageView source) {
        source.setOnMouseClicked((MouseEvent event) -> {
            controller.actionInsert(getReference(source));
        });
    }

    private void setDragDetected(ImageView source) {
        source.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!isAIPlayer && isMoveMode && isValidTotemPosSource(source)) {
                    Dragboard db = source.startDragAndDrop(TransferMode.ANY);
                    if (getSymbol(source).equals("X")) {
                        db.setDragView(Images.getImage(13));
                    } else {
                        db.setDragView(Images.getImage(14));
                    }
                    ClipboardContent content = new ClipboardContent();
                    content.putString(getSymbol(source));
                    db.setContent(content);
                    //((Node) mouseEvent.getSource()).setCursor(Cursor.CLOSED_HAND);
                    mouseEvent.consume();
                }
            }
        });
    }

    private void setDragOver(ImageView target) {
        target.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                if (dragEvent.getGestureSource() != target && dragEvent.getDragboard().hasString() && controller.isActionMoveValid(getReference(target).get(0), getReference(target).get(1), dragEvent.getDragboard().getString())) {
                        dragEvent.acceptTransferModes(TransferMode.MOVE);
                }
                dragEvent.consume();
            }
        });
   }
    private void setDragEntered(ImageView target) {
        target.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                if (dragEvent.getGestureSource() != target && dragEvent.getDragboard().hasString()) {
                    if (controller.isActionMoveValid(getReference(target).get(0), getReference(target).get(1), dragEvent.getDragboard().getString())) {
                        target.setImage(Images.getImage(7));
                    } else {
                        target.setImage(Images.getImage(8));
                    }
                }
                dragEvent.consume();
            }
        });
   }

    private void setDragExited(ImageView target) {
        target.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                controller.setRefreshBoard();
                dragEvent.consume();
            }
        });
   }

   private void setDragDropped(ImageView target, Controller controller) {
       target.setOnDragDropped(new EventHandler<DragEvent>() {
           @Override
           public void handle(DragEvent dragEvent) {
               Dragboard db = dragEvent.getDragboard();
               boolean success = false;
               if (db.hasString()) {
                   success = true;
               }

               controller.actionMoveTotem(getReference(target), db.getString());
               dragEvent.setDropCompleted(success);
               dragEvent.consume();
           }
       });
   }

    public void setPosXTotemX(int posXTotemX) {
        this.posXTotemX = posXTotemX;
    }

    public void setPosYTotemX(int posYTotemX) {
        this.posYTotemX = posYTotemX;
    }

    public void setPosXTotemO(int posXTotemO) {
        this.posXTotemO = posXTotemO;
    }

    public void setPosYTotemO(int posYTotemO) {
        this.posYTotemO = posYTotemO;
    }

    public void setAIPlayer(boolean AIPlayer) {
        isAIPlayer = AIPlayer;
    }
}
