package service;

import dao.CategoriaDAO;
import modelo.Categoria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CategoriaService {

    private static final Logger logger = LoggerFactory.getLogger(CategoriaService.class);
    private final CategoriaDAO categoriaDAO;

    public CategoriaService() {
        this.categoriaDAO = new CategoriaDAO();
    }

    public boolean categoriaExiste(String nombre) {
        try {
            return categoriaDAO.existeCategoriaPorNombre(nombre);
        } catch (Exception e) {
            logger.error("Error al verificar existencia de categoría: {}", nombre, e);
            return false;
        }
    }

    public boolean registrarCategoria(String nombre) {
        try {
            Categoria categoria = new Categoria();
            categoria.setNombre(nombre);
            categoria.setEstado(1);

            boolean guardado = categoriaDAO.guardarCategoria(categoria);
            if (!guardado) {
                logger.warn("No se pudo registrar la categoría: {}", nombre);
            }
            return guardado;
        } catch (Exception e) {
            logger.error("Error al registrar categoría: {}", nombre, e);
            return false;
        }
    }
}
