package net.iesseveroochoa.sebastiancardonahenao.practica6.ui.pokemon;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.iesseveroochoa.sebastiancardonahenao.practica6.databinding.FragmentFavoritosBinding;
import net.iesseveroochoa.sebastiancardonahenao.practica6.databinding.FragmentPokemonsBinding;
import net.iesseveroochoa.sebastiancardonahenao.practica6.model.Pokemon;
import net.iesseveroochoa.sebastiancardonahenao.practica6.ui.adapters.PokemonAdapter;
import net.iesseveroochoa.sebastiancardonahenao.practica6.ui.favoritos.FavoritosViewModel;

import java.util.Date;
import java.util.List;

public class PokemonsFragment extends Fragment {

    private FragmentPokemonsBinding binding;
    private RecyclerView rvPokemons;
    private ProgressBar pbCargandoDatos;
    private PokemonAdapter adapter;
    private PokemonsViewModel pokemonsViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        pokemonsViewModel = new ViewModelProvider(this).get(PokemonsViewModel.class);

        binding = FragmentPokemonsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        pbCargandoDatos = binding.pbCargandoDatos;
        rvPokemons = binding.rvPokemons;
        adapter = new PokemonAdapter();
        rvPokemons.setAdapter(adapter);
        rvPokemons.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPokemons.setBackgroundColor(Color.GREEN);

         pokemonsViewModel.getAllPokemons().observe(getViewLifecycleOwner(), listaPokemon -> {
            adapter.setListaPokemon(listaPokemon);
            onChanged(listaPokemon);
         });

         definirEventoSwiper();
         defineDetectarFinRecycler();

        return root;
    }

    private void definirEventoSwiper() {
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
                        insertaDia(pokemon, vhPokemon.getAdapterPosition());
                    }
                };
        //Creamos el objeto de ItemTouchHelper que se encargará del trabajo
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        //lo asociamos a nuestro reciclerView
        itemTouchHelper.attachToRecyclerView(rvPokemons);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void insertaDia(Pokemon pokemon, int posicion) {
        pokemon.setFechaCompra(new Date());
        pokemonsViewModel.insert(pokemon);
        adapter.notifyItemChanged(posicion); //para que lo vuelva a mostrar
    }

    private void defineDetectarFinRecycler() {
        binding.rvPokemons.addOnScrollListener(new
        RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //de esta forma sabemos si llega al final
                if ((!recyclerView.canScrollVertically(1)) && (newState == RecyclerView.SCROLL_STATE_IDLE)) {
                    Log.v("scroll", "fin recyclerview");
                    pbCargandoDatos.setVisibility(View.VISIBLE);
                    //recuperamos los 20 siguientes pokemon eso hara que se active el observador
                    pokemonsViewModel.getNextPokemon();
                }
            }
        });
    }
    public void onChanged(@Nullable List<Pokemon> listaPokemon) {
        adapter.setListaPokemon(listaPokemon);
        pbCargandoDatos.setVisibility(View.INVISIBLE);
    }


}