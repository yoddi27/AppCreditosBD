/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appcredito.controller;

import co.mycompany.appcredito.jpacontroller.CreditosJpaController;
import co.mycompany.appcredito.jpacontroller.exceptions.RollbackFailureException;
import co.mycompany.appcredito.model.Creditos;
import co.mycompany.appcredito.util.JPAFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author yypv2
 */
public class GuardarDatosServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            HttpSession session = request.getSession(true);
            RequestDispatcher rd = null;
            String numcredito = request.getParameter("txtnumerocredito");
            int intCredito ;
            try{
                intCredito= Integer.parseInt(numcredito);
            }catch(Exception e){
                intCredito=0;
            }
            String numdocumento = request.getParameter("txtdocumento");
            int intDocumento;
            try{
                intDocumento= Integer.parseInt(numdocumento);
            }catch(Exception e){
                intDocumento=0;
            }
            String nombres = request.getParameter("txtnombres");
            String apellidos = request.getParameter("txtapellidos");
            String montoprestar = request.getParameter("txtMontoPrestar");
            
            int intMontoprestar;
            try{
                intMontoprestar= Integer.parseInt(montoprestar);
            }catch(Exception e){
                intMontoprestar=0;
            }
            String tipotrabajador = request.getParameter("txttipotrabajador");
            String tipocredito = request.getParameter("txtipocredito");
            String trabajaempresa = request.getParameter("txttrabajaempresa");
            String accion = request.getParameter("accion");
            List<Creditos> listaCreditos;

            CreditosJpaController jpaController = new CreditosJpaController(JPAFactory.getFACTORY());

            Creditos credito;
            switch (accion) {
                case "prepararCrear":
                    rd = request.getRequestDispatcher("/View/Creditos.jsp");
                    break;
                case "Guardar":
                    credito = new Creditos(intCredito);
                    credito.setNumdocumento(intDocumento);
                    credito.setNombres(nombres);
                    credito.setApellidos(apellidos);
                    credito.setMontoprestar(intMontoprestar);
                    credito.setTipotrabajador(tipotrabajador);
                    credito.setTipocredito(tipocredito);
                    credito.setTrabajaempresa(trabajaempresa);
                    jpaController.create(credito);
                    session.setAttribute("MENSAJE", "Crédito Exitoso!");
                    rd = request.getRequestDispatcher("/mensaje.jsp");
                    break;
                case "listar":
                    listaCreditos = jpaController.findCreditosEntities();
                    session.setAttribute("LISTADO", listaCreditos);
                    session.setAttribute("INFORME", new ArrayList<String>());
                    rd = request.getRequestDispatcher("/View/ListaCreditos.jsp");
                    break;
                case "creditosMasUsados":
                    listaCreditos = jpaController.findCreditosEntities();
                    session.setAttribute("LISTADO", listaCreditos);
                    session.setAttribute("TIPOINFORME", "Credito mas usado");
                    session.setAttribute("INFORME", informeCreditoMasUsado(listaCreditos));

                    rd = request.getRequestDispatcher("/View/ListaCreditos.jsp");
                    break;
                case "AcomuladoDePrestamos":
                    listaCreditos = jpaController.findCreditosEntities();
                    session.setAttribute("LISTADO", listaCreditos);
                    session.setAttribute("TIPOINFORME", "Acumulado de Prestamos");
                    session.setAttribute("INFORME", informeAcumuladoPrestamos(listaCreditos));

                    rd = request.getRequestDispatcher("/View/ListaCreditos.jsp");
                    break;
                case "quienesPrestan":
                    listaCreditos = jpaController.findCreditosEntities();
                    session.setAttribute("LISTADO", listaCreditos);
                    session.setAttribute("TIPOINFORME", "Quines prestan más");
                    session.setAttribute("INFORME", informeQuienesPrestanMas(listaCreditos));

                    rd = request.getRequestDispatcher("/View/ListaCreditos.jsp");
                    break;

                default:
                    break;
            }
            rd.forward(request, response);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(GuardarDatosServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GuardarDatosServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public List<String> informeCreditoMasUsado(List<Creditos> listaCreditos){
        Integer[] acomuladores= new Integer[3];
       
        List<String> resultado=new ArrayList<>();
        Integer maximo=0;
        for (int i = 0; i < acomuladores.length; i++) {
            acomuladores[i]=0;            
        }
        if(listaCreditos.size()>0){
            for (Creditos listaCredito : listaCreditos) {
                acomuladores[Integer.parseInt(listaCredito.getTipocredito())-1]++;                
            }
            for (int i = 0; i < acomuladores.length; i++) {
                Integer acomuladore = acomuladores[i];
                maximo=Integer.max(maximo, acomuladore);
            }
            for (int i = 0; i < acomuladores.length; i++) {
                Integer acomuladore = acomuladores[i];
                if(maximo.equals(acomuladore)){
                    resultado.add(getDescripcionTipoCredito((i+1)+""));
                }
            }
        }else{
            resultado.add("No hay créditos registrados.");
        }
        return resultado;
    }
    
    public String getDescripcionTipoCredito(String tipo){
        switch(tipo){
            case "1":
                return "Vivienda";
            case "2":
                return "Estudio";
            case "3":
                return "Libre Inversión";
            default:
                return "sin tipo";
        }
    }
    
    
    public List<String> informeQuienesPrestanMas(List<Creditos> listaCreditos){
        Integer[] acomuladores= new Integer[2];
       
        List<String> resultado=new ArrayList<>();
        Integer maximo=0;
        Integer acomuladore;
        for (int i = 0; i < acomuladores.length; i++) {
            acomuladores[i]=0;            
        }
        if(listaCreditos.size()>0){
            for (Creditos listaCredito : listaCreditos) {
                acomuladores[Integer.parseInt(listaCredito.getTipotrabajador())-1]++;                
            }
            for (int i = 0; i < acomuladores.length; i++) {
                acomuladore = acomuladores[i];
                maximo=Integer.max(maximo, acomuladore);
            }
            for (int i = 0; i < acomuladores.length; i++) {
                acomuladore = acomuladores[i];
                if(maximo.equals(acomuladore)){
                    resultado.add(getDescripcionTipoTrabajador((i+1)+""));
                }
            }
        }else{
            resultado.add("No hay registros de quienes prestan.");
        }
        return resultado;
    }
    
    public String getDescripcionTipoTrabajador(String tipo){
        switch(tipo){
            case "1":
                return "Independiente";
            case "2":
                return "Dependiente";
            default:
                return "sin tipo";
        }
    }
    
     public List<String> informeAcumuladoPrestamos(List<Creditos> listaCreditos){

        Integer[] acomuladores= new Integer[3];
        List<String> resultado=new ArrayList<>();
        Integer maximo=0;
        Integer acomuladore;
        for (int i = 0; i < acomuladores.length; i++) {
            acomuladores[i]=0;            
        }
        if(listaCreditos.size()>0){
            for (Creditos listaCredito : listaCreditos) {
//                acomuladores[Integer.parseInt(listaCredito.getTipoCredito())-1]++;
                acomuladores[Integer.parseInt(listaCredito.getTipocredito())-1]=acomuladores[Integer.parseInt(listaCredito.getTipocredito())-1]+(listaCredito.getMontoprestar());
            }
            for (int i = 0; i < acomuladores.length; i++) {
                acomuladore = acomuladores[i];
                maximo=Integer.max(maximo, acomuladore);
            }
            for (int i = 0; i < acomuladores.length; i++) {
                acomuladore = acomuladores[i];
                if(maximo.equals(acomuladore)){
                    resultado.add(getDescripcionTipoCredito((i+1)+"")+" - Valor: "+maximo);
                }
            }
        }else{
            resultado.add("No hay registros acumulados.");
        }
        return resultado;
    }
     
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
