package com.acl.challenger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.previred.desafio.tres.uf.DatosUf;
import com.previred.desafio.tres.uf.vo.Uf;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CalcularUf {

    /**
     * Concatenar dos lista de Ufs
     * @param datos
     * @param listOfUfs
     * @param fechaInicio
     * @param fechaFin
     * @return Lista de UF
     */
    public List<Uf> concatListUf(DatosUf datos, List<Uf> listOfUfs, Date fechaInicio, Date fechaFin) {
        return  Stream.concat(datos.getUfs(fechaInicio, fechaFin).stream(), listOfUfs.stream()).collect(Collectors.toList());
    }

    /**
     * Ordenar lista de Ufs
     * @param listOfUfsAux
     * @return HashSet
     */
    public HashSet sortListUfs (List listOfUfsAux) {
        return (HashSet) new HashSet<>(listOfUfsAux).stream()
                .sorted(Comparator.comparing(Uf::getFecha).reversed()).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Obtener un arreglo de Json de UFs
     * @param listUfs
     * @return JsonArray
     */
    public JsonArray getJsonArrayUfs(List listUfs) {
        JsonArray result = new JsonArray();
        Uf uf;

        for (int i = 0; i < listUfs.size(); i++) {
            JsonObject newUf = new JsonObject();
            uf = (Uf) listUfs.get(i);
            newUf.addProperty("fecha", new java.sql.Date(uf.getFecha().getTime()).toLocalDate().toString());
            newUf.addProperty("valor", uf.getValor().toString());
            result.add(newUf);
        }
        return result;
    }
}
