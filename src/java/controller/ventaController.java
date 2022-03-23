package controller;

import dao.ClienteDao;
import dao.ConectarDB;
import dao.VentaDao;
import dao.productoDao;
import java.net.ConnectException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import models.Cliente;
import models.Venta;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author FABIAN
 */
public class ventaController {

    private JdbcTemplate jdbcTemplate;

    public ventaController() {
        ConectarDB con = new ConectarDB();
        
            this.jdbcTemplate = new JdbcTemplate(con.conectar());
       
    }

    @RequestMapping(value = "formVenta.htm")
    public ModelAndView formVenta() {
        ModelAndView mav = new ModelAndView();
        Venta venta = new Venta();
        VentaDao ventasDao = new VentaDao();
        List datos = ventasDao.consultarVentas();
        mav.addObject("venta", datos);
        mav.setViewName("views/formVenta");
        return mav;
    }
    @RequestMapping("formRegistrarVentas.htm")
    public ModelAndView formRegistrarVentas() {
        ModelAndView mav = new ModelAndView();
        List datos = new ArrayList();
        List codProductos = new ArrayList(); 
        Venta venta = new Venta();
        VentaDao ventaDao = new VentaDao();
        int codigo = ventaDao.consultarCodigo();
        productoDao productoDao = new productoDao();
        venta.setCod_venta(codigo);
        ClienteDao cliDao = new ClienteDao();
        datos = cliDao.consultarClientesVentas();
        mav.addObject("idClientes",datos);    
        codProductos = productoDao.consultarProductosVentas();
        mav.addObject("idProductos",codProductos);   
        mav.addObject("venta", venta);
        mav.setViewName("views/formRegistrarVentas");
        return mav;
    }
   
}
