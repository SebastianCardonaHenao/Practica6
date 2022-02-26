package net.iesseveroochoa.sebastiancardonahenao.practica6.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import net.iesseveroochoa.sebastiancardonahenao.practica6.R;
import net.iesseveroochoa.sebastiancardonahenao.practica6.model.Pokemon;
import net.iesseveroochoa.sebastiancardonahenao.practica6.utils.Utils;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private List<Pokemon> listaPokemon;

    private OnPokemonClickListener listenerPokemon;

    @NonNull
    @Override
    public PokemonAdapter.PokemonViewHolder onCreateViewHolder
            (@NonNull ViewGroup parent, int viewType) {
        View itemView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon,
                        parent, false);
        return new PokemonViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull PokemonAdapter.PokemonViewHolder
                                         holder, int position) {
        if (listaPokemon != null) {
            //mostramos los valores en el cardview
            final Pokemon pokemon = listaPokemon.get(position);
            holder.tvNombre.setText(pokemon.getNombre().toUpperCase());
            //comprobamos si tenemos fecha por si no es de base de datos. Ya que
            //utilizamos la misma clase
            holder.tvFecha.setText(pokemon.getFechaCompraFormatoLocal());
            //utilizamos Glide para mostrar la imagen
            Utils.cargaImagen(holder.ivPokemon,pokemon.getUrlImagen());
            //guardamos el pokemon actual
            holder.pokemon=pokemon;
        }
    }
    @Override
    public int getItemCount() {
        if (listaPokemon != null)
            return listaPokemon.size();
        else return 0;
    }
    //cuando se modifique la base de datos, actualizamos el recyclerview
    public void setListaPokemon(List<Pokemon> pokemons){
        listaPokemon=pokemons;
        notifyDataSetChanged();
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNombre;
        private ImageView ivPokemon;
        private TextView tvFecha;
        private CardView cvPokemon;
        //private ImageView ivBorrar;
        //guardamos el pokemon para simplificar
        private Pokemon pokemon;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            ivPokemon = itemView.findViewById(R.id.ivPokemon);
            cvPokemon = itemView.findViewById(R.id.cvPokemons);
            cvPokemon.setOnClickListener(view -> {
                if (listenerPokemon!=null)
                    listenerPokemon.onPokemonClickListener(pokemon);
            });

        }

        public Pokemon getPokemon() {
            return pokemon;
        }


    }

    public interface OnPokemonClickListener {
        void onPokemonClickListener(Pokemon pokemon);
    }

    public void setOnPokemonClickListener(OnPokemonClickListener listener){
        this.listenerPokemon = listener;
    }

}

