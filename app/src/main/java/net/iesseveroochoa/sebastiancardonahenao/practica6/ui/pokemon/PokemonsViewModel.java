package net.iesseveroochoa.sebastiancardonahenao.practica6.ui.pokemon;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PokemonsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PokemonsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is pokemon fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}