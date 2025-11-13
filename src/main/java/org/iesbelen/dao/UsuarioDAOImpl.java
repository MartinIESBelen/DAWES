package org.iesbelen.dao;

import org.iesbelen.model.Usuario;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static org.iesbelen.dao.AbstractDAOImpl.connectDB;

public class UsuarioDAOImpl implements UsuarioDAO {
    @Override
    public void create(Usuario usuario) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = connectDB();

            ps = conn.prepareStatement("INSERT INTO usuarios (usuario, password, roles) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            int idx = 1;
            ps.setString(idx++);

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
        return List.of();
    }

    @Override
    public Optional<Usuario> find(int id) {
        return Optional.empty();
    }

    @Override
    public void update(Usuario usuario) {

    }
}
