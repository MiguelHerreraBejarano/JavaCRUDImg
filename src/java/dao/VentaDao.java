package dao;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author FABIAN
 */
public class VentaDao {
    JdbcTemplate jdbcTemplate;
    ConectarDB con = new ConectarDB();
    
    public List consultarVentas(){
        List datos = new ArrayList();
         this.jdbcTemplate = new JdbcTemplate(con.conectar());           
        String sql = "select v.Cod_venta, v.id_cliente, c.nombre cliente, v.fecha_venta, "
                    +" v.cod_producto, p.nombre producto "
                    +" from venta v, cliente c, producto p "
                    +" where c.id = v.id_cliente "
                    +" and v.cod_producto = p.cod_producto";
        datos = this.jdbcTemplate.queryForList(sql);
        
        return datos;
    }

    public int consultarCodigo(){
        int cod = 0;
        
         this.jdbcTemplate = new JdbcTemplate(con.conectar());           
        String sql = "select max(Cod_venta)+1 as codigo from venta;";  
        cod = this.jdbcTemplate.queryForObject(sql, Integer.class);
        
        return cod;
    

  }  
 
}
