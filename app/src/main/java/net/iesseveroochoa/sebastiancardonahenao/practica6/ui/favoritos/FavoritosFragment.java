package net.iesseveroochoa.sebastiancardonahenao.practica6.ui.favoritos;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.iesseveroochoa.sebastiancardonahenao.practica6.databinding.FragmentFavoritosBinding;
import net.iesseveroochoa.sebastiancardonahenao.practica6.model.Pokemon;
import net.iesseveroochoa.sebastiancardonahenao.practica6.ui.adapters.PokemonAdapter;

public class FavoritosFragment extends Fragment {

    private FragmentFavoritosBinding binding;
    private PokemonAdapter adapter;
    private FavoritosViewModel favoritosViewModel;
    private RecyclerView rvPokemons;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        favoritosViewModel = new ViewModelProvider(this).get(FavoritosViewModel.class);

        binding = FragmentFavoritosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        rvPokemons = binding.rvPokemons;
        adapter = new PokemonAdapter();
        rvPokemons.setAdapter(adapter);
        rvPokemons.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPokemons.setBackgroundColor(Color.GREEN);

        favoritosViewModel.getAllPokemons().observe(getViewLifecycleOwner(), listaPokemon -> {
            adapter.setListaPokemon(listaPokemon);
        });
        definirEventoSwiper();

        return root;
    }

    private void definirEventoSwiper() {
        //Creamos el Evento de Swiper
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT |
                        ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int
                            swipeDir) {
                        //realizamos un cast del viewHolder y obtenemos el pokemon a
                        // borrar
                        // PokemonListaPokemon
                        PokemonAdapter.PokemonViewHolder
                                vhPokemon=(PokemonAdapter.PokemonViewHolder) viewHolder;
                        Pokemon pokemon=vhPokemon.getPokemon();
                        borrarPokemon(pokemon, vhPokemon.getAdapterPosition());
                    }
                };
        //Creamos el objeto de ItemTouchHelper que se encargará del trabajo
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        //lo asociamos a nuestro reciclerView
        itemTouchHelper.attachToRecyclerView(rvPokemons);
    }
    private void borrarPokemon(Pokemon pokemon,int posicion) {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(getContext());
        dialogo.setTitle("Aviso");
        dialogo.setMessage("Desea borrar a "+pokemon.getNombre());
        dialogo.setNegativeButton(android.R.string.cancel, new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        adapter.notifyItemChanged(posicion);//recuperamos la posición
                    }
                });
        dialogo.setPositiveButton(android.R.string.ok, new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Borramos
                        favoritosViewModel.delete(pokemon);
                    }
                });
        adapter.setOnPokemonClickListener(this::showMessage);
        dialogo.show();
    }

    private void showMessage(Pokemon pokemon) {
        Log.d("TOAST", "NO FUNCIONA");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}