/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Modelo.Categoria;
import Util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yuri guevara
 */
public class CategoriaDAO {

    public void crearCategoria(Categoria categoria) throws SQLException {
        String sql = "INSERT INTO Categorias (nombre_categoria, descripcion_categoria) VALUES (?, ?)";
        try (Connection c = ConexionDB.getConnection(); PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNombreCategoria());
            stmt.setString(2, categoria.getDescripcionCategoria());
            stmt.executeUpdate();
        }
    }

    // Método para leer todas las categorías
    public List<Categoria> leerTodasCategorias() throws SQLException {
        String sql = "SELECT * FROM Categorias";
        List<Categoria> categorias = new ArrayList<>();
        try (Connection c = ConexionDB.getConnection(); PreparedStatement stmt = c.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("id_categoria"));
                categoria.setNombreCategoria(rs.getString("nombre_categoria"));
                categoria.setDescripcionCategoria(rs.getString("descripcion_categoria"));
                categorias.add(categoria);
            }
        }
        return categorias;
    }

    // Método para actualizar una categoría
    public void actualizarCategoria(Categoria categoria) throws SQLException {
        String sql = "UPDATE Categorias SET nombre_categoria = ?, descripcion_categoria = ? WHERE id_categoria = ?";

        try (Connection c = ConexionDB.getConnection(); PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNombreCategoria());
            stmt.setString(2, categoria.getDescripcionCategoria());
            stmt.setInt(3, categoria.getIdCategoria());
            stmt.executeUpdate();
        }
    }

    // Método para eliminar una categoría
    public void eliminarCategoria(int idCategoria) throws SQLException {
        String sql = "DELETE FROM Categorias WHERE id_categoria = ?";

        try (Connection c = ConexionDB.getConnection(); PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, idCategoria);
            stmt.executeUpdate();
        }
    }

    // Método temporal para probar
    public static void main(String[] args) {
        try {
            CategoriaDAO dao = new CategoriaDAO();
            // Eliminar una categoría
            dao.eliminarCategoria(4); // ID a eliminar
            System.out.println("Categoría eliminada.");

            // Leer y mostrar todas las categorías para verificar
            List<Categoria> categorias = dao.leerTodasCategorias();
            System.out.println("\nLista de categorías:");
            for (Categoria cat : categorias) {
                System.out.println("ID: " + cat.getIdCategoria()
                        + ", Nombre: " + cat.getNombreCategoria()
                        + ", Descripción: " + cat.getDescripcionCategoria());
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar: " + e.getMessage());
        }
    }

}
