package com.acl.challenger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.previred.desafio.tres.uf.DatosUf;
import com.previred.desafio.tres.uf.Valores;
import com.previred.desafio.tres.uf.vo.Uf;
import com.previred.desafio.tres.uf.vo.Ufs;

import java.io.FileWriter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Author: Kevyn Delgado
 * @ Description: Clase principal
 */
public class Principal {


    public static void main(String[] args) {
        JsonObject jsonObject = new JsonObject();
        JsonArray ufsArray;
        Valores valores = new Valores();
        DatosUf datos = DatosUf.getInstance();
        Ufs ufs = valores.getRango();
        CalcularUf calcularUf = new CalcularUf();
        final Logger logger = Logger.getLogger(String.valueOf(Principal.class));

        //Obtener fecha Inicio, Fecha Fin y Lista de Ufs
        Date fechaInicio = ufs.getInicio();
        Date fechaFin = ufs.getFin();

        List<Uf> listOfUfsOriginal = new ArrayList<>(ufs.getUfs());

        // Concatenar la lista de rango completo y la lista generada por el .jar
        List<Uf> listOfUfsAux = calcularUf.concatListUf(datos, listOfUfsOriginal, fechaInicio, fechaFin);

        //Limpiar y cargar los datos  sin duplicados en una nueva lista
        List<Uf> listOfUfs2 = new ArrayList<>(new LinkedHashSet<>(listOfUfsAux));

        //Ordenar la lista auxiliar y asignar valores a la lista original
        listOfUfsOriginal.clear();
        listOfUfsOriginal.addAll(calcularUf.sortListUfs(listOfUfs2));

        ufsArray = calcularUf.getJsonArrayUfs(listOfUfsOriginal);

        jsonObject.addProperty("inicio", new java.sql.Date(fechaInicio.getTime()).toLocalDate().toString());
        jsonObject.addProperty("fin", new java.sql.Date(fechaFin.getTime()).toLocalDate().toString());
        jsonObject.add("UFs", ufsArray);

        try (FileWriter file = new FileWriter("valores.json")){
            file.write(jsonObject.toString());
            file.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE,e.getMessage());
        }

    }
}
