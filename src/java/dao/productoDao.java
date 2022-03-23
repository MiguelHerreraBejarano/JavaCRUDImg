/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author SENA
 */
public class productoDao {
   JdbcTemplate jdbcTemplate;
    ConectarDB con = new ConectarDB();
    
    public List consultarProductosVentas() {
        List datos = new ArrayList();
        
            this.jdbcTemplate = new JdbcTemplate(con.conectar());
            String sql = "select cod_producto, nombre from producto";
            datos = this.jdbcTemplate.queryForList(sql);
            return datos;
        
        
    }
    
}
