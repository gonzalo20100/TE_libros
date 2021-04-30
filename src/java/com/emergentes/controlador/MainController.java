package com.emergentes.controlador;

import com.emergentes.ConexionDB;
import com.emergentes.modelo.Libro;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String op;
            op = (request.getParameter("op") != null)? request.getParameter("op"):"list";
            ArrayList<Libro> lista = new  ArrayList<Libro>();
            ConexionDB canal = new ConexionDB();
            Connection conn = canal.conectar();
            PreparedStatement ps;
            ResultSet rs;
            
            if (op.equals("list")) {
                //para listar los datos
                String sql = "select * from libros";
                // para consultar de seleccion y almacenar el una coleccion
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    Libro lib = new Libro();
                    lib.setId(rs.getInt("id"));
                    lib.setIsbn(rs.getString("isbn"));
                    lib.setTitulo(rs.getString("titulo"));
                    lib.setCategoria(rs.getString("categoria"));
                    lista.add(lib);
                }
                request.setAttribute("lista", lista);
                //enviar a index.jsp para mostrar la informacion
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            
            if (op.equals("nuevo")) {
                //instanciar un objeto de la clase Libro
                Libro li = new Libro();                
                //El objeto se pone como tributo de request
                request.setAttribute("libro", li);
                //redireccionar a editar.jsp
                request.getRequestDispatcher("editar.jsp").forward(request, response);
            }
            
            if (op.equals("eliminar")) {
                //obtener el id
                int id = Integer.parseInt(request.getParameter("id"));
                //realizar la aeliminacion en la base de datos
                String sql = "delete from libros where id = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ps.executeUpdate();
                //redireccionar a MainController
                response.sendRedirect("MainController");
            }
            
            if (op.equals("editar")) {
                int id = Integer.parseInt(request.getParameter("id"));
                //para listar los datos
                String sql = "select * from libros where id=?";
                // para consultar de seleccion y almacenar el una coleccion
                ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Libro lib = new Libro();
                    lib.setId(rs.getInt("id"));
                    lib.setIsbn(rs.getString("isbn"));
                    lib.setTitulo(rs.getString("titulo"));
                    lib.setCategoria(rs.getString("categoria"));
                    lista.add(lib);
                }
                request.setAttribute("lista", lista);
                //enviar a index.jsp para mostrar la informacion
                request.getRequestDispatcher("edicion.jsp").forward(request, response);
            }
        } catch (SQLException ex) {
            System.out.println("ERROR EN LA CONEXION: >>>" + ex.getMessage());
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            
            String isbn = request.getParameter("isbn");
            String titulo = request.getParameter("titulo");
            String categoria = request.getParameter("categoria");
            
            Libro lib = new Libro();
            lib.setId(id);
            lib.setIsbn(isbn);
            lib.setTitulo(titulo);
            lib.setCategoria(categoria);
            
            ConexionDB canal = new ConexionDB();
            Connection conn = canal.conectar();
            PreparedStatement ps;
            ResultSet rs;
            
            if (id == 0) {
                // nuevo registro
                String sql = "insert into libros (isbn, titulo, categoria) values (?, ?, ?)";
                ps = conn.prepareStatement(sql);
                ps.setString(1, lib.getIsbn());
                ps.setString(2, lib.getTitulo());
                ps.setString(3, lib.getCategoria());
                ps.executeUpdate();
            }else{
                // edicion de registro
                String sql = "UPDATE libros SET isbn = ? , titulo = ? , categoria = ? WHERE id = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, lib.getIsbn());
                ps.setString(2, lib.getTitulo());
                ps.setString(3, lib.getCategoria());
                ps.setInt(4, lib.getId());
                ps.executeUpdate();
            }
            response.sendRedirect("MainController");
        } catch (SQLException ex) {
            System.out.println("ERROR EN SQL: " + ex.getMessage());
        }
    }
}