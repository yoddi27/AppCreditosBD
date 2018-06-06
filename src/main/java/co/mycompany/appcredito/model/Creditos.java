/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.mycompany.appcredito.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yypv2
 */
@Entity
@Table(name = "CREDITOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Creditos.findAll", query = "SELECT c FROM Creditos c")
    , @NamedQuery(name = "Creditos.findByNumcredito", query = "SELECT c FROM Creditos c WHERE c.numcredito = :numcredito")
    , @NamedQuery(name = "Creditos.findByNumdocumento", query = "SELECT c FROM Creditos c WHERE c.numdocumento = :numdocumento")
    , @NamedQuery(name = "Creditos.findByNombres", query = "SELECT c FROM Creditos c WHERE c.nombres = :nombres")
    , @NamedQuery(name = "Creditos.findByApellidos", query = "SELECT c FROM Creditos c WHERE c.apellidos = :apellidos")
    , @NamedQuery(name = "Creditos.findByMontoprestar", query = "SELECT c FROM Creditos c WHERE c.montoprestar = :montoprestar")
    , @NamedQuery(name = "Creditos.findByTipotrabajador", query = "SELECT c FROM Creditos c WHERE c.tipotrabajador = :tipotrabajador")
    , @NamedQuery(name = "Creditos.findByTipocredito", query = "SELECT c FROM Creditos c WHERE c.tipocredito = :tipocredito")
    , @NamedQuery(name = "Creditos.findByTrabajaempresa", query = "SELECT c FROM Creditos c WHERE c.trabajaempresa = :trabajaempresa")})
public class Creditos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "NUMCREDITO")
    private Integer numcredito;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NUMDOCUMENTO")
    private int numdocumento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRES")
    private String nombres;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONTOPRESTAR")
    private int montoprestar;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "TIPOTRABAJADOR")
    private String tipotrabajador;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "TIPOCREDITO")
    private String tipocredito;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "TRABAJAEMPRESA")
    private String trabajaempresa;

    public Creditos() {
    }

    public Creditos(Integer numcredito) {
        this.numcredito = numcredito;
    }

    public Creditos(Integer numcredito, int numdocumento, String nombres, String apellidos, int montoprestar, String tipotrabajador, String tipocredito, String trabajaempresa) {
        this.numcredito = numcredito;
        this.numdocumento = numdocumento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.montoprestar = montoprestar;
        this.tipotrabajador = tipotrabajador;
        this.tipocredito = tipocredito;
        this.trabajaempresa = trabajaempresa;
    }

    public Integer getNumcredito() {
        return numcredito;
    }

    public void setNumcredito(Integer numcredito) {
        this.numcredito = numcredito;
    }

    public int getNumdocumento() {
        return numdocumento;
    }

    public void setNumdocumento(int numdocumento) {
        this.numdocumento = numdocumento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getMontoprestar() {
        return montoprestar;
    }

    public void setMontoprestar(int montoprestar) {
        this.montoprestar = montoprestar;
    }

    public String getTipotrabajador() {
        return tipotrabajador;
    }

    public void setTipotrabajador(String tipotrabajador) {
        this.tipotrabajador = tipotrabajador;
    }

    public String getTipocredito() {
        return tipocredito;
    }

    public void setTipocredito(String tipocredito) {
        this.tipocredito = tipocredito;
    }

    public String getTrabajaempresa() {
        return trabajaempresa;
    }

    public void setTrabajaempresa(String trabajaempresa) {
        this.trabajaempresa = trabajaempresa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numcredito != null ? numcredito.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Creditos)) {
            return false;
        }
        Creditos other = (Creditos) object;
        if ((this.numcredito == null && other.numcredito != null) || (this.numcredito != null && !this.numcredito.equals(other.numcredito))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.mycompany.appcredito.model.Creditos[ numcredito=" + numcredito + " ]";
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
    
    public String getDescripcionTipoCredito(){
        switch(this.getTipocredito()){
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
    
    public String getDescripcionTipoTrabajador(){
        switch(this.getTipotrabajador()){
            case "1":
                return "Independiente";
            case "2":
                return "Dependiente";
            default:
                return "sin tipo";
        }
    }
    
}
