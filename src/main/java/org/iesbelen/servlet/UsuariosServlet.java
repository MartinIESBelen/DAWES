package org.iesbelen.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.iesbelen.dao.UsuarioDAO;
import org.iesbelen.dao.UsuarioDAOImpl;
import org.iesbelen.model.Usuario;
import org.iesbelen.utils.Utils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "usuariosServlet", value = "/tienda/usuarios/*")
public class UsuariosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher;
        UsuarioDAO usuDAO = new UsuarioDAOImpl();
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || "/".equals(pathInfo)) {

            // GET /usuarios
            List<Usuario> usuarios = usuDAO.getAll();
            request.setAttribute("listaUsuarios", usuarios);
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/usuarios.jsp");

        } else {

            pathInfo = pathInfo.replaceAll("/$", "");
            String[] pathParts = pathInfo.split("/");

            // GET /usuarios/crear
            if (pathParts.length == 2 && "crear".equals(pathParts[1])) {

                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/crear-usuario.jsp");

            }
            // GET /usuarios/{id}
            else if (pathParts.length == 2) {

                try {
                    int id = Integer.parseInt(pathParts[1]);
                    Optional<Usuario> optUser = usuDAO.find(id);

                    if (optUser.isPresent()) {
                        request.setAttribute("usuario", optUser.get());
                        dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/detalle-usuario.jsp");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/tienda/usuarios");
                        return;
                    }

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    response.sendRedirect(request.getContextPath() + "/tienda/usuarios");
                    return;
                }

            }
            // GET /usuarios/editar/{id}
            else if (pathParts.length == 3 && "editar".equals(pathParts[1])) {

                try {
                    int id = Integer.parseInt(pathParts[2]);
                    Optional<Usuario> optUser = usuDAO.find(id);

                    if (optUser.isPresent()) {
                        request.setAttribute("usuario", optUser.get());
                        dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/editar-usuario.jsp");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/tienda/usuarios");
                        return;
                    }

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    response.sendRedirect(request.getContextPath() + "/tienda/usuarios");
                    return;
                }

            }
            // RUTA DESCONOCIDA
            else {
                response.sendRedirect(request.getContextPath() + "/tienda/usuarios");
                return;
            }
        }

        dispatcher.forward(request, response);
    }


    //Metodos Post
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UsuarioDAO usuDAO = new UsuarioDAOImpl();
        String pathInfo = request.getPathInfo();

        pathInfo = (pathInfo == null) ? "" : pathInfo.replaceAll("/$", "");
        String[] pathParts = pathInfo.split("/");

        // POST /usuarios/crear
        if (pathParts.length == 2 && "crear".equals(pathParts[1])) {

            String usuario = request.getParameter("usuario");
            String password = request.getParameter("password");
            String rol = request.getParameter("roles");

            String passwordHash = "";
            try {
                passwordHash = Utils.hashPassword(password);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

            Usuario nuevo = new Usuario();
            nuevo.setUsuario(usuario);
            nuevo.setPassword(passwordHash);
            nuevo.setRol(rol);

            usuDAO.create(nuevo);

            response.sendRedirect(request.getContextPath() + "/tienda/usuarios");
            return;
        }


        // POST /usuarios/editar/{id}
        if (pathParts.length == 3 && "editar".equals(pathParts[1])) {

            try {
                int id = Integer.parseInt(pathParts[2]);

                String usuario = request.getParameter("usuario");
                String password = request.getParameter("password");
                String rol = request.getParameter("roles");

                Usuario actualizado = new Usuario();
                actualizado.setIdUsuario(id);
                actualizado.setUsuario(usuario);

                if (password != null && !password.isEmpty()) {
                    try {
                        actualizado.setPassword(Utils.hashPassword(password));
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
                }

                actualizado.setRol(rol);

                usuDAO.update(actualizado);

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            response.sendRedirect(request.getContextPath() + "/tienda/usuarios");
            return;
        }


        // POST /usuarios/borrar/{id}
        if (pathParts.length == 3 && "borrar".equals(pathParts[1])) {

            try {
                int id = Integer.parseInt(pathParts[2]);
                usuDAO.delete(id);

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            response.sendRedirect(request.getContextPath() + "/tienda/usuarios");
            return;
        }

        // RUTA DESCONOCIDA
        response.sendRedirect(request.getContextPath() + "/tienda/usuarios");
    }
}
