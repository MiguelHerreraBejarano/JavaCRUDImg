package controller;

import dao.ClienteDao;
import dao.ConectarDB;
import java.io.File;
import java.net.ConnectException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.swing.JOptionPane;
import models.Cliente;
import models.ClienteValidation;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class homeController {
    private JdbcTemplate jdbcTemplate;
    ClienteValidation cliValidar;
    public homeController() {
        this.cliValidar = new ClienteValidation();
        ConectarDB con = new ConectarDB();
        
            this.jdbcTemplate = new JdbcTemplate(con.conectar());
              
    }
    
    @RequestMapping(value="formCliente.htm", method=RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView mav = new ModelAndView();
        Cliente cliente = new Cliente();        
        mav.addObject("cliente",cliente);
        mav.setViewName("views/formCliente");
        return mav;
    }
    
    @RequestMapping(value="formCliente.htm", method=RequestMethod.POST)
    public ModelAndView mostrarCliente(){
        ModelAndView mav = new ModelAndView() ;
        ClienteDao cli = new ClienteDao();
        List datos = cli.consultarCliente();
        mav.addObject("cliente",datos);
        mav.setViewName("views/mostrarCliente");
        return mav;
    }

    

    @RequestMapping(value="agregarCliente.htm", method=RequestMethod.GET)
    public ModelAndView cargarCliente(){
        ModelAndView mav = new ModelAndView();
         mav.addObject("cliente", new Cliente());
        mav.setViewName("views/agregarCliente");
        return mav;
    }

    
    private static String UPLOAD_DIRECTORY = "..\\..\\web\\Imagenes\\fotos";
    
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50;
    
    
    
    @RequestMapping(value="agregarCliente.htm", method=RequestMethod.POST)
    public ModelAndView agregarCliente(Cliente cli, HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
       
        
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        
        ArrayList<String> listados = new ArrayList<>();
        if (isMultipart){
            
            DiskFileItemFactory file = new DiskFileItemFactory();
            
            file.setSizeThreshold(MEMORY_THRESHOLD);
            
            file.setRepository(new File(System.getProperty("java.io.tmpdir")));
                    ServletFileUpload fileUpload = new ServletFileUpload(file);
                
                    fileUpload.setFileSizeMax(MAX_FILE_SIZE);
                    fileUpload.setSizeMax(MAX_REQUEST_SIZE);
                    
                    String uploadPath = request.getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
                    File uploadDir= new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            
            
            List<FileItem> items = null;
            try {
                items = fileUpload.parseRequest(request);
            
            }catch (FileUploadException ex){
                System.out.print("carga..." + ex.getMessage());
            }
            for (int i=0; i < items.size(); i++){
                
                FileItem fileItem = (FileItem) items.get(i);
                
                if(!fileItem.isFormField()){                   
                     
                    
        String fileName= new File(fileItem.getName()).getName();
        String filePath= uploadPath + File.separator+ fileName;
        File uploadFile = new File(filePath);
                        
                    
                    String nameFile = ("Imagenes/fotos/"+fileName  );
                    try {
                    // Almacen:
                        fileItem.write(uploadFile);
                        cli.setFoto(nameFile);
                    } catch (Exception e) {
                        System.out.print("escritura... " + e.getMessage());

                    } 
                    }
                    else {
                        listados.add(fileItem.getString());
                            }
                    }
                    cli.setNombre (listados.get (0));
                    cli.setDireccion (listados.get (1));
                    cli.setCorreo (listados.get (2));
                    cli.setTelefono (listados.get (3));
                    cli.setSalario(Integer.parseInt(listados.get(4)));

                    }

                    String sql = "insert into cliente (nombre, direccion, "
                        + "correo, telefono, salario, foto) values ( ?,?,?,?,?,?)";
                    jdbcTemplate.update (sql, cli.getNombre(), cli.getDireccion(),
                        cli.getCorreo(), cli.getTelefono(), cli.getSalario(), cli.getFoto());
                    mav.setViewName ("redirect:/formCliente.htm");
                    return mav;
                    }

    @RequestMapping(value="actCliente.htm", method=RequestMethod.GET)
    public ModelAndView actCliente(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        int id = Integer.parseInt(request.getParameter("id"));
        String fotoOld = request.getParameter("foto0Id");
        
        
        Cliente cli = this.cargarClientesbyId(id);
        cli.setfotoOld(fotoOld);
        /*mav.addObject("cliente", new Cliente(id, cli.getNombre(),*/
        mav.addObject("cliente", new Cliente (id, cli.getNombre(), cli.getDireccion(), cli.getCorreo(), cli.getTelefono(), cli.getSalario(), cli.getFoto(), cli.getfotoOld() ));
        mav.setViewName("views/actCliente");
        return mav;
    }
    
    
    
    
    @RequestMapping(value="actCliente.htm", method=RequestMethod.POST)
    public ModelAndView actCliente(Cliente cli, HttpServletRequest req) {
        ModelAndView mav = new ModelAndView();
        ClienteDao cliDao = new ClienteDao();
        ArrayList<String> listados = new ArrayList<>();
        
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
        
        DiskFileItemFactory file = new DiskFileItemFactory();
        
        ServletFileUpload fileUpload = new ServletFileUpload(file);
        List<FileItem> items = null;
        try{
            items = fileUpload.parseRequest(req);
            for (int i = 0; i< items.size(); i++){
                
                FileItem fileItem = (FileItem) items.get(i);
                listados.add(fileItem.getString());
            }
        }catch (FileUploadException ex){
            System.out.printf("error en la carga de la imagen homeController/actCliente..." + ex.getMessage());
        }
        if (listados.get(5).isEmpty() || listados.get(5).equals("") ||  listados.get(5).equals(null)){
            cliDao.accUsuarioSinFoto(cli, items);
        } else {
            cliDao.accUsuarioConImagenFoto(cli, isMultipart, req, items);    
        }
        mav.setViewName("redirect:/listarClientes.htm");
        return mav;
    }

   
    @RequestMapping("borrarCliente.htm")
    public ModelAndView borrarCliente(HttpServletRequest request ){
    ModelAndView mav = new ModelAndView();
    ClienteDao cliDao = new ClienteDao();
    int id = Integer.parseInt(request.getParameter("id"));
    String deletePath = request.getServletContext().getRealPath("") + File.separator;
    String foto =  request.getParameter("foto");
    
    System.out.println(deletePath + " ------ " + foto);
    
    cliDao.borrarImagen(foto,deletePath, id);
    mav.setViewName("redirect:/formCliente.htm");
    return mav;
    }
    
    public Cliente cargarClientesbyId(int id){
        final Cliente cli = new Cliente( );
        String sql = "select * from cliente where id =  " + id ; 
        
        return (Cliente) jdbcTemplate.query(sql, new ResultSetExtractor<Cliente>()
        {
            public Cliente extractData (ResultSet rs ) throws SQLException, DataAccessException{
                if(rs.next()){
                    cli.setId(rs.getInt("id"));
                    cli.setNombre(rs.getString("nombre"));
                    cli.setDireccion(rs.getString("direccion"));
                    cli.setCorreo(rs.getString("correo"));
                    cli.setTelefono(rs.getString("telefono"));
                    cli.setSalario(rs.getInt("salario"));
                    cli.setFoto(rs.getString("foto"));
                }
                return cli;
            }         
        });
    }
}