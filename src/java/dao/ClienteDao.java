package dao;

import java.io.File;
import java.net.ConnectException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.swing.JOptionPane;
import models.Cliente;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;



public class ClienteDao {

    private static String UPLOAD_DIRECTORY = "..\\..\\web\\Imagenes\\fotos";
    
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50;
    
    JdbcTemplate jdbcTemplate;
    ConectarDB con = new ConectarDB();

    public List consultarCliente() {
        List datos = new ArrayList();
        
            this.jdbcTemplate = new JdbcTemplate(con.conectar());
            String sql = "select * from cliente";
            datos = this.jdbcTemplate.queryForList(sql);
        
        return datos;
    }

    public List consultarClientesVentas() {
        List datos = new ArrayList();
        
            this.jdbcTemplate = new JdbcTemplate(con.conectar());
            String sql = "select id, nombre from cliente";
            datos = this.jdbcTemplate.queryForList(sql);
            return datos;
       
    }
    public void  borrarImagen (String foto, String deletePaht, int id){
        final String DELETE_DIRECTORY = "..\\..\\web\\";
        this.jdbcTemplate = new  JdbcTemplate(con.conectar());
        String deleteFile = deletePaht + DELETE_DIRECTORY + foto ;
         System.out.println(deleteFile);
        File borrar = new File(deleteFile);
        if(borrar.delete()){
            String sql = "delete from cliente where id = ? ";
            jdbcTemplate.update(sql, id);
            
        }
        else{
            System.out.println("no se pudo borrar..");
        }
    }
    public void accUsuarioSinFoto(Cliente cli, List lista){
        this.jdbcTemplate = new JdbcTemplate(con.conectar());
        ArrayList<String> listados = new ArrayList<>();
            for(int i = 0; i <lista.size(); i++){
                
                FileItem fileItem = (FileItem) lista.get(i);
                listados.add(fileItem.getString());
                }
                cli.setNombre (listados.get (0));
                cli.setDireccion (listados.get (1));
                cli.setCorreo (listados.get (2));
                cli.setTelefono (listados.get (3));
                cli.setSalario(Integer.parseInt(listados.get(4)));
                
                String sql = "update cliente set nombre= ?, direccion = ?"
                        + " correo = ?, telefono = ?, salario = ?, where id=?";
                jdbcTemplate.update(sql, cli.getNombre(), cli.getDireccion(),
                        cli.getCorreo(), cli.getTelefono(), cli.getSalario(), cli.getId());
    }
    
     public void accUsuarioConImagenFoto(Cliente cli,
            boolean isMultipart,
            HttpServletRequest request,
            List items) {
        this.jdbcTemplate = new JdbcTemplate(con.conectar());
        
        ArrayList<String> listados = new ArrayList<>();
        if (isMultipart) {
            DiskFileItemFactory file = new DiskFileItemFactory();
            file.setSizeThreshold(MEMORY_THRESHOLD);
            file.setRepository(new File(System.getProperty("java.io.tmpdir")));
            ServletFileUpload fileUpload= new ServletFileUpload(file);
            fileUpload.setFileSizeMax(MAX_FILE_SIZE);
            fileUpload.setSizeMax(MAX_FILE_SIZE);
            
            String uploadPath = request.getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
            String deletePath= request.getServletContext().getRealPath("") + File.separator;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
        
            for (int i= 0; i < items.size(); i++) {
                FileItem fileItem = (FileItem) items.get(i);
                if (!fileItem.isFormField()) {
                    String fileName = new File(fileItem.getName()).getName();
                    String filePath = uploadPath + File.separator + "ID-" + listados.get(0) + "-" + fileName;
                    File uploadFile = new File(filePath);
                    String nameFile = ("Imagenes/fotos/" + "ID-" + listados.get(0) + "-" + fileName );
                    
                  try {
                      borrarImagenActualizada(cli.getfotoOld(), deletePath);
                      uploadFile.delete();
                      fileItem.write(uploadFile);
                      cli.setFoto(nameFile);
                  } catch (Exception e) {
                      System.out.printf("escritura..." + e.getMessage());
                  }
                } else {
                    listados.add(fileItem.getString());
                }
            }
            
            cli.setNombre(listados.get(0));
            cli.setDireccion(listados.get(1));
            cli.setCorreo(listados.get(2));
            cli.setTelefono(listados.get(3));
            cli.setSalario(Integer.parseInt(listados.get(4)));
           
        }
        
        String sql = "update cliente set nombre = ?, direccion = ?, "
                + "correo = ?, telefono = ?, salario = ?, foto = ? where id = ?";
        jdbcTemplate.update(sql, cli.getNombre(),cli.getDireccion(),
                cli.getCorreo(), cli.getTelefono(), cli.getSalario(), cli.getFoto(), cli.getId());
        }
    public void borrarImagenActualizada(String foto, String deletePath) {
        final String DELETE_DIRECTORY = "..\\..\\web\\";
        String deleteFile= deletePath + DELETE_DIRECTORY + foto ;
        File borrar = new File(deleteFile);
        if (borrar.delete()) {
            System.out.println("borrado");
        } 
          else {
            System.out.println("No se logro borrar");
            
        }
    }
}


