package org.iesbelen.dao;

import org.iesbelen.model.Producto;
import org.iesbelen.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.iesbelen.dao.AbstractDAOImpl.connectDB;

public class UsuarioDAOImpl extends AbstractDAOImpl implements UsuarioDAO {
    @Override
    public void create(Usuario usuario) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rsGenKeys = null;

        try{
            conn = connectDB();

            ps = conn.prepareStatement("INSERT INTO usuarios (usuario, password, roles) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            int idx = 1;
            ps.setString(idx++, usuario.getUsuario());
            ps.setString(idx++, usuario.getPassword());
            ps.setString(idx++, usuario.getRol());

            int row = ps.executeUpdate();
            if(row == 0){
                System.out.println("No existe el usuario con el id " + usuario.getUsuario());
            }
            rsGenKeys = ps.getGeneratedKeys();
            if(rsGenKeys.next()){
                usuario.setIdUsuario(rsGenKeys.getInt(1));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            closeDb(conn, ps, rs);
        }
    }

    @Override
    public List<Usuario> getAll() {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        List<Usuario> listUser = new ArrayList<>();

        try {
            conn = connectDB();

            // Se utiliza un objeto Statement dado que no hay parámetros en la consulta.
            s = conn.createStatement();

            rs = s.executeQuery("SELECT * FROM usuarios");
            while (rs.next()) {
                Usuario user = new Usuario();
                int idx = 1;
                user.setIdUsuario(rs.getInt(idx++));
                user.setUsuario(rs.getString(idx++));
                user.setRol(rs.getString(idx));
                listUser.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, s, rs);
        }
        return listUser;
    }

    @Override
    public Optional<Usuario> find(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = connectDB();

            ps = conn.prepareStatement("SELECT * FROM usuarios WHERE idUsuario = ?");

            int idx = 1;
            ps.setInt(idx++, id);

            rs = ps.executeQuery();
            if(rs.next()){
                Usuario user = new Usuario();
                idx = 1;
                user.setIdUsuario(rs.getInt(idx++));
                user.setUsuario(rs.getString(idx++));
                user.setRol(rs.getString(idx));

                return Optional.of(user);
            }

        }catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }finally{
            closeDb(conn, ps, rs);
        }

        return Optional.empty();
    }

    @Override
    public void update(Usuario usuario) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;


        try {
            conn = connectDB();

            ps = conn.prepareStatement("UPDATE producto SET usuario = ?,roles = ?  WHERE id = ?");
            int idx = 1;
            ps.setString(idx++, usuario.getUsuario());
            ps.setString(idx++, usuario.getRol());
            ps.setInt(idx, usuario.getIdUsuario());

            int rows = ps.executeUpdate();

            if (rows == 0)
                System.out.println("Update de usuario 0 recuests.");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }
    }

    @Override
    public void delete(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = connectDB();

            ps = conn.prepareStatement("DELETE FROM usuario WHERE id = ?");
            int idx = 1;
            ps.setInt(idx, id);

            int rows = ps.executeUpdate();

            if (rows == 0)System.out.println("Delete de usuario 0 recuests.");
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            {
                closeDb(conn, ps, rs);
            }
        }
    }
}
