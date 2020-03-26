package mx.edu.ittepic.ladm_u3_ejercicio4_sqliteavanzado

import android.content.ContentValues
import android.database.sqlite.SQLiteException

class Trabajador(n:String,p:String,s:Float) {
    var nombre = n
    var puesto = p
    var sueldo = s
    var id = 0
    var error = -1
    /*
        Valores de error
        1 = error en tabla, no se creó o no se conectó a base de datos
        2 = error no se pudo insertar
        3 = No se pudo realizar consulta por tabla vacia
     */
    val nombreBaseDatos ="empresa"
    var puntero : MainActivity ?= null

    fun asignarPuntero(p:MainActivity){
        puntero = p
    }
    fun insertar():Boolean{
        error = -1
        try {
            var base = BaseDatos(puntero!!, nombreBaseDatos, null, 1)
            var insertar = base.writableDatabase
            var datos = ContentValues()

            datos.put("NOMBRE", nombre)
            datos.put("PUESTO", puesto)
            datos.put("SUELDO", sueldo)
            var respuesta = insertar.insert("TRABAJADOR", "IDTRABAJADOR", datos)
            if(respuesta.toInt()==-1){
                error = 2
                return false
            }
        }catch (e:SQLiteException){
            error = 1
            return false
        }

        return true
    }

    fun mostrarTodos():ArrayList<Trabajador>{
        var data = ArrayList<Trabajador>()

        try{
            var base = BaseDatos(puntero!!,nombreBaseDatos,null,1)
            var select = base.readableDatabase
            var columnas = arrayOf("*")

            var cursor = select.query("TRABAJADOR",columnas,null,null,null,null,null,null)
            if(cursor.moveToFirst()){
                do{
                    var trabajadorTemporal = Trabajador(cursor.getString(1),cursor.getString(2),cursor.getFloat(3))
                    trabajadorTemporal.id = cursor.getInt(0)
                    data.add(trabajadorTemporal)
                }while (cursor.moveToNext())
            }else{
                error = 3
            }
        }catch (e:SQLiteException){
            error = 1
        }
        return data
    }
}