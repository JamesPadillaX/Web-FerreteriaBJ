package service;

import dao.CategoriaDAO;
import modelo.Categoria;

public class CategoriaService {
    private final CategoriaDAO categoriaDAO;

    public CategoriaService() {
        this.categoriaDAO = new CategoriaDAO();
    }

    public boolean categoriaExiste(String nombre) {
        return categoriaDAO.existeCategoriaPorNombre(nombre);
    }

    public boolean registrarCategoria(String nombre) {
        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);
        categoria.setEstado(1); // Activa por defecto
        return categoriaDAO.guardarCategoria(categoria);
    }
}
