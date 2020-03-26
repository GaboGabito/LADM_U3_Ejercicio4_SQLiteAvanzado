package mx.edu.ittepic.ladm_u3_ejercicio4_sqliteavanzado

import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var listaID = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        insertar.setOnClickListener {
            var trabajador = Trabajador(nombre.text.toString(), puesto.text.toString(), sueldo.text.toString().toFloat())
            trabajador.asignarPuntero(this)
            var resultado = trabajador.insertar()
            if(resultado==true){
                mensaje("SE CAPTURO TRABAJADOR")
                nombre.setText("")
                puesto.setText("")
                sueldo.setText("")
            }else{
                when(trabajador.error){
                    1 -> {dialogo(" error en tabla, no se creó o no se conectó a base de datos")}
                    2-> {dialogo("error no se pudo insertar")}
                }
            }
        }
    }
    fun cargarLista(){
        try{
            var conexion = Trabajador("","",0f)
            conexion.asignarPuntero(this)
            var data = conexion.mostrarTodos()

            if(data.size == 0){
                if(conexion.error == 3){
                    dialogo("No se pudo realizar consulta por tabla vacia")
                }
                return
            }

            var total = data.size-1
            var vector = Array<String>(data.size,{""})
            listaID = ArrayList<String>()
            (0..total).forEach {
                var trabajador = data[it]
                var item = trabajador.nombre+"\n"+trabajador.puesto
                vector[it] = item
                listaID.add(trabajador.id.toString())
            }
            lista.adapter  = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vector)

        }catch (e:Exception){
            dialogo(e.message.toString())
        }
    }
    fun mensaje(s:String){
        Toast.makeText(this,s,Toast.LENGTH_LONG).show()
    }
    fun dialogo(s:String){
        AlertDialog.Builder(this).setTitle("ATENCION")
            .setPositiveButton("OK"){d, i->}
            .show()
    }
}
