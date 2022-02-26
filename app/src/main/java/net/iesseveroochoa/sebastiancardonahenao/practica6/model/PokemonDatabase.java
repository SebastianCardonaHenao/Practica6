package net.iesseveroochoa.sebastiancardonahenao.practica6.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Pokemon.class}, version = 1)
//Nos transforma automáticamente las fechas a entero
@TypeConverters({TransformaFechaSQLite.class})
public abstract class PokemonDatabase extends RoomDatabase {
    //Permite el acceso a los metodos CRUD
    public abstract PokemonDao pokemonDao();
    //la base de datos
    private static volatile PokemonDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService executor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static PokemonDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PokemonDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    //nombre del fichero de la base de datos
                                    PokemonDatabase.class, "pokemon_database")
                    //nos permite realizar tareas cuando es nueva o se hacreado una
                    //nueva versión del programa
                    .addCallback(sRoomDatabaseCallback)//para ejecutar al crear o al abrir
                    .build();
                }
            }
        }
        return INSTANCE;
    }
    //crearemos una tarea en segundo plano que nos permite cargar los datos de ejemplo la primera
    //vez que se abre la base de datos
    private static RoomDatabase.Callback sRoomDatabaseCallback = new
            RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    //creamos algunos contactos en un hilo
                    executor.execute(()->cargaPokemonEjemplo());
                }
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    //si queremos realizar alguna tarea cuando se abre
                }
    };
    private static void cargaPokemonEjemplo(){
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
        Pokemon pokemon;
        try {
            PokemonDao mDao=INSTANCE.pokemonDao();
            pokemon=new
                    Pokemon("bulbasaur","https://pokeapi.co/api/v2/pokemon/1/",formatoDelTexto.parse("10-10-2020"));
            mDao.insert(pokemon);
            pokemon=new
                    Pokemon("ivysaur","https://pokeapi.co/api/v2/pokemon/2/",formatoDelTexto.parse("1 1-10-2020"));
                    mDao.insert(pokemon);
            pokemon=new
                    Pokemon("venusaur","https://pokeapi.co/api/v2/pokemon/3/",formatoDelTexto.parse("12-11-2020"));
                    mDao.insert(pokemon);
            pokemon=new
                    Pokemon("charmander","https://pokeapi.co/api/v2/pokemon/4/",formatoDelTexto.parse("12-9-2020"));
            mDao.insert(pokemon);
            pokemon=new
                    Pokemon("charmeleon","https://pokeapi.co/api/v2/pokemon/5/",formatoDelTexto.parse("12-5-2020"));
            mDao.insert(pokemon);
            pokemon=new
                    Pokemon("charizard","https://pokeapi.co/api/v2/pokemon/6/",formatoDelTexto.parse("8-3-2020"));
            mDao.insert(pokemon);
            pokemon=new
                    Pokemon("squirtle","https://pokeapi.co/api/v2/pokemon/7/",formatoDelTexto.parse("1-1-2020"));
                    mDao.insert(pokemon);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
