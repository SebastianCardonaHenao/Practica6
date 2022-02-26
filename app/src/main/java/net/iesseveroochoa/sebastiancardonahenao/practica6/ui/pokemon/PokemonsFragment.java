package net.iesseveroochoa.sebastiancardonahenao.practica6.ui.pokemon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import net.iesseveroochoa.sebastiancardonahenao.practica6.databinding.FragmentPokemonsBinding;

public class PokemonsFragment extends Fragment {

    private FragmentPokemonsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PokemonsViewModel pokemonsViewModel =
                new ViewModelProvider(this).get(PokemonsViewModel.class);

        binding = FragmentPokemonsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        pokemonsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}