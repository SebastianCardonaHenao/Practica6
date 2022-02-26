package net.iesseveroochoa.sebastiancardonahenao.practica6.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.iesseveroochoa.sebastiancardonahenao.practica6.R;
import net.iesseveroochoa.sebastiancardonahenao.practica6.databinding.FragmentVerPokemonBinding;
import net.iesseveroochoa.sebastiancardonahenao.practica6.model.Pokemon;
import net.iesseveroochoa.sebastiancardonahenao.practica6.utils.Utils;

public class VerPokemonFragment extends Fragment {
    public static final String ARG_POKEMON = "VerPokemonFragment.pokemon";
    private FragmentVerPokemonBinding binding;


    public VerPokemonFragment() {
        // Required empty public constructor
    }

    public static VerPokemonFragment newInstance() {
        VerPokemonFragment fragment = new VerPokemonFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentVerPokemonBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //recuperamos el pokemos y lo mostramos
        Pokemon pokemon=getArguments().getParcelable(ARG_POKEMON);
        binding.tvPokeName.setText(pokemon.getNombre().toUpperCase());
        //mostramos la imagen con Glide
        Utils.cargaImagen(binding.imageView2,pokemon.getUrlImagen());
        return root;

    }
}