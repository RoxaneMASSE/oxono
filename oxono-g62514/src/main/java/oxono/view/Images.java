package oxono.view;
import javafx.scene.image.Image;
public class Images {
    public static Image getImage(Integer boxType) {
        Image image;

        switch(boxType) {
            case 1 : image = new Image("/Images/pink_x_box.png", 50, 50, false, false);
                break;
            case 2 : image = new Image("/Images/black_x_box.png", 50, 50, false, false);
                break;
            case 3 : image = new Image("/Images/totem_x_box.png", 50, 50, false, false);
                break;
            case 4 : image = new Image("/Images/pink_o_box.png", 50, 50, false, false);
                break;
            case 5 : image = new Image("/Images/black_o_box.png", 50, 50, false, false);
                break;

            case 6 : image = new Image("/Images/totem_o_box.png", 50, 50, false, false);
                break;
            case 7 : image = new Image("/Images/right_box.png", 50, 50, false, false);
                break;
            case 8 : image = new Image("/Images/wrong_box.png", 50, 50, false, false);
                break;
            case 9 : image = new Image("/Images/pawn_pink_x.png", 50, 50, false, false);
                break;
            case 10 : image = new Image("/Images/pawn_pink_o.png", 50, 50, false, false);
                break;
            case 11 : image = new Image("/Images/pawn_black_x.png", 50, 50, false, false);
                break;
            case 12 : image = new Image("/Images/pawn_black_o.png", 50, 50, false, false);
                break;
            case 13 : image = new Image("/Images/pawn_totem_x.png", 50, 50, false, false);
                break;
            case 14 : image = new Image("/Images/pawn_totem_o.png", 50, 50, false, false);
                break;
            default :
                image = new Image("/Images/free_box.png", 50, 50, false, false);;
        }
        return image;
    }
}
