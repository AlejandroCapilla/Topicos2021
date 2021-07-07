package sample.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.*;
public class Imagen {
    File file;
    FileInputStream fileIn;
    Image image;
    ImageView imgeView;
    byte[] data;
    int length;

    public Imagen(String ruta) throws FileNotFoundException {
        this.file = new File(ruta);
        this.fileIn = new FileInputStream(file);
        this.length = (int)file.length();
    }

    public Imagen(){
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public FileInputStream getFileIn() {
        return fileIn;
    }

    public void setFileIn(FileInputStream fileIn) {
        this.fileIn = fileIn;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ImageView getImgeView() {
        return imgeView;
    }

    public void setImgeView(Image imge) {
        this.imgeView = new ImageView(imge);
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) throws IOException {
        this.data = data;
        setImage(new Image(new ByteArrayInputStream(data)));
        setImgeView(this.image);
        imgeView.setPreserveRatio(true);
        imgeView.setFitHeight(30);
    }
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}

