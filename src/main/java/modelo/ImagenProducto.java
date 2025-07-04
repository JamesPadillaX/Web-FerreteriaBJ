package modelo;

public class ImagenProducto {
    private int idImagen;
    private int idProducto;
    private String rutaImagen;

    public ImagenProducto() {}

    public ImagenProducto(int idImagen, int idProducto, String rutaImagen) {
        this.idImagen = idImagen;
        this.idProducto = idProducto;
        this.rutaImagen = rutaImagen;
    }

    public int getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(int idImagen) {
        this.idImagen = idImagen;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }
}
